package whu.edu.aria2rpc.entity.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
public class Aria2DownloadBo {

    @NotNull(message = "下载链接不可为空")
    String downloadUrl;

    String title;

    String saveDir;
}
