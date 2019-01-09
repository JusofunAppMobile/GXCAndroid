package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.ConsultingStatisticsModel;
import com.jusfoun.jusfouninquire.net.model.HotCompanyListModel;
import com.jusfoun.jusfouninquire.net.model.HotConsultingListModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;

/**
 * Created by lsq on 2016/9/21.
 */

public class GetHotConsultingInfo {
    //热门资讯列表url
    private static final String newUrl = "/api/Branner/SearchInformationList";
    //统计资讯阅读数
    private static final String statisticsUrl="/api/Branner/InformationReadCount";

    /**
     * 热门咨询
     * @param context
     * @param tag
     * @param params
     * @param netWorkCallBack
     */
    public static void getHotConsultingInfo(Context context, String tag, final HashMap<String, String> params, final NetWorkCallBack netWorkCallBack) {
        VolleyPostRequest<HotConsultingListModel> getRequest = new VolleyPostRequest<HotConsultingListModel>(GetParamsUtil.getParmas(context.getString(R.string.req_url) + newUrl, params)
                , HotConsultingListModel.class, new Response.Listener<HotConsultingListModel>() {
            @Override
            public void onResponse(HotConsultingListModel response) {

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

    /**
     * 热门咨询统计 不需要结果回调
     * @param context
     * @param tag
     */
    public static void getHotConsultingStatistics(Context context,String newsid, String tag) {
        final HashMap<String, String> params=new HashMap<>();
        params.put("newsid",newsid);
        VolleyPostRequest<ConsultingStatisticsModel> getRequest = new VolleyPostRequest<ConsultingStatisticsModel>
                (GetParamsUtil.getParmas(context.getString(R.string.req_url) + statisticsUrl, params)
                , ConsultingStatisticsModel.class, new Response.Listener<ConsultingStatisticsModel>() {
            @Override
            public void onResponse(ConsultingStatisticsModel response) {

//                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
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
