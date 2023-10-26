package edu.whu.exception;

import edu.whu.model.common.enumerate.ExceptionEnum;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Akihabara
 * @version 1.0
 * @description TaskException: 任务相关异常类
 * @date 2023/9/21 18:41
 */
public class TaskException extends CustomerException{
    private final Long jobId;

    public TaskException(Long jobId, ExceptionEnum exceptionEnum) {
        super(exceptionEnum);
        this.jobId = jobId;
    }

    public Long getJobId() {
        return jobId;
    }
}
