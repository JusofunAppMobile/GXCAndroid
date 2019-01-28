package com.jusfoun.bigdata.http;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.bigdata.BaseCompanySituationModel;
import com.jusfoun.bigdata.NearMapModel;
import com.jusfoun.bigdata.UrlConstant;
import com.jusfoun.bigdata.XListModel;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;
import com.siccredit.guoxin.R;

import java.util.HashMap;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1609:36
 * @Email zyp@jusfoun.com
 * @Description ${附近企业 相关接口}
 */
public class NearMapSource  {


    /**
     *  获取企业列表列表
     * */
    public void getCompanyList(Context mContext, HashMap<String,String>params,String tag,final NetWorkCallBack netWorkCallBack) {

        VolleyGetRequest<XListModel> getRequest=new VolleyGetRequest<>(GetParamsUtil.getParmas(mContext.getString(R.string.req_url) + UrlConstant.GetMapCompanyListV4_0_3, params)
                , XListModel.class, new Response.Listener<XListModel>() {
            @Override
            public void onResponse(XListModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        },mContext);
        getRequest.setShouldCache(false);
        getRequest.setTag(tag);
        VolleyUtil.getQueue(mContext).add(getRequest);

    }


    /**
     *  获取地图列表
     * */
    public void getMapList(Context mContext, HashMap<String,String>params,String tag,final NetWorkCallBack netWorkCallBack) {

        VolleyGetRequest<NearMapModel> getRequest=new VolleyGetRequest<>(GetParamsUtil.getParmas(mContext.getString(R.string.req_url) + UrlConstant.GetMapListV4_0_3, params)
                , NearMapModel.class, new Response.Listener<NearMapModel>() {
            @Override
            public void onResponse(NearMapModel response) {

                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        },mContext);
        getRequest.setShouldCache(false);
        getRequest.setTag(tag);
        VolleyUtil.getQueue(mContext).add(getRequest);

    }



    /**
     *  获取某经纬度处 的企业列表
     * */
    public void getXlistDataByLaglon(Context mContext, HashMap<String,String>params,String tag,final NetWorkCallBack netWorkCallBack) {

        VolleyGetRequest<XListModel> getRequest=new VolleyGetRequest<>(GetParamsUtil.getParmas(mContext.getString(R.string.req_url) + UrlConstant.GetMapCompanyByLaglon, params)
                , XListModel.class, new Response.Listener<XListModel>() {
            @Override
            public void onResponse(XListModel response) {

                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        },mContext);
        getRequest.setShouldCache(false);
        getRequest.setTag(tag);
        VolleyUtil.getQueue(mContext).add(getRequest);

    }


    /**
     *  企业概况
     * */
    public  void getCompanySituationData(Context mContext, HashMap<String,String>params,String tag,final NetWorkCallBack netWorkCallBack){
        VolleyPostRequest<BaseCompanySituationModel> getRequest=new VolleyPostRequest<>(GetParamsUtil.getParmas(mContext.getString(R.string.req_url) + UrlConstant.GetEntBasicFacts, params)
                , BaseCompanySituationModel.class, new Response.Listener<BaseCompanySituationModel>() {
            @Override
            public void onResponse(BaseCompanySituationModel response) {

                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        },mContext);
//        getRequest.setShouldCache(false);

        getRequest.setShouldCache(false);
        getRequest.setTag(tag);
        VolleyUtil.getQueue(mContext).add(getRequest);


    }
}
