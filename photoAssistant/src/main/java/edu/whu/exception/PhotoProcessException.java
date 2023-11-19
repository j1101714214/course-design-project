package edu.whu.exception;

import lombok.Data;
import lombok.Getter;

/**
 * @author Akihabara
 * @version 1.0
 * @description PhotoProcessException: 照片处理异常类
 * @date 2023/11/11 15:19
 */
public class PhotoProcessException extends RuntimeException{
    private final String msg;
    public PhotoProcessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
