package edu.whu.exception;

import edu.whu.model.job.pojo.XyJob;

/**
 * @author Akihabara
 * @version 1.0
 * @description MethodInvokeException: 远程调用接口执行失败抛出的任务
 * @date 2023/10/6 21:08
 */
public class MethodInvokeException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private XyJob errorJob = null;
    private String msg = "";

    public MethodInvokeException(String msg, XyJob errorJob) {
        super(msg);
        this.errorJob = errorJob;
        this.msg = msg;
    }

    public XyJob getErrorJob() {
        return errorJob;
    }

    public String getMsg() {
        return msg;
    }
}
