package edu.whu.service.impl;

import edu.whu.model.job.pojo.XyJob;
import edu.whu.service.IRestTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Akihabara
 * @version 1.0
 * @description RestTemplateServiceImpl: TODO
 * @date 2023/9/21 20:18
 */
@Service
@Slf4j
public class RestTemplateServiceImpl implements IRestTemplateService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Async
    public CompletableFuture<Void> doGet(XyJob xyJob) {
        String url = xyJob.getInvokeTarget();
        Map<String, String> param = parseParam(xyJob.getInvokeParam());
        return CompletableFuture.runAsync(() -> {
            String result = restTemplate.getForObject(url, String.class, param);
            log.info("{} - 任务{}被调用, 结果为: {}",LocalDateTime.now() , xyJob.getId(), result);
        });
    }

    @Override
    @Async
    public CompletableFuture<Void> doPost(XyJob xyJob) {
        String url = xyJob.getInvokeTarget();
        Map<String, String> param = parseParam(xyJob.getInvokeParam());
        return CompletableFuture.runAsync(() -> {
            String result = restTemplate.postForObject(url, param, String.class);
            System.out.println(LocalDateTime.now() + result);
        });

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
        if(invokeParam == null) {
            return map;
        }
        String[] kvPairs = invokeParam.split(";");
        for (String kvPair : kvPairs) {
            String[] pair = kvPair.split(":");

            map.put(pair[0], pair[1]);
        }

        return map;
    }
}
