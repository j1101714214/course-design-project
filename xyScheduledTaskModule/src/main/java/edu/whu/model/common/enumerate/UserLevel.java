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
    GUEST(1, "guest"),
    USER(2, "user"),
    ADV_USER(3, "adv_user"),
    ADMIN(6, "admin"),
    SYS_ADMIN(7, "sys_admin");

    @EnumValue      // 与数据库字段相对应
    @JsonValue      // 与JSON前段返回字段相对应
    final int level;

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
