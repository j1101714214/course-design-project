package edu.whu.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.whu.exception.CustomerException;
import edu.whu.model.common.enumerate.UserLevel;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.model.user.vo.LoginAndRegisterVo;
import edu.whu.service.IXyUserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyUserServiceImplTest: XyUserServiceImpl测试类
 * @date 2023/9/16 20:53
 */
@SpringBootTest
@Transactional
@Rollback
@AutoConfigureMockMvc
public class XyUserServiceImplTest {
    @Autowired
    private IXyUserService xyUserService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private XyUser user = null;
    private LoginAndRegisterVo vo = null;


    @BeforeEach
    public void before() throws Exception {
        user = new XyUser();
        user.setUsername(RandomUtil.randomString(10));
        user.setPassword(RandomUtil.randomString(10));
        user.setUserLevel(UserLevel.GUEST);

        vo = new LoginAndRegisterVo();
        vo.setUsername("admin");
        vo.setPassword("admin");

        byte[] bytes = objectMapper.writeValueAsBytes(vo);

//        mockMvc.perform(post("/authenticate/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(bytes)
//        );
        mockMvc.perform(post("/authenticate/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bytes)
        );
    }

    @AfterTestClass
    public void after() {
        user = null;
        vo = null;
    }

    @Test
    void findUserByUsername() {
        xyUserService.save(user);

        XyUser userInDb = xyUserService.findUserByUsername(user.getUsername(), false);
        Assertions.assertNotNull(userInDb);
        Assertions.assertEquals(userInDb.getUsername(), user.getUsername());

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
