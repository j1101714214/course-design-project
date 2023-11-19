package edu.whu.service.impl;

import cn.hutool.core.util.RandomUtil;
import edu.whu.model.common.enumerate.InvokeMethod;
import edu.whu.model.job.vo.AddJobVo;
import edu.whu.model.plugin.pojo.XyPlugin;
import edu.whu.service.IXyPluginService;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimeType;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class XyPluginServiceImplTest {
    @Autowired
    private IXyPluginService pluginService;
    @Autowired
    private MinioClient minioClient;


    private Object currOperator = null;
    @BeforeEach
    public void before() throws Exception {


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
    }

    @Test
    void queryPluginList() {
    }

    @Test
    void addPlugin() {
        String filename = "D:\\360MoveData\\Users\\Akihabara\\OneDrive\\桌面\\大型软件课程设计\\demo\\target\\demo-0.0.1-SNAPSHOT.jar";
        MultipartFile multipartFile = null;
        try {
            multipartFile = new MockMultipartFile("demo-0.0.1-SNAPSHOT.jar", "demo-0.0.1-SNAPSHOT.jar", "", Files.newInputStream(Paths.get(filename)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        XyPlugin xyPlugin = new XyPlugin();
        xyPlugin.setPluginName("测试用插件.jar");
        xyPlugin.setPluginFilename("demo-0.0.1-SNAPSHOT.jar");
        xyPlugin.setPluginVersion("1.0");
        xyPlugin.setPluginUrl("demo-0.0.1-SNAPSHOT.jar");
        xyPlugin.setPluginDesc("测试用插件");
        pluginService.addPlugin(multipartFile, xyPlugin);

    }

    @Test
    void deletePlugin() {
    }

    @Test
    void download() {
        pluginService.downloadPluginAndStart(currOperator, 1723962801415454721L);
    }
}