package edu.whu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ExceptionHandler(value = PhotoProcessException.class)
    public ResponseEntity<String> accessDdeniedExceptionHandler(PhotoProcessException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("图片处理插件处理错误");
    }
}
