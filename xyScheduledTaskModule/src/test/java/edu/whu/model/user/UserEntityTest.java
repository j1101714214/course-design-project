package edu.whu.model.user;

import edu.whu.model.common.enumerate.UserLevel;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Akihabara
 * @version 1.0
 * @description UserEntityTest: 用户实体测试类
 * @date 2023/9/16 15:44
 */
@SpringBootTest
public class UserEntityTest {
    @Autowired
    private IXyUserService userService;
    @Test
    public void testUserEntityInject() {
        XyUser xyUser = new XyUser();
        xyUser.setUsername("test001");
        xyUser.setPassword("test001");
        xyUser.setUserLevel(UserLevel.GUEST);

        List<Long> plugins = new ArrayList<>();
        plugins.add(1L);
        plugins.add(2L);
        plugins.add(3L);

        xyUser.setPlugins(plugins);
        userService.save(xyUser);
    }

    @Test
    public void testUserFind() {
        List<XyUser> list = userService.list();
        for (XyUser xyUser : list) {
            System.out.println(xyUser);
        }
    }
}
