package edu.whu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.exception.CustomerException;
import edu.whu.mapper.XyLogMapper;
import edu.whu.model.common.enumerate.ExceptionEnum;
import edu.whu.model.job.pojo.XyJob;
import edu.whu.model.log.pojo.XyLog;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyLogService;
import edu.whu.service.IXyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Akihabara
 * @version 1.0
 * @description XtLogServiceImpl: IXyLogService接口实现类
 * @date 2023/10/6 20:07
 */
@Service
public class XtLogServiceImpl extends ServiceImpl<XyLogMapper, XyLog> implements IXyLogService {
    @Autowired
    private IXyUserService userService;
    @Autowired
    private XyLogMapper logMapper;

    private static final int MAX_DESC_LENGTH = 20;

    @Override
    public IPage<XyLog> queryLogByUserId(Integer pageNum, Integer pageSize, Object principal) {
        XyUser operator = userService.findCurrentOperator(principal);
        Long userId = operator.getId();
        if(ObjectUtil.isNull(operator)) {
            throw new CustomerException(ExceptionEnum.USER_NOT_LOGIN);
        }

        IPage<XyLog> page = new Page<>(pageNum - 1, pageSize);
        LambdaQueryWrapper<XyLog> lqw = new LambdaQueryWrapper<>();
        lqw.eq(XyLog::getUserId, userId);

        page = logMapper.selectPage(page, lqw);
        // 截取最大长度
        page.getRecords().forEach(log -> {
            log.setLogDesc(log.getLogDesc()
                    .substring(0, Math.min(MAX_DESC_LENGTH, log.getLogDesc().length())));
        });
        return page;
    }

    @Override
    public XyLog queryLogById(Object principal, Long logId) {
        XyUser operator = userService.findCurrentOperator(principal);
        XyLog xyLog = logMapper.selectById(logId);
        if(ObjectUtil.notEqual(operator.getId(), xyLog.getUserId())) {
            throw new CustomerException(ExceptionEnum.UN_AUTHORIZED);
        }
        return xyLog;
    }

    @Override
    public Boolean addLog(String msg, XyJob job) {
        XyLog xyLog = new XyLog();
        xyLog.setLogTime(Timestamp.valueOf(LocalDateTime.now()));
        xyLog.setUserId(job.getCreateUser());
        xyLog.setTaskId(job.getId());
        xyLog.setLogDesc(msg);

        return logMapper.insert(xyLog) == 1;
    }


}


