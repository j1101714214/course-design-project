package whu.edu.aria2rpc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whu.edu.aria2rpc.dao.DownloadInfoDao;
import whu.edu.aria2rpc.entity.Aria2Enum;
import whu.edu.aria2rpc.entity.DownloadInfo;
import whu.edu.aria2rpc.service.IAria2Service;
import whu.edu.aria2rpc.service.IDownInfoService;
import java.util.List;
import java.util.Objects;

@Service
public class DownInfoServiceImpl extends ServiceImpl<DownloadInfoDao, DownloadInfo> implements IDownInfoService {

    @Autowired
    private IAria2Service aria2Service;


    public void checkStatus(){
        List<DownloadInfo> informationList = getActiveStatus();
        for(DownloadInfo information : informationList){
            if(information.getStatus().equals(aria2Service.requestStatus(information.getGID()).toString())){

            }
        }
    }

    private List<DownloadInfo> getActiveStatus(){
        LambdaQueryWrapper<DownloadInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DownloadInfo::getStatus, Aria2Enum.DOWNLOAD_ACTIVE);
        return getBaseMapper().selectList(lqw);
    }

    public boolean initDownloadInfo(DownloadInfo info){
        return getBaseMapper().insert(info)>0;
    }

}
