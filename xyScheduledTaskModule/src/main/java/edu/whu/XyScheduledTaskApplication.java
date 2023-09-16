package edu.whu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyScheduledTaskApplication: 定时任务服务启动类
 * @date 2023/9/16 16:18
 */

@SpringBootApplication
@EnableConfigurationProperties
public class XyScheduledTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(XyScheduledTaskApplication.class);
    }
}
