package edu.whu.service.impl;

import cn.hutool.core.util.StrUtil;
import edu.whu.exception.CustomerException;
import edu.whu.exception.MethodInvokeException;
import edu.whu.model.job.pojo.XyJob;
import edu.whu.service.IRestTemplateService;
import edu.whu.service.IXyLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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
        logService.addLog(exception.getMsg(), exception.getErrorJob());
    }
}
