package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.SearchDisHonestModel;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * SearchDishonestRoute
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/31
 * @Description :失信相关搜索接口
 */
public class SearchDishonestRoute {

    public static void searchDishonest(Context context,String tag, final HashMap<String,String> params, final NetWorkCallBack netWorkCallBack){
        String url = context.getString(R.string.req_url) + "/api/BlackListNew/BlackListSearch";
        VolleyPostRequest<SearchDisHonestModel> request=new VolleyPostRequest<SearchDisHonestModel>(url, SearchDisHonestModel.class
                , new Response.Listener<SearchDisHonestModel>() {
            @Override
            public void onResponse(SearchDisHonestModel searchCreditModel) {
                netWorkCallBack.onSuccess(searchCreditModel);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(volleyError));
            }
        },context){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(context).add(request);
    }


}
