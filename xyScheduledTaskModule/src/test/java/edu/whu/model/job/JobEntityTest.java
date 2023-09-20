package edu.whu.model.job;

import edu.whu.model.common.enumerate.UserLevel;
import edu.whu.model.job.pojo.XyJob;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyJobService;
import edu.whu.service.IXyUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Akihabara
 * @version 1.0
 * @description JobEntityTest: TODO
 * @date 2023/9/20 20:51
 */
@SpringBootTest
public class JobEntityTest {
    @Autowired
    private IXyJobService jobService;
    @Test
    public void testUserEntityInject() {
        XyJob xyJob = new XyJob();
        xyJob.setJobName("任务1");
        xyJob.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        xyJob.setIsDeleted(0);

        jobService.save(xyJob);
    }

}
