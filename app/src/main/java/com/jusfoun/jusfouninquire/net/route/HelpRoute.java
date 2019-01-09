package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.HelpModel;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaoyapeng
 * @version create time:16/8/1115:01
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class HelpRoute {


    /**
     * 新首页接口
     */
    public static void getNewHomeInfo(Context context, final HashMap<String, String> params, String tag, final NetWorkCallBack netWorkCallBack) {

//        String url = "/Api/FirstPage/GetHelperTxt";
        String url = "/api/firstpage/GetHelperTxtNew";

        VolleyPostRequest<HelpModel> getRequest = new VolleyPostRequest<HelpModel>(context.getString(R.string.req_url)+url, HelpModel.class, new Response.Listener<HelpModel>() {
            @Override
            public void onResponse(HelpModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        },context){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

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

}
