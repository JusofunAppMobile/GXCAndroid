package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.model.QuestResultModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/20.
 * Description
 */
public class PostCompanyAmend {
    private static final String postUrl = "/Api/ErrorBack/InsertErrorBack";
    private static final String questionUrl = "/api/entdetail/GetQuestionResult";
    private static final String askQuestion = "/api/entdetail/PostQuestionResult";

    public static void postCompanyAmend(Context mContext, final HashMap<String, String> params, String tag, final NetWorkCallBack netWorkCallBack) {
        VolleyPostRequest<BaseModel> postRequest = new VolleyPostRequest<BaseModel>(mContext.getString(R.string.req_url) + postUrl
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
        }, mContext) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT, 1, 1.0f));
        postRequest.setTag(tag);
        VolleyUtil.getQueue(mContext).add(postRequest);
    }

    /**
     * 获取问答列表
     */
    public static void getQuestionList(Context context, HashMap<String, String> params, final NetWorkCallBack netWorkCallBack) {
        String url = context.getString(R.string.req_url) + questionUrl;
        VolleyPostRequest<QuestResultModel> getRequest = new VolleyPostRequest<>(Request.Method.GET, GetParamsUtil.getParmas(url, params), QuestResultModel.class, new Response.Listener<QuestResultModel>() {
            @Override
            public void onResponse(QuestResultModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, context);

//        VolleyUtil.getQueue(context).getCache().invalidate(getRequest.getCacheKey(), false);
        getRequest.setShouldCache(false);
        VolleyUtil.getQueue(context).add(getRequest);
    }

    /**
     * 回答问题
     */
    public static void askQuestion(Context context, HashMap<String, String> params, final NetWorkCallBack netWorkCallBack) {
        String url = context.getString(R.string.req_url) + askQuestion;
        VolleyPostRequest<BaseModel> getRequest = new VolleyPostRequest<>(GetParamsUtil.getParmas(url, params), BaseModel.class, new Response.Listener<BaseModel>() {
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
        VolleyUtil.getQueue(context).add(getRequest);
    }
}
