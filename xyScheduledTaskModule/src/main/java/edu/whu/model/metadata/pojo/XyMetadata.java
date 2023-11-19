package edu.whu.model.metadata.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import edu.whu.model.common.enumerate.InvokeMethod;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyMetadata: 任务元数据表格关联类
 * @date 2023/11/14 18:43
 */
@Data
@ApiModel(value = "任务元数据类", description = "与tb_metadata表相对应的用户实体类")
@NoArgsConstructor
@TableName(value = "public.tb_metadata", autoResultMap = true)
public class XyMetadata implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "任务元数据id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 任务组名
     */
    @TableField("group_name")
    @ApiModelProperty(value = "任务组名")
    private String groupName;

    /**
     * 任务目标地址
     */
    @TableField("group_target")
    @ApiModelProperty(value = "任务目标地址")
    private String groupTarget;

    /**
     * 任务请求方式
     */
    @TableField("group_method")
    @ApiModelProperty(value = "任务请求方式")
    private InvokeMethod groupMethod;

    /**
     * 任务组描述
     */
    @TableField("group_desc")
    @ApiModelProperty(value = "任务组描述")
    private String groupDesc;

    /**
     * 任务路径参数描述
     */
    @TableField("group_paths")
    @ApiModelProperty(value = "任务路径参数描述")
    private String groupPaths;

    /**
     * 任务请求参数描述
     */
    @TableField("group_params")
    @ApiModelProperty(value = "任务请求参数描述")
    private String groupParams;
}
