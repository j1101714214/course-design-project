package edu.whu.model.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import edu.whu.model.common.enumerate.UserLevel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyUser: tb_users表对应的实体类
 * @date 2023/9/16 15:30
 */
@Data
@ApiModel(value = "用户实体类", description = "与tb_user表相对应的用户实体类")
@NoArgsConstructor
@TableName("public.tb_users")
public class XyUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "用户id")
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 用户密码, 采用md5算法加密
     */
    @TableField("password")
    @ApiModelProperty(value = "用户密码, 密文存储在数据库中")
    private String password;

    @TableField("user_level")
    @ApiModelProperty(value = "用户权限")
    private UserLevel userLevel;


}
