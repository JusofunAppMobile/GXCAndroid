package com.jusfoun.bigdata;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.CompanyMapDataModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;

/**
 * @author zhaoyapeng
 * @version create time:18/1/515:24
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class BigDataSource {

    public static final String GetAreaOrIndustryList = "api/JusFounBigData/GetAreaOrIndustryList";
    public static void getCompanyMap(Context context, HashMap<String,String> params, String tag, final NetWorkCallBack netWorkCallBack){
        VolleyGetRequest<CompanyMapDataModel> getRequest=new VolleyGetRequest<CompanyMapDataModel>(
                GetParamsUtil.getParmas(context.getString(R.string.req_url)+GetAreaOrIndustryList, params), CompanyMapDataModel.class, new Response.Listener() {
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
}
