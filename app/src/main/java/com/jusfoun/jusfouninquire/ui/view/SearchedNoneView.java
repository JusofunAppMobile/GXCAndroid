package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.activity.AdvancedSearchActivity;
import com.jusfoun.jusfouninquire.ui.activity.ManuallySearchActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;

import netlib.util.AppUtil;
import netlib.util.EventUtils;

/**
 * Created by Albert on 2015/11/26.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:搜索无结果View
 */
public class SearchedNoneView extends LinearLayout {
    private Context mContext;

    private RelativeLayout mAdvancedSearch,mOptmise,mManual,mSwitchProvince;
    private TextView searchBtn;
    public SearchedNoneView(Context context) {
        super(context);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public SearchedNoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public SearchedNoneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SearchedNoneView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    private void initData(){

    }

    private void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.searched_none_view_layout, this, true);
        mSwitchProvince = (RelativeLayout)findViewById(R.id.switch_province);
        mAdvancedSearch = (RelativeLayout)findViewById(R.id.advanced_search);
        mOptmise = (RelativeLayout)findViewById(R.id.optmise_layout);
        mManual = (RelativeLayout)findViewById(R.id.manual_layout);
        searchBtn= (TextView) findViewById(R.id.searchBtn);
    }

    private void initWidgetAction(){
        mSwitchProvince.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               if (listener != null){
                   listener.OnScopeUnfold();
               }
            }
        });

        mAdvancedSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.SEARCH09);
                Intent intent = new Intent(mContext, AdvancedSearchActivity.class);
                mContext.startActivity(intent);
            }
        });

        mOptmise.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.SEARCH10);
                Intent intent = new Intent(mContext,WebActivity.class);
                intent.putExtra(WebActivity.URL,mContext.getString(R.string.req_url)
                        + mContext.getString(R.string.optmise_searchkey_url)
                        +"?appType=0&version="+ AppUtil.getVersionName(mContext));
                mContext.startActivity(intent);
            }
        });

        mManual.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.SEARCH11);
                Intent intent = new Intent(mContext, ManuallySearchActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    public void setSearchBtnVisibility(int visibility){
        searchBtn.setVisibility(visibility);
    }

    public void setSearchBtnListener(OnClickListener listener){
        if (listener!=null)
            searchBtn.setOnClickListener(listener);
    }

    private OnSwitchScopeListener listener;

    public void setListener(OnSwitchScopeListener listener) {
        this.listener = listener;
    }

    public interface OnSwitchScopeListener{
        public void OnScopeUnfold();
    }
}
