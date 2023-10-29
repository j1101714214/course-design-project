package edu.whu.model.common.enumerate;

import org.springframework.http.HttpStatus;

/**
 * @author Akihabara
 * @version 1.0
 * @description ExceptionEnum: 自定义异常枚举类
 * @date 2023/9/16 18:39
 */
public enum ExceptionEnum {
    /* 用户管理 */
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "当前用户不存在"),
    USER_HAS_EXIST(HttpStatus.BAD_REQUEST, "当前用户已经存在"),
    CANNOT_SAVE_USER(HttpStatus.BAD_REQUEST, "保存用户异常"),
    ILLEGAL_OPERATION(HttpStatus.BAD_REQUEST, "非法操作"),
    /* 任务管理 */
    JOB_NOT_EXIST(HttpStatus.BAD_REQUEST, "任务不存在"),
    TASK_CONFIG_ERROR(HttpStatus.BAD_REQUEST, "任务配置错误"),
    CRON_INVALID(HttpStatus.BAD_REQUEST, "错误的Cron表达式"),
    PAUSE_ERROR(HttpStatus.BAD_REQUEST, "挂起任务失败"),
    TASK_NOT_BE_PROCESSING(HttpStatus.BAD_REQUEST, "任务不在运行之中"),
    TASK_NOT_BE_PENDING(HttpStatus.BAD_REQUEST, "任务并未挂起"),
    TASK_NOT_DELETED(HttpStatus.BAD_REQUEST, "任务未被正常删除"),
    TASK_NOT_UPDATE(HttpStatus.BAD_REQUEST, "任务未被正常修改"),
    TASK_RUN_ERROR(HttpStatus.BAD_REQUEST, "任务执行失败"),
    ERROR_INVOKE_METHOD(HttpStatus.BAD_REQUEST, "未知的任务请求方式"),
    UNKNOWN_TASK(HttpStatus.BAD_REQUEST, "未知任务被调用"),
    /* 插件管理 */
    ERROR_INVOKE_PLUGIN(HttpStatus.BAD_REQUEST, "启动插件失败"),
    ERROR_PLUGIN_TYPE(HttpStatus.BAD_REQUEST, "错误插件类型"),

    /* 权限管理 */
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
