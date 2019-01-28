package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.AppRecommendModel;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.model.CheckVersionModel;
import com.jusfoun.jusfouninquire.net.model.FocusedModel;
import com.jusfoun.jusfouninquire.net.model.LoginModel;
import com.jusfoun.jusfouninquire.net.model.SystemMsgModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lee
 * @version create time:2015/11/1114:16
 * @Email email
 * @Description $个人中心的一系列
 */

public class PersonCenterHelper {

    /**post*/
    public static void doNetPOSTToRegister(Context mContext, final HashMap<String, String> map, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/UserNotices/List";
        VolleyPostRequest<LoginModel> request = new VolleyPostRequest<LoginModel>(url, LoginModel.class, new Response.Listener<LoginModel>() {
            @Override
            public void onResponse(LoginModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        },mContext){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        request.setShouldCache(false);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /**意见反馈*/
    public static void doNetPostFeedBack(Context mContext, final HashMap<String, String> map, String tag,final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/Suggestion/InSuggestion";
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
        },mContext){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }
    /**我的关注*/
    public static void doNetGetMyAttention(Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/Attend/GetAttend";
        VolleyGetRequest<FocusedModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), FocusedModel.class,
                new Response.Listener<FocusedModel>() {

                    @Override
                    public void onResponse(FocusedModel response) {
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
    /**获取系统消息*/
    public static void doNetGetSystemMsg(Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/UserNotices/List";
        VolleyGetRequest<SystemMsgModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), SystemMsgModel.class,
                new Response.Listener<SystemMsgModel>() {

            @Override
            public void onResponse(SystemMsgModel response) {
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
     * 处理系统消息的网络请求,
     * @param mContext
     * @param map
     * @param tag
     * @param callBck
     */
    public static void dealSystemMsg(Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/UserNotices/UtUserNoticeReads";
        VolleyGetRequest<BaseModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), BaseModel.class,
                new Response.Listener<BaseModel>() {

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

    /**推荐应用*/
    public static void doNetGetRecommonApp(Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/ReApplication/GetReApplications";
        VolleyGetRequest<AppRecommendModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), AppRecommendModel.class,
                new Response.Listener<AppRecommendModel>() {

                    @Override
                    public void onResponse(AppRecommendModel response) {
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
    /**检查更新*/
    public static void doNetGetCheckVersion(Context mContext, final HashMap<String, String> map, String tag,final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/VersionNumber/GetVersionNumber";
        VolleyGetRequest<CheckVersionModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), CheckVersionModel.class,
                new Response.Listener<CheckVersionModel>() {

                    @Override
                    public void onResponse(CheckVersionModel response) {
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
