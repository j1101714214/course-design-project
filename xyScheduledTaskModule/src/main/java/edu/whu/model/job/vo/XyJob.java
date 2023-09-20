package edu.whu.model.job.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import edu.whu.model.common.enumerate.JobStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyJob: TODO
 * @date 2023/9/20 20:06
 */
@Data
@ApiModel(value = "任务实体类", description = "与tb_jobs表相对应的用户实体类")
@NoArgsConstructor
@TableName("public.tb_job")
public class XyJob implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "job_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "任务id")
    private Long id;

    /**
     * 任务名
     */
    @TableField("job_name")
    @ApiModelProperty(value = "任务名")
    private String jobName;

    /**
     * 任务组
     */
    @TableField("job_group")
    @ApiModelProperty(value = "任务组名")
    private String jobGroup;

    /**
     * 调用目标字符串: 暂定为API接口调用
     */
    @TableField("invoke_target")
    @ApiModelProperty(value = "调用目标字符串")
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    @TableField("cron_expression")
    @ApiModelProperty(value = "cron执行表达式")
    private String cronExpression;

    /**
     * cron计划策略
     */
    @TableField("misfire_policy")
    @ApiModelProperty(value = "cron计划策略")
    private String misfirePolicy;

    /**
     * 是否允许并发执行
     */
    @TableField("concurrent")
    @ApiModelProperty(value = "是否允许并发执行")
    private Boolean concurrent;

    /**
     * 创建人Id
     */
    @TableField("create_user")
    @ApiModelProperty(value = "创建人Id")
    private Long createUser;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    /**
     * 修改人
     */
    @TableField("update_user")
    @ApiModelProperty(value = "修改人")
    private Long updateUser;

    /**
     * 修改时间
     */
    @TableField("update_time")
    @ApiModelProperty(value = "修改时间")
    private Timestamp updateTime;

    /**
     * 任务执行状态
     */
    @TableField("status")
    @ApiModelProperty(value = "任务执行状态")
    private JobStatus status;

    /**
     * 任务是否已删除
     */
    @TableField("is_deleted")
    @ApiModelProperty(value = "任务是否已删除")
    private Boolean isDeleted;
}
