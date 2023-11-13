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
public class Download implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 下载信息id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 搜索名称
     */
    @TableField("search_name")
    private String searchName;

    /**
     * 订阅方法
     */
    @TableField("sub_method")
    private Integer subMethod;

    /**
     * 下载方法
     */
    @TableField("download_method")
    private Integer downloadMethod;

    /**
     * 下载路径
     */
    private String dest;

    /**
     * 最新剧集
     */
    @TableField("last_episode")
    private Integer lastEpisode;


}
