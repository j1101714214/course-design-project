package edu.whu.service.impl;

import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Akihabara
 * @version 1.0
 * @description UserDetailServiceImpl: 重写loadUserByUsername, 使用数据库实现鉴权
 * @date 2023/9/16 18:19
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private IXyUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        XyUser user = userService.findUserByUsername(username, true);
        return User.builder().username(username)
                .password(user.getPassword())
                .roles(user.getUserLevel().getDescription())
                .build();
    }
}
