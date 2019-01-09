package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaoyapeng
 * @version create time:15/11/2上午11:21
 * @Email zyp@jusfoun.com
 * @Description ${网络请求 路由}
 */
public class NetWorkRoute {

    static String url = "http://api.jusfoun.com/api_juxin/api/tools/get";
    static String getUrl = "http://www.baidu.com";

    public static void testPostNet(final Context mContext, final HashMap<String, String> map, final NetWorkCallBack callBck) {
        VolleyPostRequest<BaseModel> request = new VolleyPostRequest<BaseModel>(url, BaseModel.class, new Response.Listener<BaseModel>() {
            @Override
            public void onResponse(BaseModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        request.setShouldCache(false);
        // 设置 超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(90*1000,1,1.0f));
        VolleyUtil.getQueue(mContext).add(request);
    }

    public static void testGetNet(final Context mContext, final HashMap<String, String> map, final NetWorkCallBack callBck) {
        VolleyGetRequest<BaseModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url,map), BaseModel.class, new Response.Listener<BaseModel>() {

            @Override
            public void onResponse(BaseModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        VolleyUtil.getQueue(mContext).add(request);
    }
}
