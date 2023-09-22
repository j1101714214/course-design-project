package edu.whu.model.common.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Akihabara
 * @version 1.0
 * @description UserLevel: 请求方式枚举类
 * @date 2023/9/16 15:37
 */
public enum InvokeMethod {
    HTTP_GET("GET"),
    HTTP_POST("POST"),
    HTTP_PUT("PUT"),
    HTTP_DELETE("DELETE");


    @EnumValue      // 与数据库字段相对应
    @JsonValue      // 与JSON前段返回字段相对应
    final String method;

    InvokeMethod(String method) {
        this.method = method;
    }


    public String getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return method;
    }
}
