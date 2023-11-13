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
 * @since 2023-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Filter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 过滤器id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 过滤器名称
     */
    private String name;

    private String origin;

    /**
     * 替换目标
     */
    private String dest;

    /**
     * 包含字段
     */
    private String include;

    /**
     * 排除字段
     */
    private String exclude;


}
