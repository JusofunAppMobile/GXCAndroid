package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.BiddingListModel;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.net.model.RecruitListModel;
import com.jusfoun.jusfouninquire.net.model.TaxationModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaoyapeng
 * @version create time:17/9/1118:0
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class BiddingNetSource {
    /**
     *  获取 招投标 想你想
     * */
    public static void getBiddimgList(Context context, HashMap<String,String> params, String tag, final NetWorkCallBack netWorkCallBack){
        VolleyGetRequest<BiddingListModel> getRequest=new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+"/api/entdetail/GetBidWinningNoticeInfo", params), BiddingListModel.class
                , new Response.Listener<BiddingListModel>() {
            @Override
            public void onResponse(BiddingListModel response) {
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


    /**
     *  获取 招聘
     * */
    public static void getRecruitmentList(Context context, HashMap<String,String> params, String tag, final NetWorkCallBack netWorkCallBack){
        VolleyGetRequest<RecruitListModel> getRequest=new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+"/api/entdetail/GetCompInJobPage", params), RecruitListModel.class
                , new Response.Listener<RecruitListModel>() {
            @Override
            public void onResponse(RecruitListModel response) {
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

    /**
     *  获取 风险信息----欠税公告
     * */
    public static void getTaxationNet(Context context,final  HashMap<String,String> params, String tag, final NetWorkCallBack netWorkCallBack){
        VolleyPostRequest<TaxationModel> getRequest=new VolleyPostRequest<TaxationModel>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+"/api/entdetail/GetEntOwingTaxAnnouncement", params), TaxationModel.class
                , new Response.Listener<TaxationModel>() {
            @Override
            public void onResponse(TaxationModel response) {
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
