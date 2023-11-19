package edu.whu.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;

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
public class Anime implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 视频信息id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 视频名称
     */
    private String name;

    /**
     * 视频详细介绍信息
     */
    private String description;

    /**
     * 视频发布时间
     */
    @TableField("ani_initial_date")
    private String aniInitialDate;

    /**
     * 视频剧集数
     */
    @TableField("ani_total_episodes")
    private Integer aniTotalEpisodes;

    /**
     * 视频季
     */
    @TableField("ani_season")
    private Integer aniSeason;

    /**
     * 视频下载信息
     */
    @TableField("download_info_id")
    private Long downloadInfoId;

    /**
     * 订阅下载文件的过滤方法
     */
    @TableField("filter_id")
    private Long filterId;


}
