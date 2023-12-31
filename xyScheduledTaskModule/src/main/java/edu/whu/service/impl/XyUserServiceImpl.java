package edu.whu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


/**
 * @author Akihabara
 * @version 1.0
 * @description XyUserServiceImpl: IXyUserService接口实现类
 * @date 2023/9/16 15:49
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class XyUserServiceImpl extends ServiceImpl<XyUserMapper, XyUser> implements IXyUserService {
    @Autowired
    private XyUserMapper xyUserMapper;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Override
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

    @Override
    public Boolean deleteUser(Long userId) {
        int cnt = xyUserMapper.deleteById(userId);
        return cnt == 1;
    }



    @Override
    public Boolean updateUser(Object principal, Long userId, XyUser user) {
        XyUser operator = findCurrentOperator(principal);
        if(operator == null) {
            throw new CustomerException(ExceptionEnum.USER_NOT_LOGIN);
        }

        UserLevel operatorUserLevel = operator.getUserLevel();
        UserLevel userUpdateLevel = user.getUserLevel();

        if(ObjectUtil.isNotNull(operatorUserLevel) && ObjectUtil.isNotNull(userUpdateLevel)) {
            if(operatorUserLevel.getLevel() < userUpdateLevel.getLevel()) {
                throw new CustomerException(ExceptionEnum.INSUFFICIENT_PERMISSION);
            }
        } else if(ObjectUtil.isNull(operatorUserLevel)) {
            throw new CustomerException(ExceptionEnum.UN_AUTHORIZED);
        }

        Long operatorId = operator.getId();
        if(!ObjectUtil.equal(operatorId, userId) && UserLevel.SYS_ADMIN != operatorUserLevel) {
            throw new CustomerException(ExceptionEnum.INSUFFICIENT_PERMISSION);
        }

        LambdaUpdateWrapper<XyUser> luw = new LambdaUpdateWrapper<>();
        boolean hasUpdate = false;
        if(user.getUsername() != null && !ObjectUtil.equal(user.getUsername(), operator.getUsername())) {
            XyUser usernameInDb = findUserByUsername(user.getUsername(), false);
            if(usernameInDb != null && !Objects.equals(usernameInDb.getId(), userId)) {
                throw new CustomerException(ExceptionEnum.USER_HAS_EXIST);
            }
            hasUpdate = true;
            luw.set(XyUser::getUsername, user.getUsername());
        }
        if(user.getPassword() != null) {
            hasUpdate = true;
            luw.set(XyUser::getPassword, passwordEncoder.encode(user.getPassword()));
        }
        if(user.getUserLevel() != null) {
            hasUpdate = true;
            luw.set(XyUser::getUserLevel, userUpdateLevel);
        }

        if(hasUpdate) {
            luw.eq(XyUser::getId, userId);

            int cnt = xyUserMapper.update(null, luw);
            return cnt == 1;
        } else {
            return true;
        }
    }

    @Override
    public XyUser queryUserById(Object principal, Long userId) {
        XyUser operator = findCurrentOperator(principal);

//        if (operator == null) {
//            throw new CustomerException(ExceptionEnum.USER_NOT_LOGIN);
//        }
//        if (!ObjectUtil.equals(operator.getId(), userId)) {
//            throw new CustomerException(ExceptionEnum.ILLEGAL_OPERATION);
//        }
        operator.setPassword("*******");
        return operator;
    }

    @Override
    public IPage<XyUser> queryUserList(Integer pageNum, Integer pageSize) {
        IPage<XyUser> page = new Page<>(pageNum, pageSize);
        page = xyUserMapper.selectPage(page, null);

        page.getRecords().forEach(user -> user.setPassword("******"));

        return page;
    }

    /**
     * 获取当前操作者: 即当前操作系统的用户(已弃用, 因为mybatis底层使用代理无法获取对应的线程上下文)
     * @return      当前系统的操作者
     */
    @Override
    public XyUser findCurrentOperator(Object principal) {
        try {
            if(principal instanceof UserDetails) {
                // 如果当前用户已经登录
                UserDetails userDetails = (UserDetails) principal;
                return findUserByUsername(userDetails.getUsername(), false);
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public XyUser findUserById(Long userId) {
        LambdaQueryWrapper<XyUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(XyUser::getId, userId);

        XyUser xyUser = xyUserMapper.selectOne(lqw);
        if(ObjectUtil.isNull(xyUser)) {
            throw new CustomerException(ExceptionEnum.USER_NOT_EXIST);
        }

        return xyUser;
    }

}
