package whu.edu.aria2rpc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DownloadInfo {

    private String GID;

    private String title;

    private String url;

    private String status;

}
