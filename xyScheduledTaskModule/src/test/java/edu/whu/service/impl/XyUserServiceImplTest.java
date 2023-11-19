package edu.whu.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

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
    private Object otherOperator = null;
    private Object currOperator = null;


    @BeforeEach
    public void before() throws Exception {
        user = new XyUser();
        String username = RandomUtil.randomString(10);
        user.setUsername(username);
        String password = RandomUtil.randomString(10);
        user.setPassword(password);
        user.setUserLevel(UserLevel.GUEST);

        vo = new LoginAndRegisterVo();
        vo.setUsername("admin");
        vo.setPassword("admin");

        byte[] bytes = objectMapper.writeValueAsBytes(vo);

//        mockMvc.perform(post("/authenticate/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(bytes)
//        );
        // 构造一个管理员角色操作非本人的数据
        otherOperator = User.builder().username("admin")
                .password("$2a$10$dY3wAtsqpXdGbq2qfrxL/OCJm4BlsxB8pU2.hc6uhiW6rZb1wQUaS")
                .roles("ADMIN")
                .build();

        currOperator = User.builder().username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .roles("USER")
                .build();

    }

    @AfterEach
    public void after() {
        user = null;
        vo = null;
        currOperator = null;
        otherOperator = null;
    }

    @Test
    void testFindUserByUsername() {
        xyUserService.save(user);

        XyUser userInDb = xyUserService.findUserByUsername(user.getUsername(), false);
        Assertions.assertNotNull(userInDb);
        Assertions.assertEquals(userInDb.getUsername(), user.getUsername());
    }

    @Test
    void testDeleteUser() {
        xyUserService.save(user);
        xyUserService.deleteUser(user.getId());

        XyUser userAfterDelete = xyUserService.getById(user.getId());
        Assertions.assertNull(userAfterDelete);
    }

    @Test
    void testUpdateUser() {
        xyUserService.save(user);

        // 成功情况
        user.setUsername("张三");
        Boolean ret = xyUserService.updateUser(currOperator, user.getId(), user);
        Assertions.assertTrue(ret);

        XyUser userInDBUpdated = xyUserService.getById(user.getId());
        Assertions.assertNotNull(userInDBUpdated);
        Assertions.assertEquals("张三", userInDBUpdated.getUsername());

        // 无操作权限
        Assertions.assertThrows(CustomerException.class, () -> {
            xyUserService.updateUser(otherOperator, user.getId(), user);
        });

        // 修改用户名为已存在用户名
        user.setUsername("admin");
        Assertions.assertThrows(CustomerException.class, () -> {
            xyUserService.updateUser(currOperator, user.getId(), user);
        });

        // 修改权限不足
        user.setUsername("张三");
        user.setUserLevel(UserLevel.SYS_ADMIN);
        Assertions.assertThrows(CustomerException.class, () -> {
            xyUserService.updateUser(currOperator, user.getId(), user);
        });
    }

    @Test
    void testQueryUserById() {
        xyUserService.save(user);

        Assertions.assertThrows(CustomerException.class, () -> xyUserService.queryUserById(otherOperator, user.getId()));
        Assertions.assertThrows(CustomerException.class, () -> xyUserService.queryUserById(null, user.getId()));
        Assertions.assertNotNull(xyUserService.queryUserById(currOperator, user.getId()));
    }

    @Test
    void queryUserList() {
        xyUserService.save(user);

        IPage<XyUser> page = xyUserService.queryUserList(1, 10);
        Assertions.assertNotNull(page.getRecords());
        Assertions.assertEquals(page.getTotal(), 10);
        Assertions.assertEquals(page.getCurrent(), 1L);
        Assertions.assertNotNull(page.getRecords().stream()
                .filter(xyUser -> xyUser.getId().equals(user.getId())).collect(Collectors.toList()).get(0));
    }
}
