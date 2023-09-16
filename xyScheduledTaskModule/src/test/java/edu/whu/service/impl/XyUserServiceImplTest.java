package edu.whu.service.impl;

import edu.whu.model.common.enumerate.UserLevel;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyUserServiceImplTest: XyUserServiceImpl测试类
 * @date 2023/9/16 20:53
 */
@SpringBootTest
public class XyUserServiceImplTest {
    @Autowired
    private IXyUserService xyUserService;

    private XyUser user = null;


    @BeforeEach
    public void before() {
        user = new XyUser();
        user.setUsername("test_user1");
        user.setPassword("123456");
        user.setUserLevel(UserLevel.GUEST);
    }

    @AfterEach
    public void after() {
        user = null;
    }
}
