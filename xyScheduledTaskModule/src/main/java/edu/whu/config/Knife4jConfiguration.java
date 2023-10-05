package edu.whu.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import edu.whu.config.property.Knife4jProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Akihabara
 * @version 1.0
 * @description Knife4jConfiguration: knife4j API文档配置类
 * @date 2023/9/16 15:56
 */
@Configuration
@EnableKnife4j
@EnableSwagger2
public class Knife4jConfiguration {
    @Autowired
    private Knife4jProperty knife4jProperty;
    /**
     * 创建并配置 Docket 对象，用于 Swagger 文档的生成和展示
     *
     * @return 配置好的 Docket 对象
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("任务调度服务")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(knife4jProperty.getTitle())
                .description(knife4jProperty.getDescription())
                .version(knife4jProperty.getVersion())
                .build();
    }


}
