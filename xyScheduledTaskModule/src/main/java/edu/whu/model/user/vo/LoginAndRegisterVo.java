package edu.whu.model.user.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author Akihabara
 * @version 1.0
 * @description LoginAndRegisterVo: 登录和注册视图模型
 * @date 2023/9/16 19:09
 */
@Data
public class LoginAndRegisterVo {
    // 邮箱正则匹配
    private final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$";
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
    @Pattern(regexp = EMAIL_REGEX)
    private String email;
}
