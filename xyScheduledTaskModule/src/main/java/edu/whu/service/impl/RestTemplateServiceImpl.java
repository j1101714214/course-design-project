package edu.whu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import edu.whu.exception.CustomerException;
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
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
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
        Map<String, String> param = parseParam(xyJob.getInvokeParam());
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class, param);

        if(result.getStatusCode().value() != HttpStatus.ACCEPTED.value()) {
            throw new MethodInvokeException(result.getBody(), xyJob);
        }
        log.info("{} - 任务{}被调用, 结果为: {}",LocalDateTime.now() , xyJob.getId(), result);

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
        Map<String, String> param = parseParam(xyJob.getInvokeParam());
        ResponseEntity<String> result = restTemplate.postForEntity(url, param, String.class);
        if(result.getStatusCode().value() != HttpStatus.ACCEPTED.value()) {
            throw new MethodInvokeException(result.getBody(), xyJob);
        }
    }

    @Override
    @Async
    public void doPut(XyJob xyJob) {
        String url = xyJob.getInvokeTarget();
        Map<String, String> param = parseParam(xyJob.getInvokeParam());
        restTemplate.put(url, param);
    }

    @Override
    @Async
    public void doDelete(XyJob xyJob) {
        String url = xyJob.getInvokeTarget();
        Map<String, String> param = parseParam(xyJob.getInvokeParam());
        restTemplate.delete(url, param);
    }

    private Map<String, String> parseParam(String invokeParam) {
        Map<String, String> map = new HashMap<>();
        if(StrUtil.isEmptyOrUndefined(invokeParam)) {
            return map;
        }
        String[] kvPairs = invokeParam.split(";");
        for (String kvPair : kvPairs) {
            String[] pair = kvPair.split(":");

            map.put(pair[0], pair[1]);
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
                sendSimpleMail(user.getEmail(), user, errorJob, exception.getMsg());
            }
            if(StrUtil.isNotEmpty(adminAddress)) {
                sendSimpleMail(adminAddress, user, errorJob, exception.getMsg());
            }
        });
    }

    private void sendSimpleMail(String email, XyUser user, XyJob errorJob, String msg) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(username);
            helper.setTo(email);
            helper.setSubject("任务 " + errorJob.getId() + " 处理失败失败");

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
