package edu.whu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.exception.CustomerException;
import edu.whu.mapper.XyPluginMapper;
import edu.whu.model.common.enumerate.ExceptionEnum;
import edu.whu.model.plugin.pojo.XyPlugin;
import edu.whu.model.user.pojo.XyUser;
import edu.whu.service.IXyPluginService;
import edu.whu.service.IXyUserService;
import edu.whu.utils.PluginUtils;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import jdk.nashorn.internal.objects.annotations.Property;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Akihabara
 * @version 1.0
 * @description XyPluginServiceImpl: IXyPluginService接口实现类
 * @date 2023/9/27 21:27
 */
@Service
@Slf4j
public class XyPluginServiceImpl extends ServiceImpl<XyPluginMapper, XyPlugin> implements IXyPluginService {
    @Autowired
    private XyPluginMapper pluginMapper;
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private IXyUserService userService;

    @Value("${xy-nas-tools.root}")
    private String root;

    public void setRoot(String root) {
        this.root = root;
    }

    private String DOWNLOAD_DIR = null;

    @PostConstruct
    public void init() {
        DOWNLOAD_DIR = root + "/plugins/";
        // 初始化创建插件文件夹
        File directory = new File(DOWNLOAD_DIR);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                log.info("Directory created successfully.");
            } else {
                log.info("Failed to create directory.");
            }
        }
    }

    @Value("${minio.bucket.files}")
    private String bucket;
    @Override
    public IPage<XyPlugin> queryPluginList(Integer pageNum, Integer pageSize) {
        IPage<XyPlugin> page = new Page<>(pageNum, pageSize);
        return pluginMapper.selectPage(page, null);
    }

    @Override
    public Boolean addPlugin(MultipartFile file, XyPlugin plugin) {
        plugin.setPluginUrl("");
        plugin.setPluginFilename("");
        String filename = file.getOriginalFilename();
        if(filename == null) {
            throw new CustomerException(ExceptionEnum.ERROR_PLUGIN_TYPE);
        }

        if(!plugin.getPluginName().endsWith(".jar")) {
            plugin.setPluginName(plugin.getPluginName() + ".jar");
        }

        // 上传文件到对应的与服务器并且可以让用户下载
        if (!uploadFile(file, bucket, plugin.getPluginName())) {
            throw new CustomerException(ExceptionEnum.ERROR_PLUGIN_UPLOAD);
        }
        // 保存插件信息
        int cnt = pluginMapper.insert(plugin);
        return cnt == 1;
    }

    @Override
    public Boolean deletePlugin(Long id) {
        XyPlugin plugin = pluginMapper.selectById(id);

        // TODO: 删除远程服务器的插件文件
        int cnt = pluginMapper.deleteById(id);
        return cnt == 1;
    }

    @Override
    public Boolean downloadPluginAndStart(Object principal, Long pluginId) {
        XyPlugin xyPlugin = pluginMapper.selectById(pluginId);
        if(!downloadFile(xyPlugin)) {
            throw new CustomerException(ExceptionEnum.ERROR_PLUGIN_DOWNLOAD);
        }
        XyUser operator = userService.findCurrentOperator(principal);
        List<Long> plugins = operator.getPlugins();
        if(plugins == null) {
            plugins = new ArrayList<>();
        }
        plugins.add(xyPlugin.getId());
        operator.setPlugins(new ArrayList<>(new HashSet<>(plugins)));       // 去重
        userService.updateById(operator);

        // 启动插件
        try {
            PluginUtils.loadPlugin(DOWNLOAD_DIR + xyPlugin.getPluginName());
        } catch (Exception e) {
            e.printStackTrace();                                            // 启动失败就不管
            return false;
        }
        return true;
    }

    @Override
    public void loadPluginsByUserId(XyUser operator) {
        List<Long> plugins = operator.getPlugins();
        if(plugins == null || plugins.size() == 0) {
            return;
        }
        for (Long plugin : plugins) {
            XyPlugin xyPlugin = pluginMapper.selectById(plugin);
            try {
                PluginUtils.loadPlugin(DOWNLOAD_DIR + xyPlugin.getPluginName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IPage<XyPlugin> queryPluginListByUser(Integer pageNum, Integer pageSize, Long userId) {
        XyUser operator = userService.findUserById(userId);
        IPage<XyPlugin> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<XyPlugin> lqw = new LambdaQueryWrapper<>();
        if(operator.getPlugins() == null || operator.getPlugins().size() == 0) {
            return page;
        }
        lqw.in(XyPlugin::getId, operator.getPlugins());
        return pluginMapper.selectPage(page, lqw);
    }

    @Override
    public Boolean stopPlugin(XyPlugin xyPlugin) {
        try {
            PluginUtils.killPluginByName(xyPlugin.getPluginName());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean startPlugin(XyPlugin xyPlugin) {
        try {
            PluginUtils.loadPlugin(DOWNLOAD_DIR + xyPlugin.getPluginName());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean removePlugin(Object principal, XyPlugin xyPlugin) {
        try {
            PluginUtils.killPluginByName(xyPlugin.getPluginName());
            // 删除文件
            File file = new File(DOWNLOAD_DIR + xyPlugin.getPluginName());
            if (file.exists()) {
                boolean delete = file.delete();
            }
            XyUser operator = userService.findCurrentOperator(principal);

            operator.getPlugins().remove(xyPlugin.getId());
            userService.updateById(operator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 文件上传文件
     *
     * @param file          文件
     * @param bucketName    桶名
     * @param objectName    文件名,如果有文件夹则格式为 "文件夹名/文件名"
     * @return              操作结果
     */
    private boolean uploadFile(MultipartFile file, String bucketName, String objectName) {
        if (Objects.isNull(file) || Objects.isNull(bucketName)) {
            throw new RuntimeException("文件或者桶名参数不全！");
        }

        try {
            //资源的媒体类型
            String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            InputStream inputStream = file.getInputStream();
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName).object(objectName)
                    .stream(inputStream, inputStream.available(), -1)
                    .contentType(contentType)
                    .build();
            ObjectWriteResponse response = minioClient.putObject(args);
            inputStream.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean downloadFile(XyPlugin xyPlugin) {
        String outputFileName = DOWNLOAD_DIR + xyPlugin.getPluginName();
        try (BufferedInputStream stream = new BufferedInputStream(minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(xyPlugin.getPluginName())
                .build()));
             FileOutputStream outputStream = new FileOutputStream(outputFileName);
        ) {
            // 读取源文件内容并写入目标文件
            int len = -1;
            byte[] data = new byte[1024];
            while ((len = stream.read(data)) != -1) {
                outputStream.write(data,0,len);
            }
            return true;
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new RuntimeException(e);
        }
    }

}
