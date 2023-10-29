package edu.whu.model.job.vo;

import edu.whu.annotation.InvokeMethodValidate;
import edu.whu.model.common.enumerate.InvokeMethod;
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
public class AddJobVo {
    @NotEmpty(message = "任务名不为空")
    private String jobName;
    @NotEmpty(message = "任务组别不能为空")
    private String jobGroup;
    @NotEmpty(message = "调用目标不能为空")
    private String invokeTarget;
    @NotEmpty(message = "cron表达式不能为空")
    private String cronExpression;
    @NotEmpty(message = "cron策略不能为空")
    private String misfirePolicy;
    private String concurrent;
    private String invokeParam = "";
    @InvokeMethodValidate(message = "请求方式为空")
    private InvokeMethod invokeMethod;
}
