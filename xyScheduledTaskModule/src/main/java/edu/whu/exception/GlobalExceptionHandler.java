package edu.whu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author Akihabara
 * @version 1.0
 * @description GlobalExceptionHandler: 全局异常处理器
 * @date 2023/9/16 18:23
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 通用异常处理器
     * @param exception 异常
     * @return          异常响应实体
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> generalExceptionHandler(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    /**
     * 通用异常处理器
     * @param exception 异常
     * @return          异常响应实体
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<String> accessDdeniedExceptionHandler(AccessDeniedException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("权限不足");
    }

    /**
     * 自定义异常处理器
     * @param exception 异常
     * @return          异常响应实体, 并设置不同的状态码
     */
    @ExceptionHandler(value = CustomerException.class)
    public ResponseEntity<String> customerExceptionHandler(CustomerException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(exception.getCode()).body(exception.getMessage());
    }

    /**
     * spring-validation框架对于参数校验的异常处理器
     * @param exception 异常
     * @return          异常响应实体, 并设置不同的状态码
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<String> handleBindException(BindException exception) {
        List<FieldError> errors = exception.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError errorMessage : errors) {
            sb.append(errorMessage.getField()).append(": ").append(errorMessage.getDefaultMessage()).append(", ");
        }
        return ResponseEntity.badRequest().body(sb.toString());
    }

    /**
     * spring-validation框架对于参数校验的异常处理器
     * @param exception 异常
     * @return          异常响应实体, 并设置不同的状态码
     */
    @ExceptionHandler(value = TaskException.class)
    public ResponseEntity<String> handleTaskException(TaskException exception) {
        String message = exception.getMessage();
        Long jobId = exception.getJobId();
        return ResponseEntity.status(exception.getCode()).body(jobId + ":" + message);
    }

    /**
     * 处理邮箱正则匹配错误
     * @param exception 正则匹配错误处理器
     * @return          异常响应实体, 并设置不同的状态码
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
