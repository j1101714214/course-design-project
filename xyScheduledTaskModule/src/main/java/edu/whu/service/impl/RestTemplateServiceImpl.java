package edu.whu.service.impl;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import edu.whu.exception.MethodInvokeException;
import edu.whu.model.job.pojo.XyJob;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IRestTemplateService;
import edu.whu.service.IXyLogService;
import edu.whu.service.IXyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Akihabara
 * @version 1.0
 * @description RestTemplateServiceImpl: restful服务, 调用插件服务接口
 * @date 2023/9/21 20:18
 */
@Service
@Slf4j
public class RestTemplateServiceImpl implements IRestTemplateService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IXyLogService logService;
    @Autowired
    private IXyUserService userService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String username;
    @Value("${xy-nas-tools.admin.address}")
    private String adminAddress;
    @Value("${xy-nas-tools.application-name}")
    private String applicationName;

    @Value("${xy-nas-tools.root}")
    private String root;

    private static final ExecutorService SINGLE_THREAD = Executors.newSingleThreadExecutor();

    @Override
    @Async
    @Retryable(
            value = MethodInvokeException.class,
            maxAttemptsExpression = "${xy-nas-tools.retry.maxAttempts}",
            backoff = @Backoff(
                    delayExpression = "${xy-nas-tools.retry.backoff.delay}",
                    multiplierExpression = "${xy-nas-tools.retry.backoff.multiplier}"
            )
    )
    public void doGet(XyJob xyJob) {
        String url = xyJob.getInvokeTarget();
        if(url.contains("userId")) {
            url = StrUtil.format(url, xyJob.getCreateUser().toString());
        }
        if(url.contains("root")) {
            url = StrUtil.format(url, root);
        }
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);



        if(result.getStatusCode().value() != HttpStatus.OK.value()) {
            throw new MethodInvokeException(result.getBody(), xyJob);
        }
        log.info("{} - 任务{}被调用, 结果为: {}",LocalDateTime.now() , xyJob.getId(), result.getBody());

    }

    @Override
    @Async
    @Retryable(
            value = MethodInvokeException.class,
            maxAttemptsExpression = "${xy-nas-tools.retry.maxAttempts}",
            backoff = @Backoff(
                    delayExpression = "${xy-nas-tools.retry.backoff.delay}",
                    multiplierExpression = "${xy-nas-tools.retry.backoff.multiplier}"
            )
    )
    public void doPost(XyJob xyJob) {
        String url = xyJob.getInvokeTarget();
        Map<String, String> param = parseParam(xyJob);
        if(url.contains("userId")) {
            url = StrUtil.format(url, xyJob.getCreateUser().toString());
        }
        if(url.contains("root")) {
            url = StrUtil.format(url, root);
        }
        ResponseEntity<String> result = restTemplate.postForEntity(url, param, String.class);
        if(result.getStatusCode().value() != HttpStatus.OK.value()) {
            throw new MethodInvokeException(result.getBody(), xyJob);
        }
    }

    @Override
    @Async
    public void doPut(XyJob xyJob) {
        String url = xyJob.getInvokeTarget();
        Map<String, String> param = parseParam(xyJob);
        restTemplate.put(url, param);
    }

    @Override
    @Async
    public void doDelete(XyJob xyJob) {
        String url = xyJob.getInvokeTarget();
        Map<String, String> param = parseParam(xyJob);
        restTemplate.delete(url, param);
    }

    private Map<String, String> parseParam(XyJob xyJob) {
        String invokeParam = xyJob.getInvokeParam();
        Map<String, String> map = new HashMap<>();

        if(StrUtil.isEmptyOrUndefined(invokeParam)) {
            return map;
        }
        String[] kvPairs = invokeParam.split(";");
        for (String kvPair : kvPairs) {
            int separator = kvPair.indexOf(':');
            map.put(kvPair.substring(0, separator), kvPair.substring(separator + 1, kvPair.length()));
        }

        return map;
    }

    @Recover
    private void recover(MethodInvokeException exception) {
        XyJob errorJob = exception.getErrorJob();
        logService.addLog(exception.getMsg(), errorJob);
        // 根据用户注册邮箱发送邮件
        XyUser user = userService.findUserById(errorJob.getCreateUser());
        // 开启一个线程发送邮件
        SINGLE_THREAD.execute(() -> {
            if(StrUtil.isNotEmpty(user.getEmail())) {
                sendMimeMail(user.getEmail(), user, errorJob, exception.getMsg());
            }
            if(StrUtil.isNotEmpty(adminAddress)) {
                sendMimeMail(adminAddress, user, errorJob, exception.getMsg());
            }
        });
    }

    private void sendMimeMail(String email, XyUser user, XyJob errorJob, String msg) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(username);
            helper.setTo(email);
            helper.setSubject("Task No." + errorJob.getId() + " process failed!!");

            // 构造参数
            Context context = new Context();
            Map<String, Object> emailParam = new HashMap<>();
            emailParam.put("username", user.getUsername());
            emailParam.put("task_id", errorJob.getId());
            emailParam.put("msg", msg);
            emailParam.put("from", applicationName);
            context.setVariable("param", emailParam);
            String template = templateEngine.process("error", context);
            helper.setText(template, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException ignored) {
        }
    }
}
