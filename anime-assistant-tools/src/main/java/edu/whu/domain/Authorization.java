package edu.whu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Authorization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long user_id;

    /**
     * db源api密钥
     */
    private String api_key;

    /**
     * db源api访问令牌
     */
    private String api_token;

    /**
     * db源
     */
    private Integer source;


}
