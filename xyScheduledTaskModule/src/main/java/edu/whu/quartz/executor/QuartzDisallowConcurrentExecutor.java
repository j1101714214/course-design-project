package edu.whu.quartz.executor;

import edu.whu.exception.TaskException;
import edu.whu.model.common.enumerate.ExceptionEnum;
import edu.whu.model.job.pojo.XyJob;
import edu.whu.quartz.AbstractQuartzJob;
import edu.whu.service.IRestTemplateService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Akihabara
 * @version 1.0
 * @description QuartzDisallowConcurrentExecutor: 定时任务处理, 禁止并发执行
 * @date 2023/9/20 22:13
 */
@Component
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecutor extends AbstractQuartzJob {
    @Autowired
    private IRestTemplateService restTemplateService;
    @Override
    protected void doExecute(JobExecutionContext jobExecutionContext, XyJob xyJob) {
        switch (xyJob.getInvokeMethod()) {
            case HTTP_GET:
                restTemplateService.doGet(xyJob);
                break;
            case HTTP_POST:
                restTemplateService.doPost(xyJob);
                break;
            case HTTP_PUT:
                restTemplateService.doPut(xyJob);
                break;
            case HTTP_DELETE:
                restTemplateService.doDelete(xyJob);
                break;
            default:
                throw new TaskException(xyJob.getId(), ExceptionEnum.ERROR_INVOKE_METHOD);
        }
    }
}
