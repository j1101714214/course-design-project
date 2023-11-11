package whu.edu.aria2rpc.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Aria2DownloadBo {

    @NotNull(message = "下载链接不可为空")
    String downloadUrl;

    String title;

    String saveDir;
}
