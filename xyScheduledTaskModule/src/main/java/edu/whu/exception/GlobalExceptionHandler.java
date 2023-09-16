package edu.whu.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
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
     * @param request   请求
     * @param exception 异常
     * @return          异常响应实体
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> generalExceptionHandler(HttpServletRequest request, Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    /**
     * 自定义异常处理器
     * @param request   请求
     * @param exception 异常
     * @return          异常响应实体, 并设置不同的状态码
     */
    @ExceptionHandler(value = CustomerException.class)
    public ResponseEntity<String> customerExceptionHandler(HttpServletRequest request, CustomerException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(exception.getCode()).body(exception.getMessage());
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<String> handleBindException(HttpServletRequest request, BindException exception) {
        List<FieldError> errors = exception.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError errorMessage : errors) {
            sb.append(errorMessage.getField()).append(": ").append(errorMessage.getDefaultMessage()).append(", ");
        }
        return ResponseEntity.badRequest().body(sb.toString());
    }
}
