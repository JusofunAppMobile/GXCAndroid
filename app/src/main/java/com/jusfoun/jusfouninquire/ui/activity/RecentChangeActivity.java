package com.jusfoun.jusfouninquire.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.HomeRecentModel;
import com.jusfoun.jusfouninquire.net.model.RecentChangeItemModel;
import com.jusfoun.jusfouninquire.net.route.ReportRoute;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.adapter.RecentChangeAdapter;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.view.BackAndRightImageTitleView;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;

import com.jusfoun.jusfouninquire.TimeOut;

/**
 * @Description ${近期变更、企业雷达}
 */
public class RecentChangeActivity extends BaseInquireActivity implements XListView.IXListViewListener {
    private XListView mlistView;
    private TitleView titleView;
    private LinearLayout mLookany;
    private NetWorkErrorView netErrorLayout;

    private HomeRecentModel.RecentBean bean;


    private RelativeLayout mFrameLayout;
    private ImageView mFrameImage;
    private SceneAnimation mSceneAnimation;
    /**
     * 变量
     */
    private int pagenum = 1;
    private String userId = "";

    /**
     * 对象
     */
    private RecentChangeAdapter adapter;

    @Override
    protected void initData() {
        if ((LoginSharePreference.getUserInfo(mContext) != null) && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            userId = LoginSharePreference.getUserInfo(mContext).getUserid();
        }
        adapter = new RecentChangeAdapter(this);
        bean = new Gson().fromJson(getIntent().getStringExtra("bean"), HomeRecentModel.RecentBean.class);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_system_message);
        titleView = (TitleView) findViewById(R.id.titleView);
//        titleView.setTitleText(bean.title);
        mlistView = (XListView) findViewById(R.id.system_msg_xlistview);
        mLookany = (LinearLayout) findViewById(R.id.look_any);
        netErrorLayout = (NetWorkErrorView) findViewById(R.id.neterrorlayout);

        mFrameLayout = (RelativeLayout) findViewById(R.id.image_frame_layout);
        mFrameImage = (ImageView) findViewById(R.id.image_frame);
        mSceneAnimation = new SceneAnimation(mFrameImage, 75);
    }

    @Override
    protected void initWidgetActions() {
        titleView.setTitle(bean.title);
        mlistView.setAdapter(adapter);
        mlistView.setXListViewListener(this);
        mlistView.setPullRefreshEnable(true);
        mlistView.setPullLoadEnable(true);

        netErrorLayout.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                netErrorLayout.setVisibility(View.GONE);
                loadData();
            }
        });

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                i--;
                RecentChangeItemModel.DataResultBean bean = (RecentChangeItemModel.DataResultBean) adapter.getItem(i);
                if (bean != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(CompanyDetailActivity.COMPANY_ID, bean.id);
                    bundle.putString(CompanyDetailActivity.COMPANY_NAME, bean.ename);
                    goActivity(CompanyDetailActivity.class, bundle);
                }
            }
        });

        loadData();
    }

    private int pageIndex = 1;


    private void loadData() {
        if (pageIndex == 1 && adapter.isDataEmpty()) {
            mFrameLayout.setVisibility(View.VISIBLE);
            mSceneAnimation.start();
        }

        HashMap<String, String> map = new HashMap<>();
        final TimeOut timeOut = new TimeOut(this);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", "20");
        map.put("type", bean.type + "");
        map.put("t", timeOut.getParamTimeMollis() + "");
        map.put("m", timeOut.MD5time() + "");

        ReportRoute.recentChange(this, map, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                mFrameLayout.setVisibility(View.GONE);
                mSceneAnimation.stop();
                finishLoadMoreOrRefresh();
                RecentChangeItemModel model = (RecentChangeItemModel) data;
                if (model.totalCount == 0) {
                    mlistView.setVisibility(View.GONE);
                    netErrorLayout.setVisibility(View.GONE);
                    mLookany.setVisibility(View.VISIBLE);
                } else {
                    mlistView.setVisibility(View.VISIBLE);
                    netErrorLayout.setVisibility(View.GONE);
                    mLookany.setVisibility(View.GONE);
                    if (pageIndex == 1) {
                        adapter.refresh(model.dataResult);
                    } else {
                        adapter.addMore(model.dataResult);
                    }
                    pageIndex++;
                    if (model.totalCount == adapter.getCount()) {
                        mlistView.setPullLoadEnable(false);
                    } else {
                        mlistView.setPullLoadEnable(true);
                    }
                }
            }

            @Override
            public void onFail(String error) {
                mFrameLayout.setVisibility(View.GONE);
                mSceneAnimation.stop();
                finishLoadMoreOrRefresh();
                mlistView.setVisibility(View.GONE);
                netErrorLayout.setNetWorkError();
                netErrorLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void finishLoadMoreOrRefresh() {
        hideLoadDialog();

        mlistView.stopRefresh();
        mlistView.stopLoadMore();
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        loadData();
    }
}
