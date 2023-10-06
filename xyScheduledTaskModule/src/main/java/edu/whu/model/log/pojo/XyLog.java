package edu.whu.model.log.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyLog: 任务发生错误的日志记录
 * @date 2023/10/6 20:02
 */
@Data
@ApiModel(value = "日志实体类", description = "与tb_logs表相对应的用户实体类")
@NoArgsConstructor
@TableName(value = "public.tb_logs", autoResultMap = true)
public class XyLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "log_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "日志id")
    private Long id;

    /**
     * 用户id
     */
    @TableField("log_user")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 任务id
     */
    @TableField("log_task")
    @ApiModelProperty(value = "任务id")
    private Long taskId;

    /**
     * 错误发生时间
     */
    @TableField("log_time")
    @ApiModelProperty(value = "错误发生时间")
    private Timestamp logTime;

    /**
     * 错误描述
     */
    @TableField("log_desc")
    @ApiModelProperty(value = "错误描述")
    private String logDesc;
}
