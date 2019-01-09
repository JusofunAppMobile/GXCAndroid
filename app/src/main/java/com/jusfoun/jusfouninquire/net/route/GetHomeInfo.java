package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.AdResultModel;
import com.jusfoun.jusfouninquire.net.model.AdvertiseModel;
import com.jusfoun.jusfouninquire.net.model.HomeRecentModel;
import com.jusfoun.jusfouninquire.net.model.HomeVersionDataListModel;
import com.jusfoun.jusfouninquire.net.model.NewHomeModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/17.
 * Description 获取首页信息
 */
public class GetHomeInfo {

    private static final String url="/api/FirstPage/GetNewPage";
    private static final String newUrl="/api/FirstPage/GetIndexPageNew";

    public static final String DYNAMIC_CONTENT_URL = "/api/FirstPage/GetDynamicContent";
    private static final String URL_AD = "/api/Notice/GetOneNotice";//获取首页广告信息
    private static final String URL_RECENT = "/api/entdetail/CountChangeGroupBy";//近期变更

    public static void getHomeInfo(Context context,HashMap<String,String> params, String tag,final NetWorkCallBack netWorkCallBack){

        VolleyGetRequest<NewHomeModel> getRequest=new VolleyGetRequest<NewHomeModel>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + newUrl, params)
                , NewHomeModel.class, new Response.Listener<NewHomeModel>() {
            @Override
            public void onResponse(NewHomeModel response) {

                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        },context){
            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
//        getRequest.setShouldCache(false);

        getRequest.setShouldCache(false);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT, 1, 1.0f));
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }

    /**
     * 在首页 获取广告数据
     * @param context
     * @param params
     * @param netWorkCallBack
     */
    public static void getAdvertise(Context context,HashMap<String,String> params,String tag, final NetWorkCallBack netWorkCallBack){
        String url = context.getString(R.string.req_url)+"/api/AdvertPage/GetAdvert";
        VolleyGetRequest<AdvertiseModel> getRequest=new VolleyGetRequest<AdvertiseModel>(url, AdvertiseModel.class, new Response.Listener<AdvertiseModel>() {
            @Override
            public void onResponse(AdvertiseModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        },context);
        getRequest.setShouldCache(false);
        getRequest.setTag(tag);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(2*1000, 1, 1.0f));
        VolleyUtil.getQueue(context).add(getRequest);
    }

    /*
    *  1.0.1需求，更新设置、个人中心、首页显示
    */
    public static void getNewVersionData(Context context,final NetWorkCallBack netWorkCallBack){
        String url = context.getString(R.string.req_url) + DYNAMIC_CONTENT_URL;
//        Log.e("tag", "getNewVersionData1=" + url);
        VolleyGetRequest<HomeVersionDataListModel> getRequest = new VolleyGetRequest<HomeVersionDataListModel>(url, HomeVersionDataListModel.class, new Response.Listener<HomeVersionDataListModel>() {
            @Override
            public void onResponse(HomeVersionDataListModel response) {
//                Log.e("tag","response.cache="+response.isCache+" response="+response);
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        },context);

        VolleyUtil.getQueue(context).getCache().invalidate(getRequest.getCacheKey(), false);
        getRequest.setShouldCache(true);
        VolleyUtil.getQueue(context).add(getRequest);
    }

    /**
     * 获取首页广告
     * */
    public static void getAdData(Context context,final NetWorkCallBack netWorkCallBack){
        String url = context.getString(R.string.req_url) + URL_AD;
        VolleyPostRequest<AdResultModel> getRequest = new VolleyPostRequest<>(url, AdResultModel.class, new Response.Listener<AdResultModel>() {
            @Override
            public void onResponse(AdResultModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        },context);

//        VolleyUtil.getQueue(context).getCache().invalidate(getRequest.getCacheKey(), false);
        getRequest.setShouldCache(false);
        VolleyUtil.getQueue(context).add(getRequest);
    }


    /**
     * 新首页接口
     * */
    public static void getNewHomeInfo(Context context,HashMap<String,String> params, String tag,final NetWorkCallBack netWorkCallBack){

        String url ="/api/FirstPage/GetIndexPageNew";
        VolleyGetRequest<NewHomeModel> getRequest=new VolleyGetRequest<NewHomeModel>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + url, params)
                , NewHomeModel.class, new Response.Listener<NewHomeModel>() {
            @Override
            public void onResponse(NewHomeModel response) {

                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        },context){
            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
//        getRequest.setShouldCache(false);

        getRequest.setShouldCache(false);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT, 1, 1.0f));
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }


    /**
     * 近期变更、企业雷达
     * */
    public static void getRecentChange(Context context, String TAG, HashMap<String, String> params, final NetWorkCallBack netWorkCallBack){
        String url = context.getString(R.string.req_url) + URL_RECENT;
        VolleyPostRequest<HomeRecentModel> getRequest = new VolleyPostRequest<>(GetParamsUtil.getParmas(url, params), HomeRecentModel.class, new Response.Listener<HomeRecentModel>() {
            @Override
            public void onResponse(HomeRecentModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        },context);

//        VolleyUtil.getQueue(context).getCache().invalidate(getRequest.getCacheKey(), false);
        getRequest.setShouldCache(false);
        VolleyUtil.getQueue(context).add(getRequest);
    }
}
