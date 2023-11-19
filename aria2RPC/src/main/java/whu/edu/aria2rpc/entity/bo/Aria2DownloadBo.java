package whu.edu.aria2rpc.entity.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class Aria2DownloadBo {

    @NotNull(message = "下载链接不可为空")
    String downloadUrl;

    @Value("Untitled")
    String title;

    @Value(".\\")
    String saveDir;
}
