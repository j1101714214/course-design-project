package edu.whu.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.exception.CustomerException;
import edu.whu.model.common.enumerate.InvokeMethod;
import edu.whu.model.common.enumerate.UserLevel;
import edu.whu.model.job.pojo.XyJob;
import edu.whu.model.job.vo.AddJobVo;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.model.user.vo.LoginAndRegisterVo;
import edu.whu.service.IXyJobService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;

/**
 * @author Akihabara
 * @version 1.0
 * @description HttpXyJobServiceTest: TODO
 * @date 2023/10/3 21:17
 */
@SpringBootTest
@Transactional
@Rollback
public class HttpXyJobServiceTest {
    @Autowired
    private IXyJobService jobService;

    private AddJobVo vo;

    private Object currOperator = null;

    @BeforeEach
    public void before() throws Exception {
        vo = new AddJobVo();
        vo.setJobName("test_task1");
        vo.setJobGroup("test_group");
        vo.setInvokeTarget("http://127.0.0.1:12121/hello?name=wangwu");
        vo.setCronExpression("/10 * * * * ?");
        vo.setMisfirePolicy("1");
        vo.setInvokeMethod(InvokeMethod.HTTP_GET);

        String username = RandomUtil.randomString(10);
        String password = RandomUtil.randomString(10);

        currOperator = User.builder().username("admin")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

    }

    @AfterEach
    public void after() {
        currOperator = null;
        vo = null;
    }

    @Test
    void queryUserList() {
        IPage<XyJob> page = jobService.queryUserList(1, 10);

        Assertions.assertNotNull(page);
        Assertions.assertEquals(page.getTotal(), 2);
    }

    @Test
    void queryJobListByUser() {
        IPage<XyJob> page =
                jobService.queryJobListByUser(currOperator, 1, 10, 1708466197371809793L);

        Assertions.assertNotNull(page);
        Assertions.assertEquals(page.getTotal(), 1);

        Assertions.assertThrows(CustomerException.class, () -> {
            jobService.queryJobListByUser(null, 1, 10, 1708466197371809793L);
        });

        Assertions.assertThrows(CustomerException.class, () -> {
            jobService.queryJobListByUser(null, 1, 10, 1808466197371809793L);
        });
    }

    @Test
    void addJob() {
        Boolean ret = jobService.addJob(currOperator, vo);
        Assertions.assertTrue(ret);

        Assertions.assertThrows(CustomerException.class, () -> {
            jobService.addJob(null, vo);
        });
    }

    @Test
    void updateJob() {
        XyJob job = jobService.queryJobById(2L);

        Assertions.assertThrows(CustomerException.class, () -> {
            jobService.updateJob(null, vo, 2L);
        });

        Assertions.assertThrows(CustomerException.class, () -> {
            jobService.updateJob(currOperator, vo, 2L);
        });

        vo.setJobName("task_job1");
        vo.setConcurrent("0");
        jobService.updateJob(currOperator, vo, 1L);
        Assertions.assertEquals("0", jobService.queryJobById(1L).getConcurrent());
    }

    @Test
    void queryJobById() {
        XyJob job = jobService.queryJobById(2L);
        Assertions.assertEquals("http://127.0.0.1:12121/hello?name=wangwu", job.getInvokeTarget());
    }

    @Test
    void deleteJob() {

        Assertions.assertThrows(CustomerException.class, () -> {
            jobService.deleteJob(null, 2L);
        });

        Assertions.assertThrows(CustomerException.class, () -> {
            jobService.deleteJob(currOperator, 2L);
        });

        Assertions.assertThrows(CustomerException.class, () -> {
            jobService.deleteJob(currOperator, 2L);
        });

        Boolean ret = jobService.deleteJob(currOperator, 1L);
        Assertions.assertTrue(ret);
    }
}
