package com.jusfoun.jusfouninquire.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.HomeDataItemModel;
import com.jusfoun.jusfouninquire.net.model.HomeDataModel;
import com.jusfoun.jusfouninquire.net.model.HotCompanyListModel;
import com.jusfoun.jusfouninquire.net.route.GetHotCompanyInfo;
import com.jusfoun.jusfouninquire.service.event.HomeHotChangedEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.adapter.HotCompanyListAdapter;
import com.jusfoun.jusfouninquire.ui.adapter.NewAddListAdapter;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;

import netlib.util.EventUtils;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/17.
 * Description 热门企业列表页
 */
public class HotCompanyActivity extends BaseInquireActivity implements XListView.IXListViewListener {

    public static final String MODEL = "model";
    private XListView listView;
    private TitleView title;
    private HomeDataModel model;
    private HotCompanyListAdapter adapter;
    private static final int PAGESIZE = 20;
    private int pageIndex = 1;

    private LinearLayout loading;
    private ImageView loading_img;
    private SceneAnimation sceneAnimation;
    private NetWorkErrorView errorView;
    public static final String TYPE ="type";

    public static final int TYPE_HOT=1;
    public static final int TYPE_NEW =2;
    private int type;
    private NewAddListAdapter searchResultAdapter;


    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            model = (HomeDataModel) bundle.getSerializable(MODEL);
        type=getIntent().getIntExtra(TYPE,TYPE_HOT);
        adapter = new HotCompanyListAdapter(mContext);
        searchResultAdapter = new NewAddListAdapter(mContext);
//        mSwipeBackLayout.setEnableGesture(true);
    }

    @Override
    protected void initView() {

        setContentView(R.layout.activity_hot_company);
        listView = (XListView) findViewById(R.id.list_hot_company);
        title = (TitleView) findViewById(R.id.title_hot_company);
        loading= (LinearLayout) findViewById(R.id.loading);
        loading_img= (ImageView) findViewById(R.id.loading_img);
        sceneAnimation=new SceneAnimation(loading_img,75);
        errorView= (NetWorkErrorView) findViewById(R.id.net_work_error);
    }

    @Override
    protected void initWidgetActions() {
        if(type==TYPE_HOT){
            title.setTitle("热门企业");
            listView.setAdapter(adapter);
        }else if(type==TYPE_NEW){
            title.setTitle("新增企业");
            listView.setAdapter(searchResultAdapter);
            listView.setPadding(PhoneUtil.dip2px(mContext,10),PhoneUtil.dip2px(mContext,5),PhoneUtil.dip2px(mContext,10),0);
        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("tag", "position=" + position);
                EventUtils.event(mContext, EventUtils.HOTLIST20);
                if (view.getTag() != null) {
                    if(TYPE_HOT==type) {
                        HotCompanyListAdapter.ViewHolder viewHolder = (HotCompanyListAdapter.ViewHolder) view.getTag();
                        HomeDataItemModel homeDataItemModel = viewHolder.data;
                        Bundle bundle = new Bundle();
                        bundle.putString(CompanyDetailActivity.COMPANY_ID, homeDataItemModel.getCompanyid());
                        bundle.putString(CompanyDetailActivity.COMPANY_NAME, homeDataItemModel.getCompanyname());
                        goActivity(CompanyDetailActivity.class, bundle);
                    }else if(TYPE_NEW==type){
                        NewAddListAdapter.ViewHolder viewHolder = (NewAddListAdapter.ViewHolder) view.getTag();
                        HomeDataItemModel homeDataItemModel = viewHolder.data;
                        Bundle bundle = new Bundle();
                        bundle.putString(CompanyDetailActivity.COMPANY_ID, homeDataItemModel.getCompanyid());
                        bundle.putString(CompanyDetailActivity.COMPANY_NAME, homeDataItemModel.getCompanyname());
                        goActivity(CompanyDetailActivity.class, bundle);
                    }
                }
            }
        });
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        errorView.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                getHotCompanyInfo(true,true);
            }
        });
        getHotCompanyInfo(true,true);

    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof HomeHotChangedEvent) {
            HomeHotChangedEvent ev = (HomeHotChangedEvent) event;
            if (ev.getList() != null) {
                if(type==TYPE_HOT){
                    adapter.refersh(ev.getList());
                }else if(type==TYPE_NEW){
                    searchResultAdapter.refresh(ev.getList());
                }

            }

        }
    }

    /**
     * 获取热门企业列表
     * @param refresh 是否为刷新
     * @param isShow 是否加载动画
     */
    private void getHotCompanyInfo(final boolean refresh,boolean isShow) {

        if (isShow){
            sceneAnimation.start();
            loading.setVisibility(View.VISIBLE);
        }
        HashMap<String, String> params = new HashMap<>();
        if (InquireApplication.getUserInfo() != null
                && !TextUtils.isEmpty(InquireApplication.getUserInfo().getUserid()))
            params.put("userid", InquireApplication.getUserInfo().getUserid());
        params.put("pagesize", PAGESIZE + "");
        params.put("pageindex", pageIndex + "");
        params.put("type", type + "");


        GetHotCompanyInfo.getHotCompanyInfo(mContext, TAG, params, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                if (loading.getVisibility()==View.VISIBLE){
                    sceneAnimation.stop();
                    loading.setVisibility(View.GONE);
                }
                listView.stopRefresh();
                listView.stopLoadMore();
                if (data instanceof HotCompanyListModel) {
                    errorView.setVisibility(View.GONE);
                    HotCompanyListModel model = (HotCompanyListModel) data;
                    if (model.getResult() == 0) {
                        if (refresh) {
                            if (model.getBusinesslist() != null){
                                if(type==TYPE_HOT){
                                    adapter.refersh((model.getBusinesslist()));
                                }else if(type==TYPE_NEW){
                                    searchResultAdapter.refresh((model.getBusinesslist()));
                                }
                            }
                        } else {
                            if(type==TYPE_HOT){
                                adapter.addData((model.getBusinesslist()));
                            }else if(type==TYPE_NEW){
                                searchResultAdapter.addData((model.getBusinesslist()));
                            }
                        }
                        pageIndex++;

                        if(type==TYPE_HOT){
                            if (adapter.getCount() >= model.getCount()||model.getBusinesslist()!=null&&model.getBusinesslist().size()<PAGESIZE) {
                                listView.setPullLoadEnable(false);
                            } else {
                                listView.setPullLoadEnable(true);
                            }
                        }else if(type==TYPE_NEW){
                            if (searchResultAdapter.getCount() >= model.getCount()||model.getBusinesslist()!=null&&model.getBusinesslist().size()<PAGESIZE) {
                                listView.setPullLoadEnable(false);
                            } else {
                                listView.setPullLoadEnable(true);
                            }
                        }


                    } else {
                        Toast.makeText(mContext, model.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    errorView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(String error) {
                errorView.setVisibility(View.VISIBLE);
                listView.stopLoadMore();
                listView.stopRefresh();
                if (loading.getVisibility()==View.VISIBLE){
                    sceneAnimation.stop();
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        getHotCompanyInfo(true,false);
    }

    @Override
    public void onLoadMore() {
        getHotCompanyInfo(false,false);
    }
}
