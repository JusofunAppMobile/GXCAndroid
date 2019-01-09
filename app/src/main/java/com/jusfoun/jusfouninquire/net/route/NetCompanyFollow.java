package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.FollowModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/16.
 * Description 关注/取消关注
 */
public class NetCompanyFollow {

    private static final String url = "/api/Attend/UpDateAttend";

    public static void putCompanyFollow(Context context, final HashMap<String, String> params,String tag, final NetWorkCallBack netWorkCallBack) {
        VolleyGetRequest<FollowModel> postRequest = new VolleyGetRequest<FollowModel>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+url,params)
                , FollowModel.class
                , new Response.Listener<FollowModel>() {
            @Override
            public void onResponse(FollowModel response) {
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
