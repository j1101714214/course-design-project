package edu.whu.service;

import edu.whu.model.job.pojo.XyJob;
import io.netty.util.concurrent.CompleteFuture;

import java.util.concurrent.CompletableFuture;

/**
 * @author Akihabara
 * @version 1.0
 * @description IRestTemplateService: TODO
 * @date 2023/9/21 20:18
 */
public interface IRestTemplateService {
    /**
     * 执行get调用
     * @param xyJob 任务详情
     * @return      任务调用后返回值
     */
    CompletableFuture<Void> doGet(XyJob xyJob);

    /**
     * 执行post调用
     * @param xyJob 任务详情
     * @return      任务调用后返回值
     */
    CompletableFuture<Void> doPost(XyJob xyJob);

    /**
     * 执行put调用
     * @param xyJob 任务详情
     */
    CompletableFuture<Void> doPut(XyJob xyJob);

    /**
     * 执行delete调用
     * @param xyJob 任务详情
     */
    CompletableFuture<Void> doDelete(XyJob xyJob);
}
