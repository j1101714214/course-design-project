package edu.whu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.whu.model.job.pojo.XyJob;
import edu.whu.model.log.pojo.XyLog;

/**
 * @author Akihabara
 * @version 1.0
 * @description IXyLogService: XyLog对应的服务层接口
 * @date 2023/10/6 20:07
 */
public interface IXyLogService extends IService<XyLog> {
    /**
     * 分页查询当前用户的错误日志信息
     * @param pageNum       日志页码
     * @param pageSize      每页条数
     * @param principal     当前用户登录信息
     * @return              当前用户的日志信息
     */
    IPage<XyLog> queryLogByUserId(Integer pageNum, Integer pageSize, Object principal);

    /**
     * 查询单条日志记录
     * @param principal     用户登录信息
     * @param logId         日志id
     * @return              日志记录
     */
    XyLog queryLogById(Object principal, Long logId);

    /**
     * 添加任务
     * @param msg           错误信息
     * @param job           日志关联任务
     * @return              操作结果
     */
    Boolean addLog(String msg, XyJob job);
}
