package com.jusfoun.jusfouninquire.ui.util;

import android.net.Uri;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;

import java.util.HashMap;
import java.util.Map;

/**
 * Author  wangchenchen
 * CreateDate 2015/12/9.
 * Email wcc@jusfoun.com
 * Description 百度地图相关工具类
 */
public class BaiduMapUtil {

    /**
     * 获取百度静态地图
     * @param point 地址信息
     * @return
     */
    public static Uri getBaiduMapUrl(LatLng point) {
        HashMap<String, String> params = new HashMap<>();
        String url = "http://api.map.baidu.com/staticimage?";
        params.put("center", point.latitude + "," + point.longitude);
        params.put("zoom", "17");
        params.put("markers", point.latitude + "," + point.longitude);
        params.put("markerStyles", "-1,http://api.map.baidu.com/images/marker_red.png");
        Uri.Builder builder = Uri.parse(url).buildUpon();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (param.getValue() != null)
                builder.appendQueryParameter(param.getKey(), param.getValue());
        }
        Log.e("url", builder.build().toString());
        return builder.build();
    }
}
