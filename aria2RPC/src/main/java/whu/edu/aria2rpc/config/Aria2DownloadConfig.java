package whu.edu.aria2rpc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="aria2")
public class Aria2DownloadConfig {

    private String download_url;

    private String download_port;

    private String secret;

}
