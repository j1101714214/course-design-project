package edu.whu.model.common.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Akihabara
 * @version 1.0
 * @description JobStatus: 任务状态枚举类
 * @date 2023/9/16 18:39
 */
public enum JobStatus {
    PENDING(1, "等待执行"),
    PROCESSING(2, "正在执行"),
    PROCESSED(4, "执行完毕");


    final String description;
    @EnumValue      // 与数据库字段相对应
    @JsonValue      // 与JSON前段返回字段相对应
    final Integer status;

    JobStatus(int status, String description) {
        this.status = status;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
