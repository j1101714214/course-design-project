package edu.whu.model.log;

import edu.whu.model.job.pojo.XyJob;
import edu.whu.model.log.pojo.XyLog;
import edu.whu.service.IXyJobService;
import edu.whu.service.IXyLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Akihabara
 * @version 1.0
 * @description LogTest: TODO
 * @date 2023/10/6 20:09
 */
@SpringBootTest
public class LogEntityTest {
    @Autowired
    private IXyLogService logService;
    @Test
    public void testUserEntityInject() {
        XyLog xyLog = new XyLog();
        xyLog.setUserId(1L);
        xyLog.setTaskId(1L);
        xyLog.setLogTime(Timestamp.valueOf(LocalDateTime.now()));

        logService.save(xyLog);
    }
}
