package whu.edu.aria2rpc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName(value="download_info")
@NoArgsConstructor
public class DownloadInfo {

    public DownloadInfo(String GID,String title,String dir,String url,String status,LocalDateTime createTime){
        this.GID = GID;
        this.title = title;
        this.dir = dir;
        this.url = url;
        this.status = status;
        this.createTime = createTime;
    }

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("gid")
    private String GID;

    private String title;

    private String dir;

    private String url;

    @TableField("status")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
