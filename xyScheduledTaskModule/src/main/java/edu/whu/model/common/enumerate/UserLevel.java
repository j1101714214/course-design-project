package edu.whu.model.common.enumerate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Akihabara
 * @version 1.0
 * @description UserLevel: 用户角色枚举类
 * @date 2023/9/16 15:37
 */
public enum UserLevel {
    GUEST(1, "游客"),
    USER(2, "普通用户"),
    ADV_USER(3, "高级用户"),
    ADMIN(6, "管理员"),
    SYS_ADMIN(7, "系统管理员");

    @EnumValue      // 与数据库字段相对应
    final int level;

    @JsonValue      // 与JSON前段返回字段相对应
    final String description;

    UserLevel(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
