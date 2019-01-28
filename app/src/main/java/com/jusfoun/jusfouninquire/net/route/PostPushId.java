package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/19.
 * Description 提交唯一标示
 */
public class PostPushId{
    private static final String postUrl="/api/SendPush/AddSendPush";

    public static void postPushId(Context mContext, final HashMap<String,String> params, final NetWorkCallBack netWorkCallBack){
        VolleyPostRequest<BaseModel> postRequest=new VolleyPostRequest<BaseModel>(mContext.getString(R.string.req_url) + postUrl
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
        },mContext){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(2*1000, 1, 1.0f));
        VolleyUtil.getQueue(mContext).add(postRequest);
    }
}
