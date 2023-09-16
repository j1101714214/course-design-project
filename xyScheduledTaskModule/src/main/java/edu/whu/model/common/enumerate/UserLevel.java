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
    GUEST(1, "ROLE_GUEST"),
    USER(2, "ROLE_USER"),
    ADV_USER(3, "ROLE_ADV_USER"),
    ADMIN(6, "ROLE_ADMIN"),
    SYS_ADMIN(7, "ROLE_SYS_ADMIN");


    final int level;
    @EnumValue      // 与数据库字段相对应
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
