package whu.edu.aria2rpc.service;

import org.apache.http.client.methods.HttpPost;

public class RequestBuilder {

    public static HttpPost postRequestBuilder(){
        HttpPost httpPost = new HttpPost("http://127.0.0.1:6800/jsonrpc");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        return httpPost;
    }
}
