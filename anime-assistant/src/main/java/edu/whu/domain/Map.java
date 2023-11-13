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
 * @since 2023-11-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Map implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 视频id
     */
    @TableId(value = "anime_id", type = IdType.AUTO)
    private Long animeId;

    /**
     * 分类信息id
     */
    @TableField("cate_id")
    private Long cateId;


}
