package whu.edu.aria2rpc.utility;

import org.apache.http.client.methods.HttpPost;
import whu.edu.aria2rpc.entity.Aria2Enum;

import java.util.UUID;

public class Aria2Utility {


    /**
     * 生成随机ID
     * @return String
     */
    public static String IDGenerator(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    /**
     * 结果字符串转枚举
     * @return Aria2Enum
     */
    public static Aria2Enum string2Enum(String status) {
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

    /**
     * 构建请求体
     * @param url aria2服务器地址
     * @param port aria2服务器端口
     * @return HttpPost
     */
    public static HttpPost postRequestBuilder(String url,String port){
        HttpPost httpPost = new HttpPost("http://"+url+":"+port+"/jsonrpc");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        return httpPost;
    }

}
