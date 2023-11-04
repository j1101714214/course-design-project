package whu.edu.aria2rpc.utility;

import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import whu.edu.aria2rpc.entity.Aria2Enum;

import java.util.UUID;

public class Aria2Utility {



    public static String IDGenerator(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    public static Aria2Enum String2Enum(String status) {
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

    public static HttpPost postRequestBuilder(String url,String port){
        HttpPost httpPost = new HttpPost("http://"+url+":"+port+"/jsonrpc");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        return httpPost;
    }

}
