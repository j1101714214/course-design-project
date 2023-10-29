package edu.whu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Akihabara
 * @version 1.0
 * @description WebMvcConfig: MVC相关配置
 * @date 2023/10/5 21:04
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream()
                // 过滤出StringHttpMessageConverter类型实例
                .filter(StringHttpMessageConverter.class::isInstance)
                .map(c -> (StringHttpMessageConverter) c)
                // 这里将转换器的默认编码设置为utf-8
                .forEach(c -> c.setDefaultCharset(StandardCharsets.UTF_8));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
}
