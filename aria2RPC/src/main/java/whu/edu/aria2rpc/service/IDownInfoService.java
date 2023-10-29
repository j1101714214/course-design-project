package whu.edu.aria2rpc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import whu.edu.aria2rpc.entity.Aria2Enum;
import whu.edu.aria2rpc.entity.DownloadInfo;

public interface IDownInfoService extends IService<DownloadInfo> {

    public boolean initDownloadInfo(DownloadInfo info);

    Aria2Enum requestStatus(String GID);
}
