package whu.edu.aria2rpc.config;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="aria2")
@ApiModel(value = "Aria2DownloadConfig", description = "Aria2下载配置")
public class Aria2DownloadConfig {

    private String download_url;

    private String download_port;

    private String secret;

}
