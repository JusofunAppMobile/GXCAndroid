package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.model.FilterModel;
import com.jusfoun.jusfouninquire.net.model.RecruitmentModel;
import com.jusfoun.jusfouninquire.net.model.SearchContactListModel;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchListModel;
import com.jusfoun.jusfouninquire.net.model.TaxationDataModel;
import com.jusfoun.jusfouninquire.net.model.ZFilterListModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;

/**
 * @author zhaoyapeng
 * @version create time:16/8/1310:25
 * @Email zyp@jusfoun.com
 * @Description ${搜索先关router}
 */
public class SearchRoute {
    public static void searchNet(Context context, HashMap<String, String> params, String tag, final NetWorkCallBack netWorkCallBack) {
        String url = "/api/Search/GetSear";

        // 企业通讯录
        if (params != null && !params.isEmpty() && params.containsKey("type") && SearchHistoryItemModel.SEARCH_CONTACT.equals(params.get("type"))) {
            url = "/api/Contacts/get";

            VolleyGetRequest<SearchContactListModel> getRequest = new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + url, params), SearchContactListModel.class
                    , new Response.Listener<SearchContactListModel>() {
                @Override
                public void onResponse(SearchContactListModel response) {
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
        } else {
            VolleyGetRequest<SearchListModel> getRequest = new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + url, params), SearchListModel.class
                    , new Response.Listener<SearchListModel>() {
                @Override
                public void onResponse(SearchListModel response) {
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


    public static void filterNet(Context context, HashMap<String, String> params, String tag, final NetWorkCallBack netWorkCallBack) {
        String url = "/api/Tools/GetScreen";
        VolleyGetRequest<ZFilterListModel> getRequest = new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + url, params), ZFilterListModel.class
                , new Response.Listener<ZFilterListModel>() {
            @Override
            public void onResponse(ZFilterListModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, context);

        getRequest.setShouldCache(true);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT_COMPANY, 1, 1.0f));
        getRequest.setTag(tag);
        VolleyUtil.getQueue(context).add(getRequest);
    }

    /**
     * 搜索筛选词接口
     *
     * @param mContext
     * @param map
     * @param tag
     * @param callBck
     */
    public static void getFilter(final Context mContext, final HashMap<String, String> map, String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url) + "/Api/FirstPage/GetKeyWordSummary";
        VolleyPostRequest<FilterModel> request = new VolleyPostRequest<>(GetParamsUtil.getParmas(url, map), FilterModel.class, new Response.Listener<FilterModel>() {

            @Override
            public void onResponse(FilterModel response) {
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

    /**
     * 失信筛选词接口
     *
     * @param mContext
     * @param map
     * @param tag
     * @param callBck
     */
    public static void getDisFilter(final Context mContext, final HashMap<String, String> map, String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url) + "/Api/Tools/PriviceListDeal";
        VolleyGetRequest<FilterModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), FilterModel.class, new Response.Listener<FilterModel>() {

            @Override
            public void onResponse(FilterModel response) {
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


    /**
     * 搜索税号
     */
    public static void searchTaxIdNet(Context context, HashMap<String, String> params, String tag, final NetWorkCallBack netWorkCallBack) {
        String url = "/api/search/GetSearByRegCode";
        VolleyGetRequest<TaxationDataModel> getRequest = new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + url, params), TaxationDataModel.class
                , new Response.Listener<TaxationDataModel>() {
            @Override
            public void onResponse(TaxationDataModel response) {
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


    /**
     * 搜索招聘
     */
    public static void searchRecruitmentNet(Context context, HashMap<String, String> params, String tag, final NetWorkCallBack netWorkCallBack) {
        String url = "/api/entdetail/GetCompInJobPageAll";
        VolleyGetRequest<RecruitmentModel> getRequest = new VolleyGetRequest<>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + url, params), RecruitmentModel.class
                , new Response.Listener<RecruitmentModel>() {
            @Override
            public void onResponse(RecruitmentModel response) {
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
