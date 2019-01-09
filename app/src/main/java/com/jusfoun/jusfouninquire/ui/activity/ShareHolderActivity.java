package com.jusfoun.jusfouninquire.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.TimeOut;
import com.jusfoun.jusfouninquire.net.model.CompanyListModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;
import com.jusfoun.jusfouninquire.ui.widget.shareholder.ShareHolderDataModel;

import java.util.HashMap;
import java.util.Map;


/**
 * @author zhaoyapeng
 * @version create time:18/8/2010:03
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class ShareHolderActivity extends BaseInquireActivity {
    public static final String COMPANY_ID = "company_id";
    public static final String COMPANY_NAME = "company_name";
    private String mCompanyId = "", mCompanyName;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString(COMPANY_ID) != null)
                mCompanyId = bundle.getString(COMPANY_ID);
            mCompanyName = bundle.getString(COMPANY_NAME);
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_share_holder);
    }

    @Override
    protected void initWidgetActions() {
        loadData();
    }


    private void loadData() {

        final HashMap<String, String> params = new HashMap<>();


        String userId;
        if(InquireApplication.getUserInfo() == null){
            userId = "";
        }else {
            userId = TextUtils.isEmpty(InquireApplication.getUserInfo().getUserid()) ? "" : InquireApplication.getUserInfo().getUserid();
        }

        params.put("userId", userId);
        params.put("entId", mCompanyId);
        TimeOut timeOut = new TimeOut(InquireApplication.application);
        params.put("t", timeOut.getParamTimeMollis()+"");
        params.put("m", timeOut.MD5time()+"");

        String url = "/api/User/GDCTDetail";
        VolleyPostRequest<ShareHolderDataModel> getRequest = new VolleyPostRequest<ShareHolderDataModel>(GetParamsUtil.getParmas(getString(R.string.req_url) + url, params), ShareHolderDataModel.class
                , new Response.Listener<ShareHolderDataModel>() {
            @Override
            public void onResponse(ShareHolderDataModel response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }, this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        getRequest.setShouldCache(false);
        getRequest.setTag(TAG);
        VolleyUtil.getQueue(this).add(getRequest);
    }
}
