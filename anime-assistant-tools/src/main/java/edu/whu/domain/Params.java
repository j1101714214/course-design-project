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
public class Params implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 参数id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数的详细信息
     */
    private String description;

    /**
     * 参数类型
     */
    private String type;

    /**
     * params位置（0-path params, 1-query params, 2-body)
     */
    private Integer pos;

    /**
     * params对应api
     */
    @TableField("api_id")
    private Long apiId;

    /**
     * params对应api源
     */
    @TableField("db_id")
    private Integer dbId;


}
