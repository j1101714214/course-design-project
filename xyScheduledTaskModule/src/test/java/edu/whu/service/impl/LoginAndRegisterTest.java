package edu.whu.service.impl;

import edu.whu.exception.CustomerException;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.model.user.vo.LoginAndRegisterVo;
import edu.whu.service.IXyUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.net.BindException;

/**
 * @author Akihabara
 * @version 1.0
 * @description LoginAndRegisterTest: TODO
 * @date 2023/9/16 20:58
 */
@SpringBootTest
@Transactional
@Rollback
public class LoginAndRegisterTest {
    @Autowired
    private IXyUserService userService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    LoginAndRegisterVo loginAndRegisterVo = null;

    @BeforeEach
    public void before() {
        loginAndRegisterVo = new LoginAndRegisterVo();
        loginAndRegisterVo.setUsername("admin");
        loginAndRegisterVo.setPassword("admin");
    }

    @AfterEach
    public void after() {
        loginAndRegisterVo = null;
    }

    @Test
    public void testRegisterNormal() {
        // 测试用户注册的正常操作
        userService.saveUser(loginAndRegisterVo);

        XyUser user = userService.findUserByUsername(loginAndRegisterVo.getUsername(), true);
        Assertions.assertEquals(user.getUsername(), loginAndRegisterVo.getUsername());
        Assertions.assertTrue(passwordEncoder.matches(loginAndRegisterVo.getPassword(), user.getPassword()));
    }

    @Test
    @Rollback
    public void testRegisterAbnormal() {
        // 测试重复注册
        userService.saveUser(loginAndRegisterVo);
        Assertions.assertThrows(CustomerException.class, () -> userService.saveUser(loginAndRegisterVo));
    }
}
