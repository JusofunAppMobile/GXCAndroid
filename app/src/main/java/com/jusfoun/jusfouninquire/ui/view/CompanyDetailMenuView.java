package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.TimeOut;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.route.NetWorkCompanyDetails;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailsActivity;
import com.jusfoun.jusfouninquire.ui.adapter.CompanyMenuAdapter;

import java.util.HashMap;

import netlib.util.EventUtils;

/**
 * @author zhaoyapeng
 * @version create time:17/9/711:21
 * @Email zyp@jusfoun.com
 * @Description ${企业详情 九宫格 view}
 */
public class CompanyDetailMenuView extends RelativeLayout {
    private Context mContext;
    private RecyclerView mCompanyMenu;
    private CompanyMenuAdapter adapter;
    private TextView titleText;
    private String companyId, companyName;
    public static final int TYPE_RISKINFO = 1;
    public static final int TYPE_OPERATINGCONDITIONS = 2;
    public static final int TYPE_INTANGIBLEASSETS = 3;

    private UserInfoModel userInfo;
    private CompanyDetailModel companyDetailModel;

    public CompanyDetailMenuView(Context context) {
        super(context);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    public CompanyDetailMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    public CompanyDetailMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    private void initData() {
        adapter = new CompanyMenuAdapter(mContext);
        userInfo = InquireApplication.getUserInfo();
    }

    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_company_detail_menu, this, true);
        mCompanyMenu = (RecyclerView) findViewById(R.id.recyclerview);
        titleText = (TextView) findViewById(R.id.text_title);
    }

    private void initActions() {
        mCompanyMenu.setLayoutManager(new GridLayoutManager(mContext, 4));
        mCompanyMenu.setAdapter(adapter);
        AppUtils.addItemDecoration(mContext, mCompanyMenu);

        adapter.setOnItemClickListener(new CompanyMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String umeng) {
                EventUtils.event2(mContext,umeng);
                if (companyDetailModel == null)
                    return;

                Bundle argument = new Bundle();
                argument.putSerializable(CompanyDetailsActivity.COMPANY, companyDetailModel);
                argument.putInt(CompanyDetailsActivity.POSITION, position);

                Intent intent = new Intent(mContext, CompanyDetailsActivity.class);
                intent.putExtras(argument);
                mContext.startActivity(intent);
            }
        });


    }

    public void setData(int type, CompanyDetailModel companyDetailModel, String companyId, String compantName) {
        this.companyDetailModel = companyDetailModel.getCloneModel();
        this.companyId = companyId;
        this.companyName = compantName;

        if(type==TYPE_RISKINFO){
            titleText.setText("风险信息");
        }else if(type==TYPE_OPERATINGCONDITIONS){
            titleText.setText("经营状况");
        }else if(type==TYPE_INTANGIBLEASSETS){
            titleText.setText("无形资产");
        }
        getMenuData(type);
    }

    public void setOnItemClickListener(CompanyMenuAdapter.OnItemClickListener listener) {
        adapter.setOnItemClickListener(listener);
    }

    private void getMenuData(int type) {
        HashMap<String, String> params = new HashMap<String,String>();
        TimeOut timeOut = new TimeOut(mContext);
        params = new HashMap<>();
        params.put("companyid", companyId);
        params.put("entname", companyName == null ? "" : companyName);
        params.put("companyname", companyName == null ? "" : companyName);

        if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserid()))
            params.put("userid", userInfo.getUserid());
        else {
            params.put("userid", "");
        }
        params.put("t", timeOut.getParamTimeMollis() + "");
        params.put("m", timeOut.MD5time() + "");
        NetWorkCompanyDetails.getMenuList(mContext, type, params, "CompanyDetailsItemHttp", new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                CompanyDetailModel model = (CompanyDetailModel) data;
                if (model.getResult() == 0) {
                    companyDetailModel.setSubclassMenu(model.getSubclassMenu());
                    adapter.refresh(model.getSubclassMenu());
                }
            }

            @Override
            public void onFail(String error) {
            }
        });
    }
}
