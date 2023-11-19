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
public class Source implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 视频资源网站
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 视频网站名称
     */
    private String name;

    /**
     * 视频网站描述信息
     */
    private String description;

    /**
     * 网站链接
     */
    private String website;


}
