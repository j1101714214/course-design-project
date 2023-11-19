package edu.whu.model.plugin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyPlugin: 插件实体类
 * @date 2023/9/27 21:22
 */
@Data
@ApiModel(value = "插件实体类", description = "与tb_plugins表相对应的用户实体类")
@NoArgsConstructor
@TableName("public.tb_plugins")
public class XyPlugin implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "plugin_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "插件id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 插件名
     */
    @TableField("plugin_name")
    @ApiModelProperty(value = "插件名")
    private String pluginName;

    /**
     * 插件名
     */
    @TableField("plugin_filename")
    @ApiModelProperty(value = "插件文件名")
    private String pluginFilename;

    /**
     * 插件描述
     */
    @TableField("plugin_desc")
    @ApiModelProperty(value = "插件描述")
    private String pluginDesc;

    /**
     * 插件下载路径
     */
    @TableField("plugin_url")
    @ApiModelProperty(value = "插件下载路径")
    private String pluginUrl;

    /**
     * 插件版本号
     */
    @TableField("plugin_version")
    @ApiModelProperty(value = "插件版本号")
    private String pluginVersion;
}
