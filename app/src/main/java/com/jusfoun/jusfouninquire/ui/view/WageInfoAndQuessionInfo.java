package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.route.WageInfoModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;

/**
 * Created by lsq on 2016/8/12.
 */
public class WageInfoAndQuessionInfo {
    private static final String newUrl = "/api/Tools/GetPropaganda";

    private static final String Url = "/api/Tools/GetPropaganda";
    private static final String QuestionnaireURL = "/api/Tools/FindQuestionnaires";
    private static final String NoticeServerURL = "/api/Tools/Participate";

    public static void getWageInfo(Context context, String tag, final HashMap<String, String> params, final NetWorkCallBack netWorkCallBack) {
        Log.e("ss","hao");
        VolleyGetRequest<WageInfoModel> getRequest = new VolleyGetRequest<WageInfoModel>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + newUrl, params)
                , WageInfoModel.class, new Response.Listener<WageInfoModel>() {
            @Override
            public void onResponse(WageInfoModel response) {

                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, context) {
            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
//        getRequest.setShouldCache(false);

        getRequest.setShouldCache(false);
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }
    public static void getQuessionInfo(Context context, String tag, final HashMap<String, String> params, final NetWorkCallBack netWorkCallBack) {
        VolleyGetRequest<WageInfoModel> getRequest = new VolleyGetRequest<WageInfoModel>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + Url, params)
                , WageInfoModel.class, new Response.Listener<WageInfoModel>() {
            @Override
            public void onResponse(WageInfoModel response) {

                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, context) {
            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };

        getRequest.setShouldCache(false);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT, 1, 1.0f));
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }


    public static void getQuestionnair(Context context, String tag, final HashMap<String, String> params, final NetWorkCallBack netWorkCallBack) {
        VolleyGetRequest<WageInfoModel> getRequest = new VolleyGetRequest<WageInfoModel>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + QuestionnaireURL, params)
                , WageInfoModel.class, new Response.Listener<WageInfoModel>() {
            @Override
            public void onResponse(WageInfoModel response) {

                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, context) {
            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
//        getRequest.setShouldCache(false);

        getRequest.setShouldCache(false);
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }


    public static void NoticeServer(Context context, String tag, final HashMap<String, String> params, final NetWorkCallBack netWorkCallBack){
        VolleyGetRequest<BaseModel> getRequest = new VolleyGetRequest<BaseModel>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + NoticeServerURL, params)
                , BaseModel.class, new Response.Listener<BaseModel>() {
            @Override
            public void onResponse(BaseModel response) {

                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, context);

        getRequest.setShouldCache(false);
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }

}
