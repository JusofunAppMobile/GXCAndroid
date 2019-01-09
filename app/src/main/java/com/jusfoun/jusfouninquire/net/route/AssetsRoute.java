package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.BrandListModel;
import com.jusfoun.jusfouninquire.net.model.CompanyListModel;
import com.jusfoun.jusfouninquire.net.model.PatentListModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;

/**
 * 无形资产
 *
 * @时间 2017/9/6
 * @作者 LiuGuangDan
 */

public class AssetsRoute {

    public static void brandListNet(Context context, HashMap<String, String> params, String tag, final NetWorkCallBack netWorkCallBack) {
        String url = "/api/entdetail/GetCompIcon";
        VolleyGetRequest<BrandListModel> getRequest = new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + url, params), BrandListModel.class
                , new Response.Listener<BrandListModel>() {
            @Override
            public void onResponse(BrandListModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, context);

        getRequest.setShouldCache(false);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT_COMPANY, 1, 1.0f));
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }

    public static void companyItem(Context context, HashMap<String, String> params, String tag, String url, final NetWorkCallBack netWorkCallBack) {
        VolleyGetRequest<CompanyListModel> getRequest = new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + url, params), CompanyListModel.class
                , new Response.Listener<CompanyListModel>() {
            @Override
            public void onResponse(CompanyListModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, context);

        getRequest.setShouldCache(false);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT_COMPANY, 1, 1.0f));
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }


    public static void patentListNet(Context context, HashMap<String, String> params, String tag, final NetWorkCallBack netWorkCallBack) {
        String url = "/api/entdetail/GetCompPatent";
        VolleyGetRequest<PatentListModel> getRequest = new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + url, params), PatentListModel.class
                , new Response.Listener<PatentListModel>() {
            @Override
            public void onResponse(PatentListModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, context);

        getRequest.setShouldCache(false);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT_COMPANY, 1, 1.0f));
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }

}
