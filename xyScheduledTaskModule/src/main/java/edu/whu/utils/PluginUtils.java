package edu.whu.utils;

import cn.hutool.core.util.StrUtil;
import edu.whu.exception.CustomerException;
import edu.whu.model.common.enumerate.ExceptionEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Akihabara
 * @version 1.0
 * @description PluginUtils: 插件管理工具类
 * @date 2023/9/22 11:24
 */
@Component
public class PluginUtils {
    private static final String CMD_JAVA_COMMAND_PREFIX = "java -jar ";
    private static final String REGEX_PARSE_PID = "PID [0-9]*";
    private static String CMD_KILL_TASK = "";

    private static final List<String> PIDS = new ArrayList<>();

    @PostConstruct
    private void init() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            // Windows操作系统
            CMD_KILL_TASK = "taskkill /f /t /pid ";
        } else if (os.contains("mac")) {
            // Mac操作系统
            CMD_KILL_TASK = "kill -9 ";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("linux")) {
            // Linux操作系统
            CMD_KILL_TASK = "kill -9 ";
        } else {
            // 其他操作系统
            CMD_KILL_TASK = "";
        }
    }


    /**
     * 通过路径加载对应的jar包
     * @param path  jar包的路径
     */
    public void loadPlugin(String path) {
        try {
            // 拼接命令
            String command = CMD_JAVA_COMMAND_PREFIX + path;
            Process process = Runtime.getRuntime().exec(command);

            // 准备提取进程号
            Pattern pattern = Pattern.compile(REGEX_PARSE_PID);

            // 获取命令输出结果
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "GBK")); // 设置编码为GBK
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if(matcher.find()) {
                    System.out.println(line);
                    System.out.println(matcher.group().split(" ")[1]);
                    PIDS.add(matcher.group().split(" ")[1]);
                    break;
                }
            }
            inputStream.close();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据pid杀死进程
     * @param pid   插件进程pid
     */
    private void killPlugin(String pid) {
        try {
            // 拼接命令
            if(StrUtil.isNullOrUndefined(CMD_KILL_TASK)) {
                throw new CustomerException(ExceptionEnum.ERROR_INVOKE_PLUGIN);
            }
            String command = CMD_KILL_TASK + pid;
            Process process = Runtime.getRuntime().exec(command);

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对外暴露的接口, 删除所有插件进程
     */
    @PreDestroy
    public void killAllPlugins() {
        PIDS.forEach(this::killPlugin);
    }


    /*public static void main(String[] args) {
        loadPlugin("./xyScheduledTaskModule/src/main/resources/plugin/demo-0.0.1-SNAPSHOT.jar");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        killAllPlugins();
    }*/
}
