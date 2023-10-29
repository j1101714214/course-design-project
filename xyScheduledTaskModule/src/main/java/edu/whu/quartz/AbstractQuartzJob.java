package edu.whu.quartz;

import cn.hutool.core.util.ObjectUtil;
import edu.whu.exception.CustomerException;
import edu.whu.exception.TaskException;
import edu.whu.model.common.constant.ScheduleConstants;
import edu.whu.model.common.enumerate.ExceptionEnum;
import edu.whu.model.job.pojo.XyJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author Akihabara
 * @version 1.0
 * @description AbstractQuartzJob: quartz框架调用任务使用的抽象类
 * @date 2023/9/20 21:21
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {
    private static final ThreadLocal<Date> threadLocal = new ThreadLocal<>();
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        XyJob xyJob = (XyJob) jobExecutionContext.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES);
        try {
            before(jobExecutionContext, xyJob);
            if(xyJob != null) {
                doExecute(jobExecutionContext, xyJob);
            }
            after(jobExecutionContext, xyJob);
        } catch (Exception exception) {
            if(ObjectUtil.isEmpty(xyJob) || ObjectUtil.isEmpty(xyJob.getId())) {
                throw new CustomerException(ExceptionEnum.UNKNOWN_TASK);
            }
            throw new TaskException(xyJob.getId(), ExceptionEnum.TASK_RUN_ERROR);
        }
    }

    protected void after(JobExecutionContext jobExecutionContext, XyJob xyJob) {
        Date date = threadLocal.get();
        threadLocal.remove();
    }

    /**
     * 执行方法, 子类重载
     * @param jobExecutionContext   工作执行上下文对象
     * @param xyJob                 工作详情
     */
    protected abstract void doExecute(JobExecutionContext jobExecutionContext, XyJob xyJob);

    /**
     * 执行前处理
     * @param jobExecutionContext   任务上下文对象
     * @param xyJob                 任务详情
     */
    protected void before(JobExecutionContext jobExecutionContext, XyJob xyJob) {
        threadLocal.set(new Date());
    }
}
