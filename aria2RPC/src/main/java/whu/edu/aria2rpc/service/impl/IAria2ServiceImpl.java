package whu.edu.aria2rpc.service.impl;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import whu.edu.aria2rpc.service.IAria2Service;

import java.io.*;
import java.util.UUID;

@Service
public class IAria2ServiceImpl implements IAria2Service {

    @Override
    public String requestDownload(String downloadUrl, String title, String saveDir) throws Exception {

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
        StringEntity entity = new StringEntity(jsonObject.toString());
        httpPost.setEntity(entity);
        HttpResponse res = httpClient.execute(httpPost);

        JSONObject resJSON = new JSONObject(EntityUtils.toString(res.getEntity()));
        System.out.println(resJSON.get("result"));
        return resJSON.get("result").toString();
    }

    @Override
    public String requestStatus(String GID) throws IOException {
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

        StringEntity entity = new StringEntity(requestObject.toString());
        System.out.println(requestObject);
        httpPost.setEntity(entity);
        HttpResponse res = httpClient.execute(httpPost);
        JSONObject resJSON = new JSONObject(EntityUtils.toString(res.getEntity()));
        System.out.println(resJSON.getJSONObject("result").get("status"));
        //complete error active
        /*todo:
            active:定时查询后续状态直到complete或error
            error:返回false
            complete:返回true
        */
        return null;
    }

    public HttpPost postRequestBuilder(){
        HttpPost httpPost = new HttpPost("http://127.0.0.1:6800/jsonrpc");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        return httpPost;
    }

    public String IDGenerator(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

}
