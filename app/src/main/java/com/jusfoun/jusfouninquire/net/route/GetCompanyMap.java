package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.CompanyMapDataModel;
import com.jusfoun.jusfouninquire.net.model.CompanyMapDetailDataModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/13.
 * Description
 */
public class GetCompanyMap {

    private static final  String getUrl="/api/EntAll/GetEntAtlasData";
    private static final String getDetailUrl="/api/EntAll/GetEntAtlasEntDetail";
    public static void getCompanyMap(Context context,HashMap<String,String> params, String tag,final NetWorkCallBack netWorkCallBack){
        VolleyGetRequest<CompanyMapDataModel> getRequest=new VolleyGetRequest<CompanyMapDataModel>(
                GetParamsUtil.getParmas(context.getString(R.string.req_url)+getUrl, params), CompanyMapDataModel.class, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, context){

        };

        getRequest.setShouldCache(false);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT, 1, 1.0f));
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }

    public static void getCompanyMapDetail(Context context,HashMap<String,String> params, String tag,final NetWorkCallBack netWorkCallBack){
        VolleyGetRequest<CompanyMapDetailDataModel> getRequest=new VolleyGetRequest<>(
                GetParamsUtil.getParmas(context.getString(R.string.req_url)+getDetailUrl,params)
                ,CompanyMapDetailDataModel.class, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        netWorkCallBack.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
                    }
                },context);
        getRequest.setShouldCache(false);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT, 1, 1.0f));
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }
}
