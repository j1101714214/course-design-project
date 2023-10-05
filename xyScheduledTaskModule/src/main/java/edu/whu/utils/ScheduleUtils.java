package edu.whu.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.cron.CronUtil;
import edu.whu.exception.CustomerException;
import edu.whu.exception.TaskException;
import edu.whu.model.common.constant.ScheduleConstants;
import edu.whu.model.common.enumerate.ExceptionEnum;
import edu.whu.model.common.enumerate.JobStatus;
import edu.whu.model.job.pojo.XyJob;
import edu.whu.quartz.executor.QuartzDisallowConcurrentExecutor;
import edu.whu.quartz.executor.QuartzJobExecutor;
import org.quartz.*;

/**
 * @author Akihabara
 * @version 1.0
 * @description ScheduleUtils: 任务调度工具类
 * @date 2023/9/20 22:11
 */
public class ScheduleUtils {
    /**
     * 获取quartz任务处理器类型
     * @param xyJob 执行任务
     * @return      具体执行任务类
     */
    private static Class<? extends Job> getQuartzJobClass(XyJob xyJob) {
        return (xyJob.getConcurrent().equals(XyJob.ENABLE_CONCURRENT))?
                QuartzJobExecutor.class : QuartzDisallowConcurrentExecutor.class;
    }

    public static JobKey getJobKey(Long jobId, String jogGroup) {
        return JobKey.jobKey(
                ScheduleConstants.TASK_CLASS_NAME + jobId, jogGroup
        );
    }

    public static TriggerKey getTryggerKey(Long jobId, String jogGroup) {
        return TriggerKey.triggerKey(
                ScheduleConstants.TASK_TRIGGER_NAME + jobId, jogGroup
        );
    }

    public static void createScheduleJob(Scheduler scheduler, XyJob xyJob) {
        Class<? extends Job> jobClass = getQuartzJobClass(xyJob);
        // 构架jobDetail信息
        Long jobId = xyJob.getId();
        String jobGroup = xyJob.getJobGroup();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

        // cron表达式调度构建起
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(xyJob.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(xyJob, cronScheduleBuilder);

        // 构建定时任务的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTryggerKey(jobId, jobGroup))
                .withSchedule(cronScheduleBuilder).build();
        // 将任务实体放入jobDetail之中
        jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, xyJob);

        // 判断当前任务是否存在
        try {
            if(scheduler.checkExists(getJobKey(jobId, jobGroup))) {
                scheduler.deleteJob(getJobKey(jobId, jobGroup));
            }
            // 如果任务过期则调度任务
            if(ObjectUtil.isNotNull(CronUtils.getNextExecution(xyJob.getCronExpression()))) {
                // 任务调度
                scheduler.scheduleJob(jobDetail, trigger);
            }
            if(ObjectUtil.equal(xyJob.getStatus(), JobStatus.PENDING)) {
                scheduler.pauseJob(getJobKey(jobId, jobGroup));
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置定时任务策略
     * @param xyJob     任务详情
     * @param cronScheduleBuilder   定时任务处理器构建器
     * @return          添加任务策略之后的任务处理器构建器
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(XyJob xyJob, CronScheduleBuilder cronScheduleBuilder) {
        switch (xyJob.getMisfirePolicy()) {
            case ScheduleConstants.MISFIRE_DEFAULT:
                return cronScheduleBuilder;
            case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
                return cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
                return cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstants.MISFIRE_DO_NOTHING:
                return cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException(xyJob.getId(), ExceptionEnum.TASK_CONFIG_ERROR);
        }
    }

}
