package edu.whu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Akihabara
 * @version 1.0
 * @description ImageConfig: 解决访问本地图片
 * @date 2023/11/15 16:11
 */
@Configuration
public class ImageConfig implements WebMvcConfigurer {
    @Value("${photo-plugin.root}")
    private String root;
    /*
     *addResourceHandler:访问映射路径
     *addResourceLocations:资源绝对路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:" + root);
    }
}
