package edu.whu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.whu.model.job.pojo.XyJob;
import edu.whu.model.job.vo.AddJobVo;

/**
 * @author Akihabara
 * @version 1.0
 * @description IXyJobService: XyJob对应的服务层接口
 * @date 2023/9/20 20:21
 */
public interface IXyJobService extends IService<XyJob> {
    /**
     * 挂起任务
     * @param jobId 任务详情
     * @return      操作结果
     */
    Boolean pauseJob(Long jobId);

    /**
     * 恢复任务
     * @param jobId 任务详情
     * @return      操作结果
     */
    Boolean resumeJob(Long jobId);

    /**
     * 立即执行一次任务
     * @param jobId 任务详情
     * @return      操作结果
     */
    Boolean runOnce(Long jobId);
    /**
     * 分页查询任务列表, 仅管理员及以上级别用户可以使用, 可以查出所有用的的任务
     * @param pageNum   页码
     * @param pageSize  每页条数
     * @return          当前页面的任务信息
     */
    IPage<XyJob> queryUserList(Integer pageNum, Integer pageSize);

    /**
     * 查询当前用户的任务列表
     * @param pageNum   页码
     * @param pageSize  每条页数
     * @param userId    当前页面的当前登录用户的信息
     * @param principal 当前操作人
     * @return          当前用户的任务信息
     */
    IPage<XyJob> queryJobListByUser(Object principal, Integer pageNum, Integer pageSize, Long userId);

    /**
     * 将任务添加到数据库之中
     * @param addJobVo  任务详情
     * @param principal 当前操作人
     * @return          操作结果
     */
    Boolean addJob(Object principal, AddJobVo addJobVo);

    /**
     * 修改指定用户的指定任务
     * @param addJobVo  修改任务的信息
     * @param jobId     任务id
     * @param principal 当前操作人
     * @return          操作结果
     */
    Boolean updateJob(Object principal, AddJobVo addJobVo, Long jobId);

    /**
     * 根据任务id查询任务
     * @param jobId     任务id
     * @return          相关的任务详情
     */
    XyJob queryJobById(Long jobId);

    /**
     * 删除指定任务
     * @param jobId 任务id
     * @param principal 当前操作人
     * @return      相关任务详情
     */
    Boolean deleteJob(Object principal, Long jobId);

    void startTasksByUserId(Long userId);
}
