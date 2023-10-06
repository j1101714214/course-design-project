package edu.whu;

import edu.whu.utils.PluginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.retry.annotation.EnableRetry;

import javax.annotation.PostConstruct;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyScheduledTaskApplication: 定时任务服务启动类
 * @date 2023/9/16 16:18
 */

@SpringBootApplication
@EnableConfigurationProperties
@EnableRetry
public class XyScheduledTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(XyScheduledTaskApplication.class);
    }
}
