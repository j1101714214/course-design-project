package edu.whu.service;

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
}
