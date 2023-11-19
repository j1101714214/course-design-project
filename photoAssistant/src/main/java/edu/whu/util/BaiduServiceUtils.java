package edu.whu.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author Akihabara
 * @version 1.0
 * @description BaiduServiceUtils: 百度地图服务工具类
 * @date 2023/11/12 18:44
 */
public class BaiduServiceUtils {
    private static final String AK = "U2WkQ1pGNOVccHI6PQRwjNsHZgc0fVOL";
    private static final String API = "https://api.map.baidu.com/reverse_geocoding/v3/?ak={}&output=json&coordtype=wgs84ll&location={},{}";

    public static String getLocationFromGPS(String latitude, String longitude) {
        String uri = StrUtil.format(API, AK, dms2Dd(latitude), dms2Dd(longitude));
        HttpRequest httpRequest = HttpRequest.get(uri);
        HttpResponse response = httpRequest.execute();

        // 构建地址
        String body = response.body();
        JSONObject parseObj = JSONUtil.parseObj(body);
        if(parseObj.getInt("status") == 0) {
            JSONObject result = parseObj.getJSONObject("result");
            return result.getStr("formatted_address");
        }
        return "";
    }

    //将度分秒转换为经纬度
    public static double dms2Dd(String dms) {
        double result = 0.0;
        String[] parts = dms.split("° |' |\"");
        double deg = Double.parseDouble(parts[0]);
        double min = Double.parseDouble(parts[1]);
        double sec = Double.parseDouble(parts[2]);
        result = deg + min/60  + sec/3600;
        return result;
    }
}
