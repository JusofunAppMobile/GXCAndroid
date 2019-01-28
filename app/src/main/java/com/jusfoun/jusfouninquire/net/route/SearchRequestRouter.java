package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.AdvancedConfigModel;
import com.jusfoun.jusfouninquire.net.model.FocusedModel;
import com.jusfoun.jusfouninquire.net.model.SearchDisHonestModel;
import com.jusfoun.jusfouninquire.net.model.SearchResultModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;

/**
 * Created by Albert on 2015/11/10.
 * Mail : lbh@jusfoun.com
 * TODO :搜索相关网络请求
 */
public class SearchRequestRouter {
    //private static final String SEARCH_DISHONEST_URL = "http://192.168.1.6:8248/blacklist/BlackListSearch";


    /*
    * 搜索失信接口
    * */
    public static void SearchDishonest(final Context mContext, final HashMap<String, String> map, String tag,final NetWorkCallBack callBck){
        String url = mContext.getResources().getString(R.string.req_url) + "/api/BlackListNew/BlackListSearch";
        VolleyPostRequest<SearchDisHonestModel> request = new VolleyPostRequest<>(GetParamsUtil.getParmas(url, map), SearchDisHonestModel.class, new Response.Listener<SearchDisHonestModel>() {

            @Override
            public void onResponse(SearchDisHonestModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT, 1, 1.0f));
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /*
    * 搜索企业接口
    * */
    public static void SearchIndustry(final Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck){
        String url = mContext.getResources().getString(R.string.req_url) + "/api/entdetail/GetEntListByKey";
        VolleyGetRequest<SearchResultModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), SearchResultModel.class, new Response.Listener<SearchResultModel>() {

            @Override
            public void onResponse(SearchResultModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /*
    * 获取我的关注接口
    * */
    public static void getFocused(final Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck){
        String url = mContext.getResources().getString(R.string.req_url) + "/api/Attend/GetAttend";
        VolleyGetRequest<FocusedModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), FocusedModel.class, new Response.Listener<FocusedModel>() {

            @Override
            public void onResponse(FocusedModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /*
    * 获取高级搜索条件接口
    * */

    public static void getAdvancedConfig(final Context mContext, final HashMap<String, String> map, String tag,final NetWorkCallBack callBck){
        String url = mContext.getResources().getString(R.string.req_url) + "/api/entdetail/GetSearchItemIsShowProperdyList";
        VolleyGetRequest<AdvancedConfigModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), AdvancedConfigModel.class, new Response.Listener<AdvancedConfigModel>() {

            @Override
            public void onResponse(AdvancedConfigModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /*
    * 高级搜索接口
    * */

    public static void doAdvancedSearch(final Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck){
        String url = mContext.getResources().getString(R.string.req_url) + "/api/entdetail/GetEntList";
        VolleyGetRequest<SearchResultModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), SearchResultModel.class, new Response.Listener<SearchResultModel>() {

            @Override
            public void onResponse(SearchResultModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }
}
