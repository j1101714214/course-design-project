package edu.whu.exception;

import edu.whu.model.common.enumerate.ExceptionEnum;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Akihabara
 * @version 1.0
 * @description CustomerException: 自定义异常处理
 * @date 2023/9/16 18:27
 */
@Data
public class CustomerException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String message;
    private HttpStatus code;
    public CustomerException(String message) {
        super(message);
        this.message = message;
        this.code = HttpStatus.BAD_REQUEST;
    }

    public CustomerException(String message, HttpStatus code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public CustomerException(ExceptionEnum exception) {
        this(exception.getMessage(), exception.getCode());
    }
}
