package edu.whu.model.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import edu.whu.model.common.enumerate.UserLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyUser: tb_users表对应的实体类
 * @date 2023/9/16 15:30
 */
@Data
@NoArgsConstructor
@TableName("public.tb_users")
public class XyUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 用户密码, 采用md5算法加密
     */
    @TableField("password")
    private String password;

    @TableField("user_level")
    private UserLevel userLevel;


}
