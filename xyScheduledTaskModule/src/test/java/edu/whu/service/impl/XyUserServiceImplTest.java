package edu.whu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.exception.CustomerException;
import edu.whu.model.common.enumerate.UserLevel;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyUserServiceImplTest: XyUserServiceImpl测试类
 * @date 2023/9/16 20:53
 */
@SpringBootTest
@Transactional
@Rollback
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

    @Test
    void findUserByUsername() {
        xyUserService.save(user);

        XyUser userInDb = xyUserService.findUserByUsername(user.getUsername(), false);
        Assertions.assertNotNull(userInDb);
        Assertions.assertEquals(userInDb.getUsername(), user.getUsername());
    }

    @Test
    void deleteUser() {
        xyUserService.save(user);
        xyUserService.deleteUser(user.getId());

        XyUser userAfterDelete = xyUserService.getById(user.getId());
        Assertions.assertNull(userAfterDelete);
    }

    @Test
    void updateUser() {
        xyUserService.save(user);

        user.setUsername("张三");
        Assertions.assertThrows(CustomerException.class, () -> xyUserService.updateUser(user.getId(), user));
    }

    @Test
    void queryUserById() {
        xyUserService.save(user);

        Assertions.assertThrows(CustomerException.class, () -> xyUserService.queryUserById(user.getId()));
    }

    @Test
    void queryUserList() {
        xyUserService.save(user);

        IPage<XyUser> page = xyUserService.queryUserList(1, 10);
        Assertions.assertNotNull(page.getRecords());
        Assertions.assertEquals(page.getTotal(), 1);
        Assertions.assertEquals(page.getCurrent(), 1L);
        Assertions.assertEquals(page.getRecords().get(0).getId(), user.getId());
    }
}
