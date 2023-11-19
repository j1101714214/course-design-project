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
import org.springframework.stereotype.Service;
import whu.edu.aria2rpc.entity.Aria2Enum;
import whu.edu.aria2rpc.entity.ReqRes;
import whu.edu.aria2rpc.service.IAria2Service;
import whu.edu.aria2rpc.service.IDownInfoService;

import java.util.UUID;

import static whu.edu.aria2rpc.service.RequestBuilder.postRequestBuilder;

@Service
public class Aria2ServiceImpl implements IAria2Service {

    @Autowired
    private IDownInfoService downInfoService;


    @Override
    public ReqRes requestDownload(String downloadUrl, String title, String saveDir){

        ReqRes reqRes = new ReqRes();

        JSONArray jsonArray = new JSONArray();
        jsonArray.put("token:secret");

        JSONArray urls=new JSONArray();
        urls.put(downloadUrl);
        jsonArray.put(urls);


        JSONObject output = new JSONObject();
        output.put("dir",".\\res");
        jsonArray.put(output);


        System.out.println(jsonArray);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = postRequestBuilder();

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
        reqRes.setResult(Aria2Enum.REQUEST_COMPLETE);
        reqRes.setGID(result);
        return reqRes;
    }

    @Override
    public Aria2Enum requestStatus(String GID) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = postRequestBuilder();
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

//    private HttpPost postRequestBuilder(){
//        HttpPost httpPost = new HttpPost("http://127.0.0.1:6800/jsonrpc");
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Content-Type", "application/json");
//        return httpPost;
//    }

    private String IDGenerator(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    private Aria2Enum String2Enum(String status) {
        switch (status) {
            case "complete":
                return Aria2Enum.DOWNLOAD_COMPLETE;
            case "active":
                return Aria2Enum.DOWNLOAD_ACTIVE;
            case "error":
                return Aria2Enum.DOWNLOAD_ERROR;
            default :
                return Aria2Enum.UNKNOWN_ERROR;
        }
    }

}
