package edu.whu.service;

import edu.whu.model.job.pojo.XyJob;
import io.netty.util.concurrent.CompleteFuture;

import java.util.concurrent.CompletableFuture;

/**
 * @author Akihabara
 * @version 1.0
 * @description IRestTemplateService: REST API接口调用服务层
 * @date 2023/9/21 20:18
 */
public interface IRestTemplateService {
    /**
     * 执行get调用
     * @param xyJob 任务详情
     */
    void doGet(XyJob xyJob);

    /**
     * 执行post调用
     * @param xyJob 任务详情
     */
    void doPost(XyJob xyJob);

    /**
     * 执行put调用
     */
    void doPut(XyJob xyJob);

    /**
     * 执行delete调用
     */
    void doDelete(XyJob xyJob);
}
