package edu.whu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yang hengyi
 * @since 2023-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Authorization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     * db源api密钥
     */
    @TableField("api_key")
    private String apiKey;

    /**
     * db源api访问令牌
     */
    @TableField("api_token")
    private String apiToken;

    /**
     * db源
     */
    @TableId(value = "source", type = IdType.ASSIGN_ID)
    private Integer source;


}
