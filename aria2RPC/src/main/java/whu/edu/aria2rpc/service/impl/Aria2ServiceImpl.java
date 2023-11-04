package whu.edu.aria2rpc.service.impl;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import whu.edu.aria2rpc.config.Aria2DownloadConfig;
import whu.edu.aria2rpc.entity.Aria2Enum;
import whu.edu.aria2rpc.entity.DownloadInfo;
import whu.edu.aria2rpc.entity.ReqRes;
import whu.edu.aria2rpc.service.IAria2Service;
import whu.edu.aria2rpc.service.IDownInfoService;

import javax.annotation.Resource;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static whu.edu.aria2rpc.utility.Aria2Utility.*;

@Service
public class Aria2ServiceImpl implements IAria2Service {

    @Autowired
    private IDownInfoService downInfoService;

    @Resource
    private Aria2DownloadConfig downloadConfig;

    @Override
    public ReqRes requestDownload(String downloadUrl, String title, String saveDir){

        ReqRes reqRes = new ReqRes();

        JSONArray jsonArray = new JSONArray();
        jsonArray.put("token:"+downloadConfig.getSecret());

        JSONArray urls=new JSONArray();
        urls.put(downloadUrl);
        jsonArray.put(urls);


        JSONObject output = new JSONObject();
        output.put("dir",".\\res");
        jsonArray.put(output);


        System.out.println(jsonArray);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = postRequestBuilder(downloadConfig.getDownload_url(),downloadConfig.getDownload_port());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", IDGenerator());
        jsonObject.put("jsonrpc", "2.0");

        jsonObject.put("method", "aria2.addUri");
        jsonObject.put("params", jsonArray);
        StringEntity entity;
        try {
            entity = new StringEntity(jsonObject.toString());
        }catch (Exception e) {
            reqRes.setResult(Aria2Enum.ENCODE_ERROR);
            return reqRes;
        }
        httpPost.setEntity(entity);

        HttpResponse res;
        try {
            res = httpClient.execute(httpPost);
        }catch (Exception e) {
            reqRes.setResult(Aria2Enum.REQUEST_ERROR);
            return reqRes;
        }
        JSONObject resJSON;
        try {
            resJSON = new JSONObject(EntityUtils.toString(res.getEntity()));
        }catch (Exception e) {
            reqRes.setResult(Aria2Enum.DECODE_ERROR);
            return reqRes;
        }
        String result= (String) resJSON.get("result");
        System.out.println(result);
        reqRes.setResult(Aria2Enum.DOWNLOAD_ACTIVE);
        reqRes.setGID(result);

        if(title==null){
            title = "Untitled";
        }

        DownloadInfo info = new DownloadInfo(result,title,downloadUrl,Aria2Enum.DOWNLOAD_ACTIVE.toString(), LocalDateTime.now());
        downInfoService.initDownloadInfo(info);
        System.out.println("----"+Aria2Enum.DOWNLOAD_ACTIVE.toString());

        return reqRes;
    }



}
