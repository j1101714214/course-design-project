package edu.whu.model.common.enumerate;

import org.springframework.http.HttpStatus;

/**
 * @author Akihabara
 * @version 1.0
 * @description ExceptionEnum: 自定义异常枚举类
 * @date 2023/9/16 18:39
 */
public enum ExceptionEnum {
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "当前用户不存在"),
    USER_HAS_EXIST(HttpStatus.BAD_REQUEST, "当前用户已经存在"),
    CANNOT_SAVE_USER(HttpStatus.BAD_REQUEST, "保存用户异常"),
    ILLEGAL_OPERATION(HttpStatus.BAD_REQUEST, "非法操作"),
    JOB_NOT_EXIST(HttpStatus.BAD_REQUEST, "任务不存在"),
    USER_NOT_LOGIN(HttpStatus.UNAUTHORIZED, "用户未登录"),
    UN_AUTHORIZED(HttpStatus.UNAUTHORIZED, "当前用户未授权"),
    INSUFFICIENT_PERMISSION(HttpStatus.UNAUTHORIZED, "用户权限不足");

    private HttpStatus code;
    private String message;

    public HttpStatus getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    ExceptionEnum(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}
