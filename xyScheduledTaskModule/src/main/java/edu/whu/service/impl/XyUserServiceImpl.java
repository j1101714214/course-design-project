package edu.whu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.exception.CustomerException;
import edu.whu.mapper.XyUserMapper;
import edu.whu.model.common.enumerate.ExceptionEnum;
import edu.whu.model.common.enumerate.UserLevel;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.model.user.vo.LoginAndRegisterVo;
import edu.whu.service.IXyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyUserServiceImpl: IXyUserService接口实现类
 * @date 2023/9/16 15:49
 */
@Slf4j
@Service
public class XyUserServiceImpl extends ServiceImpl<XyUserMapper, XyUser> implements IXyUserService {
    @Autowired
    private XyUserMapper xyUserMapper;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public XyUser findUserByUsername(String username, boolean isLogin) {
        LambdaQueryWrapper<XyUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(XyUser::getUsername, username);
        XyUser xyUser = xyUserMapper.selectOne(lqw);
        if(isLogin && ObjectUtil.isNull(xyUser)) {
            throw new CustomerException(ExceptionEnum.USER_NOT_EXIST);
        }
        return xyUser;
    }

    @Override
    @Transactional()
    public XyUser saveUser(LoginAndRegisterVo loginAndRegisterVo) {
        // 根据用户名查询用户, 如果用户存在则抛出异常
        XyUser userInDB = findUserByUsername(loginAndRegisterVo.getUsername(), false);
        if(ObjectUtil.isNotNull(userInDB)) {
            // 当前用户名已被注册
            throw new CustomerException(ExceptionEnum.USER_HAS_EXIST);
        }

        XyUser xyUser = new XyUser();
        BeanUtils.copyProperties(loginAndRegisterVo, xyUser);
        // 加密
        xyUser.setPassword(passwordEncoder.encode(xyUser.getPassword()));
        xyUser.setUserLevel(UserLevel.GUEST);

        int cnt = xyUserMapper.insert(xyUser);
        if(cnt != 1) {
            throw new CustomerException(ExceptionEnum.CANNOT_SAVE_USER);
        }
        return xyUser;
    }
}
