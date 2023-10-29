package whu.edu.aria2rpc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Value;

@Data
@TableName(value="download_info")
public class DownloadInfo {

    public DownloadInfo(String GID,String title,String url,String status){
        this.GID = GID;
        this.title = title;
        this.url = url;
        this.status = status;
    }

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("gid")
    private String GID;

    private String title;

    private String url;

    private String status;

}
