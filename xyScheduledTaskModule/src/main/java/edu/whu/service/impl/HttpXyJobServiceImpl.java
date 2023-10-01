package edu.whu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.exception.CustomerException;
import edu.whu.exception.TaskException;
import edu.whu.mapper.XyJobMapper;
import edu.whu.model.common.constant.ScheduleConstants;
import edu.whu.model.common.enumerate.ExceptionEnum;
import edu.whu.model.common.enumerate.JobStatus;
import edu.whu.model.common.enumerate.UserLevel;
import edu.whu.model.job.pojo.XyJob;
import edu.whu.model.job.vo.AddJobVo;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyJobService;
import edu.whu.service.IXyUserService;
import edu.whu.utils.ScheduleUtils;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Akihabara
 * @version 1.0
 * @description HttpXyJobServiceImpl: IXyUserService接口http实现类
 * @date 2023/9/20 20:22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HttpXyJobServiceImpl extends ServiceImpl<XyJobMapper, XyJob> implements IXyJobService {
    @Autowired
    private XyJobMapper jobMapper;
    @Autowired
    private IXyUserService userService;
    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void init() throws SchedulerException {
        // 在服务器启动之后读取所有数据加入任务调度之中(等用户登录之后在启动用户计划)
        scheduler.clear();
        scheduler.start();
    }

    @Override
    public Boolean pauseJob(Long jobId) {
        XyJob xyJob = queryJobById(jobId);
        String jobGroup = xyJob.getJobGroup();
        if(ObjectUtil.notEqual(xyJob.getStatus(), JobStatus.PROCESSING)) {
            // 任务不在执行状态
            throw new CustomerException(ExceptionEnum.TASK_NOT_BE_PROCESSING);
        }
        xyJob.setStatus(JobStatus.PENDING);

        boolean flag = super.updateById(xyJob);// 更新数据库信息
        try {
            if(flag) {
                scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
            }
            return flag;
        } catch (SchedulerException e) {
            throw new TaskException(jobId, ExceptionEnum.PAUSE_ERROR);
        }
    }

    @Override
    public Boolean resumeJob(Long jobId) {
        XyJob xyJob = queryJobById(jobId);
        String jobGroup = xyJob.getJobGroup();
        if(ObjectUtil.notEqual(xyJob.getStatus(), JobStatus.PENDING)) {
            // 任务不在执行状态
            throw new CustomerException(ExceptionEnum.TASK_NOT_BE_PENDING);
        }
        xyJob.setStatus(JobStatus.PROCESSING);

        boolean flag = super.updateById(xyJob);// 更新数据库信息
        try {
            if(flag) {
                scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
            }
            return flag;
        } catch (SchedulerException e) {
            throw new TaskException(jobId, ExceptionEnum.PAUSE_ERROR);
        }
    }

    @Override
    public Boolean runOnce(Long jobId) {
        XyJob xyJob = queryJobById(jobId);
        String jobGroup = xyJob.getJobGroup();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(ScheduleConstants.TASK_PROPERTIES, xyJob);
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        try {
            if(scheduler.checkExists(jobKey)) {
                scheduler.triggerJob(jobKey, jobDataMap);
            }
            return true;
        } catch (SchedulerException e) {
            throw new TaskException(jobId, ExceptionEnum.TASK_RUN_ERROR);
        }
    }

    @Override
    public IPage<XyJob> queryUserList(Integer pageNum, Integer pageSize) {
        IPage<XyJob> page = new Page<>(pageNum - 1, pageSize);
        page = jobMapper.selectPage(page, null);
        return page;
    }

    @Override
    public IPage<XyJob> queryJobListByUser(Integer pageNum, Integer pageSize, Long userId) {
        XyUser operator = userService.findCurrentOperator();
        if(ObjectUtil.isNull(operator)) {
            throw new CustomerException(ExceptionEnum.USER_NOT_LOGIN);
        }

        if(ObjectUtil.notEqual(userId, operator.getId())) {
            throw new CustomerException(ExceptionEnum.UN_AUTHORIZED);
        }

        IPage<XyJob> page = new Page<>(pageNum - 1, pageSize);
        LambdaQueryWrapper<XyJob> lqw = new LambdaQueryWrapper<>();
        lqw.eq(XyJob::getCreateUser, userId);
        page = jobMapper.selectPage(page, lqw);
        return page;
    }

    @Override
    public Boolean addJob(AddJobVo addJobVo) {
        XyUser operator = userService.findCurrentOperator();
        if(ObjectUtil.isNull(operator)) {
            throw new CustomerException(ExceptionEnum.USER_NOT_LOGIN);
        }
        XyJob xyJob = new XyJob();
        BeanUtils.copyProperties(addJobVo, xyJob);
        if(StrUtil.isNullOrUndefined(xyJob.getConcurrent())) {
            xyJob.setJobName(XyJob.DISABLE_CONCURRENT);
        }
        // 设置默认信息
        xyJob.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        xyJob.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        xyJob.setCreateUser(operator.getId());
        xyJob.setUpdateUser(operator.getId());
        xyJob.setStatus(JobStatus.PENDING);

        int cnt = jobMapper.insert(xyJob);
        boolean flag = cnt == 1;
        if(flag) {
            ScheduleUtils.createScheduleJob(scheduler, xyJob);
        }
        return flag;
    }

    @Override
    public Boolean updateJob(AddJobVo addJobVo, Long jobId) {
        XyJob xyJob = queryJobById(jobId);
        XyUser operator = userService.findCurrentOperator();
        checkPermission(operator, xyJob);


        BeanUtils.copyProperties(addJobVo, xyJob);
        // 设置默认信息
        xyJob.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        xyJob.setUpdateUser(operator.getId());

        int cnt = jobMapper.update(xyJob, null);
        boolean flag = cnt == 1;
        try {
            if(flag) {
                scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, xyJob.getJobGroup()));
            }
            ScheduleUtils.createScheduleJob(scheduler, xyJob);
            return flag;
        } catch (SchedulerException e) {
            throw new CustomerException(ExceptionEnum.TASK_NOT_UPDATE);
        }
    }

    @Override
    public XyJob queryJobById(Long jobId) {
        return jobMapper.selectById(jobId);
    }

    @Override
    public Boolean deleteJob(Long jobId) {
        XyJob xyJob = queryJobById(jobId);
        String jobGroup = xyJob.getJobGroup();
        XyUser operator = userService.findCurrentOperator();
        checkPermission(operator, xyJob);

        int cnt = jobMapper.deleteById(jobId);
        try {
            boolean flag = cnt == 1;
            if(flag) {
                scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
            }
            return flag;
        } catch (SchedulerException e) {
            throw new CustomerException(ExceptionEnum.TASK_NOT_DELETED);
        }
    }

    @Override
    @Async
    public void startTasksByUserId(Long userId) {
        XyUser operator = userService.findCurrentOperator();
        LambdaQueryWrapper<XyJob> lqw = new LambdaQueryWrapper<>();
        lqw.select(XyJob::getCreateUser).eq(XyJob::getCreateUser, operator.getId());

        List<XyJob> xyJobs = jobMapper.selectList(lqw);
        for (XyJob job : xyJobs) {
            if(ObjectUtil.notEqual(job.getStatus(), JobStatus.PROCESSED)) {
                ScheduleUtils.createScheduleJob(scheduler, job);
            }
        }
    }

    private void checkPermission(XyUser operator, XyJob xyJob) {
        if(ObjectUtil.isNull(xyJob)) {
            throw new CustomerException(ExceptionEnum.JOB_NOT_EXIST);
        }
        if(
                ObjectUtil.notEqual(xyJob.getCreateUser(), operator.getId()) ||
                        operator.getUserLevel().getLevel() <= UserLevel.ADV_USER.getLevel()
        ) {
            throw new CustomerException(ExceptionEnum.UN_AUTHORIZED);
        }
    }
}