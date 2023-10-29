package edu.whu.model.user.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Akihabara
 * @version 1.0
 * @description LoginAndRegisterVo: 登录和注册视图模型
 * @date 2023/9/16 19:09
 */
@Data
public class LoginAndRegisterVo {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
}
