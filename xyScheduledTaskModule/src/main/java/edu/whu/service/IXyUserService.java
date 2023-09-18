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
     * @return          操作结果
     */
    Boolean updateUser(Long userId, XyUser user);

    /**
     * 根据用户id查询用户的详细信息
     * @param userId    待查询用户的id
     * @return          该用户的详细信息
     */
    XyUser queryUserById(Long userId);

    /**
     * 分页查询用户列表, 不包含条件查询部分
     * @param pageNum   当前页码
     * @param pageSize  每页条数
     * @return          用户列表
     */
    IPage<XyUser> queryUserList(Integer pageNum, Integer pageSize);
}
