package whu.edu.aria2rpc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import whu.edu.aria2rpc.entity.Aria2Enum;
import whu.edu.aria2rpc.entity.DownloadInfo;

import java.util.List;

public interface IDownInfoService extends IService<DownloadInfo> {

    boolean initDownloadInfo(DownloadInfo info);

    Aria2Enum requestStatus(String GID);

    IPage<DownloadInfo> queryInfoList(int type,int page, int size);

    DownloadInfo queryDetail(String GID);
}
