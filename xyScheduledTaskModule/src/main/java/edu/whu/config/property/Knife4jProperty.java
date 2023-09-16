package edu.whu.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Akihabara
 * @version 1.0
 * @description Knife4jProperty: Knife4j相关配置参数
 * @date 2023/9/16 16:10
 */
@Data
@Component
@ConfigurationProperties(prefix = Knife4jProperty.KNIFE4J_PREFIX)
public class Knife4jProperty {
    public static final String KNIFE4J_PREFIX = "xy-nas-tools.knife4j";

    private String basePackage;
    private String title ;
    private String description;
    private String version;
}
