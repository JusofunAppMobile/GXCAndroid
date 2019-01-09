package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.model.MyReportModel;
import com.jusfoun.jusfouninquire.net.model.RecentChangeItemModel;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

import netlib.util.AppUtil;

/**
 * 企业报告
 *
 * @时间 2017/10/23
 * @作者 LiuGuangDan
 */

public class ReportRoute {

    /**
     * 下载企业报告，发送个邮件
     */
    public static void sendEmail(Context mContext, final HashMap<String, String> map, String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url) + "/api/User/RequestReport";
        String finalUrl = AppUtil.appendMapParam(url, map);
        VolleyPostRequest<BaseModel> request = new VolleyPostRequest<BaseModel>(finalUrl, BaseModel.class, new Response.Listener<BaseModel>() {
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
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }


    /**
     * 下载多个企业报告，发送个邮件
     */
    public static void sendReportMoreEmail(Context mContext, final HashMap<String, String> map, String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url) + "/api/user/RequestReportMultiple";
        String finalUrl = AppUtil.appendMapParam(url, map);
        VolleyPostRequest<BaseModel> request = new VolleyPostRequest<BaseModel>(finalUrl, BaseModel.class, new Response.Listener<BaseModel>() {
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
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /**
     * 发送企业通讯录
     */
    public static void sendContacts(Context mContext, final Map<String, String> map,final Map<String, Object> bodyMap, String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url) + "/api/Contacts/Post";
        String finalUrl = AppUtil.appendMapParam(url, map);

        Log.e("tag","VolleyPostRequest="+bodyMap+"   "+map);
        VolleyPostRequest<BaseModel> request = new VolleyPostRequest<BaseModel>(finalUrl,bodyMap, BaseModel.class, new Response.Listener<BaseModel>() {
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
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }


    /**
     * 我的报告
     */
    public static void myReport(Context mContext, final HashMap<String, String> map, String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url) + "/api/User/GetReportListLink";
        String finalUrl = AppUtil.appendMapParam(url, map);
        VolleyPostRequest<MyReportModel> request = new VolleyPostRequest<>(Request.Method.GET, finalUrl, MyReportModel.class, new Response.Listener<MyReportModel>() {
            @Override
            public void onResponse(MyReportModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);

        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }


    /**
     * 我的报告
     */
    public static void recentChange(Context mContext, final HashMap<String, String> map, String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url) + "/api/entdetail/GetChangeByType";
        String finalUrl = AppUtil.appendMapParam(url, map);
        VolleyPostRequest<RecentChangeItemModel> request = new VolleyPostRequest<>(finalUrl, RecentChangeItemModel.class, new Response.Listener<RecentChangeItemModel>() {
            @Override
            public void onResponse(RecentChangeItemModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);

        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }


}
