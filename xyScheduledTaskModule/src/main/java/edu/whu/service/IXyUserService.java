package edu.whu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.model.user.vo.LoginAndRegisterVo;

/**
 * @author Akihabara
 * @version 1.0
 * @description IXyUserService: XyUser对应的服务层接口
 * @date 2023/9/16 15:49
 */
public interface IXyUserService extends IService<XyUser> {
    /**
     * 通过用户名查询用户
     * @param username  用户名
     * @param isLogin   是否是登录操作: 因为注册操作需要验重, 借此参数区分登录和注册
     * @return          查询到的用户
     */
    XyUser findUserByUsername(String username, boolean isLogin);

    /**
     * 用户注册并保存用户信息到数据库
     * @param loginAndRegisterVo    待保存的用户信息
     * @return                      保存的用户信息
     */
    XyUser saveUser(LoginAndRegisterVo loginAndRegisterVo);

    /**
     * 删除用户
     * @param userId    待删除用户的id
     * @return          操作结果
     */
    Boolean deleteUser(Long userId);

    /**
     * 修改用户信息: 操作者能修改的用户等级不超过当前操作者的等级, 仅允许超级管理员和用户本身修改密码
     * @param userId    待修改用户的id
     * @param user      待修改用户的信息
     * @param principal 主线程传递的上下文信息
     * @return          操作结果
     */
    Boolean updateUser(Object principal, Long userId, XyUser user);

    /**
     * 根据用户id查询用户的详细信息
     * @param userId    待查询用户的id
     * @param principal 主线程传递的上下文信息
     * @return          该用户的详细信息
     */
    XyUser queryUserById(Object principal, Long userId);

    /**
     * 分页查询用户列表, 不包含条件查询部分
     * @param pageNum   当前页码
     * @param pageSize  每页条数
     * @return          用户列表
     */
    IPage<XyUser> queryUserList(Integer pageNum, Integer pageSize);

    /**
     * 获取当前操作者: 即当前操作系统的用户
     * @return      当前系统的操作者
     */
    XyUser findCurrentOperator(Object principal);

    /**
     * 根据用户id查询其邮箱地址
     * @param userId    用户id
     * @return          用户对应邮箱地址
     */
    XyUser findUserById(Long userId);
}
