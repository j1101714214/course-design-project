package edu.whu.model.job.vo;

import edu.whu.annotation.InvokeMethodValidate;
import edu.whu.model.common.enumerate.InvokeMethod;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author Akihabara
 * @version 1.0
 * @description AddJobVo: 添加和修改任务详情的视图对象
 * @date 2023/9/20 20:36
 */
@Data
@ApiModel(value = "添加任务VO类", description = "添加任务时需要传入的参数")
public class AddJobVo {
    @NotEmpty(message = "任务名不为空")
    @ApiModelProperty(value = "任务名")
    private String jobName;
    @NotEmpty(message = "任务组别不能为空")
    @ApiModelProperty(value = "任务组名")
    private String jobGroup;
    @NotEmpty(message = "调用目标不能为空")
    @ApiModelProperty(value = "调用目标字符串", example = "http:localhost:8080/get/lisi")
    private String invokeTarget;
    @NotEmpty(message = "cron表达式不能为空")
    @ApiModelProperty(value = "cron执行表达式")
    private String cronExpression;
    @NotEmpty(message = "cron策略不能为空")
    @ApiModelProperty(value = "cron计划策略")
    private String misfirePolicy;
    @ApiModelProperty(value = "是否允许并发执行", example = "'0', '1'")
    private String concurrent;
    @ApiModelProperty(value = "请求参数")
    private String invokeParam = "";
    @InvokeMethodValidate(message = "请求方式为空")
    @ApiModelProperty(value = "请求方式")
    private InvokeMethod invokeMethod;
}
