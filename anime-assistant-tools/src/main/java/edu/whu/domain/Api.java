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
 * @since 2023-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Api implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * api的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * api名称
     */
    private String name;

    /**
     * 调用方法（1-GET, 2-POST, 3-PUT, 4-DEL)
     */
    private Integer method;

    /**
     * 调用api的模板
     */
    private String template;

    /**
     * api的详细信息
     */
    private String description;

    private Long source;


}
