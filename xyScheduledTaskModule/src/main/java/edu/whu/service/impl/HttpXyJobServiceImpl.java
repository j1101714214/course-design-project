package edu.whu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.exception.CustomerException;
import edu.whu.mapper.XyJobMapper;
import edu.whu.model.common.enumerate.ExceptionEnum;
import edu.whu.model.common.enumerate.JobStatus;
import edu.whu.model.common.enumerate.UserLevel;
import edu.whu.model.job.pojo.XyJob;
import edu.whu.model.job.vo.AddJobVo;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyJobService;
import edu.whu.service.IXyUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
        xyJob.setIsDeleted(0);

        int cnt = jobMapper.insert(xyJob);
        return cnt == 1;
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
        return cnt == 1;
    }

    @Override
    public XyJob queryJobById(Long jobId) {
        return jobMapper.selectById(jobId);
    }

    @Override
    public Boolean deleteJob(Long jobId) {
        XyJob xyJob = queryJobById(jobId);
        XyUser operator = userService.findCurrentOperator();
        checkPermission(operator, xyJob);

        int cnt = jobMapper.deleteById(jobId);
        return cnt == 1;
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
