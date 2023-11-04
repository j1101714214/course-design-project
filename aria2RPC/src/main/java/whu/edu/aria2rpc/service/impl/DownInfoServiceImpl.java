package whu.edu.aria2rpc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import whu.edu.aria2rpc.config.Aria2DownloadConfig;
import whu.edu.aria2rpc.dao.DownloadInfoDao;
import whu.edu.aria2rpc.entity.Aria2Enum;
import whu.edu.aria2rpc.entity.DownloadInfo;
import whu.edu.aria2rpc.service.IDownInfoService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static whu.edu.aria2rpc.utility.Aria2Utility.*;

@Service
public class DownInfoServiceImpl extends ServiceImpl<DownloadInfoDao, DownloadInfo> implements IDownInfoService {

    @Resource
    private Aria2DownloadConfig downloadConfig;

    @Scheduled(cron = "0/15 * * * * ?")
    public void checkStatus(){
        List<DownloadInfo> informationList = getActiveStatus();
        for(DownloadInfo information : informationList){
            String GID = information.getGID();
            if(!information.getStatus().equals(requestStatus(GID).toString())){
                information.setStatus(requestStatus(GID).toString());
                information.setUpdateTime(LocalDateTime.now());
                getBaseMapper().updateById(information);
            }
        }
    }

    private DownloadInfo analyzeStatus(DownloadInfo info) {
        if (info.getUpdateTime() != null) {
            if (info.getUpdateTime().isAfter(info.getCreateTime().plusDays(7))) {
                info.setStatus(Aria2Enum.DOWNLOAD_ERROR.toString());
            }
        }
        return info;
    }

    private List<DownloadInfo> getActiveStatus(){
        LambdaQueryWrapper<DownloadInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DownloadInfo::getStatus, Aria2Enum.DOWNLOAD_ACTIVE);
        return getBaseMapper().selectList(lqw);
    }

    private List<DownloadInfo> getErrorStatus(){
        LambdaQueryWrapper<DownloadInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DownloadInfo::getStatus, Aria2Enum.DOWNLOAD_ERROR);
        return getBaseMapper().selectList(lqw);
    }

    public boolean initDownloadInfo(DownloadInfo info){
        return getBaseMapper().insert(info)>0;
    }

    @Override
    public Aria2Enum requestStatus(String GID) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = postRequestBuilder(downloadConfig.getDownload_url(), downloadConfig.getDownload_port());
        JSONObject requestObject = new JSONObject();
        requestObject.put("id",IDGenerator());
        requestObject.put("jsonrpc", "2.0");
        requestObject.put("method", "aria2.tellStatus");

        JSONArray jsonArray = new JSONArray();
        jsonArray.put("token:secret");

        jsonArray.put(GID);

        requestObject.put("params", jsonArray);
        StringEntity entity;
        try {
            entity = new StringEntity(requestObject.toString());
        }catch (Exception e) {
            return Aria2Enum.ENCODE_ERROR;
        }
        System.out.println(requestObject);
        httpPost.setEntity(entity);
        HttpResponse res;
        try {
            res = httpClient.execute(httpPost);
        }catch (Exception e){
            return Aria2Enum.REQUEST_ERROR;
        }
        JSONObject resJSON;
        try {
            resJSON = new JSONObject(EntityUtils.toString(res.getEntity()));
        }catch (Exception e) {
            return Aria2Enum.DECODE_ERROR;
        }
        String status= (String) resJSON.getJSONObject("result").get("status");
        System.out.println(status);
        //complete error active
        /*todo:
            active:定时查询后续状态直到complete或error
            error:返回false
            complete:返回true
        */
        return String2Enum(status);
    }

}
