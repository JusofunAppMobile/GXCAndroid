package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.SearchHotModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * GetHotSearchRoute
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/15
 * @Description :获取热门词汇的网络路由器
 */
public class GetHotSearchRoute {


    /**
     * 获取搜索页面热门搜索词汇接口
     * @param context
     * @param params
     * @param tag
     * @param netWorkCallBack
     */
    public static void getHotSearch(Context context, final HashMap<String, String> params, String tag, final NetWorkCallBack netWorkCallBack) {
        String url = "/api/Tools/GetHotSearchWord";
        VolleyGetRequest<SearchHotModel> postRequest = new VolleyGetRequest<SearchHotModel>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+url,params)
                , SearchHotModel.class
                , new Response.Listener<SearchHotModel>() {
            @Override
            public void onResponse(SearchHotModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, context){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT, 1, 1.0f));
        postRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(postRequest);
    }


    /**
     * 输入时获取搜索热词
     * @param context
     * @param params
     * @param tag
     * @param netWorkCallBack
     */
    public static void getRelatedHotwords(Context context, final HashMap<String, String> params, String tag, final NetWorkCallBack netWorkCallBack) {
        String url = "/api/Tools/GetHotSearch";
        VolleyGetRequest<SearchHotModel> postRequest = new VolleyGetRequest<SearchHotModel>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+url,params)
                , SearchHotModel.class
                , new Response.Listener<SearchHotModel>() {
            @Override
            public void onResponse(SearchHotModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, context){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT, 1, 1.0f));
        postRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(postRequest);
    }


    /**
     * 输入时获取失信搜索热词
     * @param context
     * @param params
     * @param tag
     * @param netWorkCallBack
     */
    public static void getDishonestHotwords(Context context, final HashMap<String, String> params, String tag, final NetWorkCallBack netWorkCallBack) {
        String url = "/api/Tools/SXGetHotSearch";
        VolleyGetRequest<SearchHotModel> postRequest = new VolleyGetRequest<SearchHotModel>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+url,params)
                , SearchHotModel.class
                , new Response.Listener<SearchHotModel>() {
            @Override
            public void onResponse(SearchHotModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, context){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT, 1, 1.0f));
        postRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(postRequest);
    }


}
