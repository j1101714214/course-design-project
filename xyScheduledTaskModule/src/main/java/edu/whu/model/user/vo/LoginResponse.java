package edu.whu.model.user.vo;

import edu.whu.model.common.enumerate.UserLevel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Akihabara
 * @version 1.0
 * @description LoginResponse: 封装返回参数
 * @date 2023/10/25 22:20
 */
@Data
@ApiModel(value = "登录操作返回值", description = "登录操作返回值")
public class LoginResponse {
    @ApiModelProperty(value = "JWT token")
    String token;
    @ApiModelProperty(value = "用户权限")
    UserLevel userLevel;
}
