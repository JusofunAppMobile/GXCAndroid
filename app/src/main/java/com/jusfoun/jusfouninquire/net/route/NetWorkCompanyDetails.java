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
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.net.model.ReportModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;
import com.jusfoun.jusfouninquire.ui.view.CompanyDetailMenuView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/10.
 * Description 获取企业详情
 */
public class NetWorkCompanyDetails {

    private final static String getUrl="/api/entdetail/GetCompanyDetails_beta";
    private final static String updateUrl="/api/EntAll/RefreshEntInfo";
    private final static String riskInformationUrl="/api/entdetail/GetRiskInformation_beta";//风险信息九宫格
    private final static String businessinformationUrl="/api/entdetail/GetBusinessInformation_beta";// 经营状况 九宫格
    private final static String intangibleassetsurl="/api/entdetail/GetIntangibleAssets_beta";// 无形资产 九宫格
    private final static String getCompanyUpdateState="/api/EntAll/GetRefreshEntInfo";//获取企业更新 状态
    private final static String reporturl="/api/User/GetReportLink";//企业报告 查看
    public static void getCompanyDetails(Context context, final HashMap<String,String> params, String tag, final NetWorkCallBack netWorkCallBack){
        VolleyGetRequest<CompanyDetailModel> getRequest=new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+getUrl, params), CompanyDetailModel.class
                , new Response.Listener<CompanyDetailModel>() {
            @Override
            public void onResponse(CompanyDetailModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        },context);

        getRequest.setShouldCache(false);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT_COMPANY, 1, 1.0f));
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }

    public static void updateCompanyDetails(Context context,HashMap<String,String> params,String tag,final NetWorkCallBack netWorkCallBack){
        VolleyGetRequest<BaseModel> getRequest=new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+updateUrl, params), BaseModel.class
                , new Response.Listener<BaseModel>() {
            @Override
            public void onResponse(BaseModel response) {
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
     *  获取九宫格
     * */
    public static void getMenuList(Context context,int type,HashMap<String,String> params,String tag,final NetWorkCallBack netWorkCallBack){
         if(type== CompanyDetailMenuView.TYPE_RISKINFO){
             getRiskInformation(context,params,tag,netWorkCallBack);
         }else if(type==CompanyDetailMenuView.TYPE_OPERATINGCONDITIONS){
             getBusinessInformation(context,params,tag,netWorkCallBack);
         }else if(type==CompanyDetailMenuView.TYPE_INTANGIBLEASSETS){
             getIntangibleAssets(context,params,tag,netWorkCallBack);
         }
    }

    /**
     *  获取 风险信息九宫格
     * */
    public static void getRiskInformation(Context context,HashMap<String,String> params,String tag,final NetWorkCallBack netWorkCallBack){
        VolleyGetRequest<CompanyDetailModel> getRequest=new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+riskInformationUrl, params), CompanyDetailModel.class
                , new Response.Listener<CompanyDetailModel>() {
            @Override
            public void onResponse(CompanyDetailModel response) {
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
     *  获取 经营状况
     * */
    public static void getBusinessInformation(Context context,HashMap<String,String> params,String tag,final NetWorkCallBack netWorkCallBack){
        VolleyGetRequest<CompanyDetailModel> getRequest=new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+businessinformationUrl, params), CompanyDetailModel.class
                , new Response.Listener<CompanyDetailModel>() {
            @Override
            public void onResponse(CompanyDetailModel response) {
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
     *  获取 五星自资产
     * */
    public static void getIntangibleAssets(Context context,HashMap<String,String> params,String tag,final NetWorkCallBack netWorkCallBack){
        VolleyGetRequest<CompanyDetailModel> getRequest=new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+intangibleassetsurl, params), CompanyDetailModel.class
                , new Response.Listener<CompanyDetailModel>() {
            @Override
            public void onResponse(CompanyDetailModel response) {
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
     *  获取 五星自资产
     * */
    public static void getReportUrl(Context context,HashMap<String,String> params,String tag,final NetWorkCallBack netWorkCallBack){
        VolleyGetRequest<ReportModel> getRequest=new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+reporturl, params), ReportModel.class
                , new Response.Listener<ReportModel>() {
            @Override
            public void onResponse(ReportModel response) {
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
     *  获取 企业更新状态信息
     * */
    public static void getCompanyUpdateState(Context context,HashMap<String,String> params,String tag,final NetWorkCallBack netWorkCallBack){
        VolleyGetRequest<CompanyDetailModel> getRequest=new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url)+getCompanyUpdateState, params), CompanyDetailModel.class
                , new Response.Listener<CompanyDetailModel>() {
            @Override
            public void onResponse(CompanyDetailModel response) {
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
