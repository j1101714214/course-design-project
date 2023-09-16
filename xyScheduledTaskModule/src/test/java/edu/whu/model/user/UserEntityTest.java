package edu.whu.model.user;

import edu.whu.model.common.enumerate.UserLevel;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        userService.save(xyUser);
    }
}
