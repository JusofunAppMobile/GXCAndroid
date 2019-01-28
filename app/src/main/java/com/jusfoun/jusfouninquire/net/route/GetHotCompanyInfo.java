package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.HomeDataItemModel;
import com.jusfoun.jusfouninquire.net.model.HotCompanyListModel;
import com.jusfoun.jusfouninquire.net.model.NewHomeModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;

/**
 * Created by lsq on 2016/8/11.
 */
public class GetHotCompanyInfo {
    private static final String newUrl = "/api/FirstPage/GetSDHotCompanyList";

    public static void getHotCompanyInfo(Context context, String tag, final HashMap<String, String> params, final NetWorkCallBack netWorkCallBack) {
        VolleyGetRequest<HotCompanyListModel> getRequest = new VolleyGetRequest<HotCompanyListModel>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + newUrl, params)
                , HotCompanyListModel.class, new Response.Listener<HotCompanyListModel>() {
            @Override
            public void onResponse(HotCompanyListModel response) {

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
        getRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT, 1, 1.0f));
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }
}