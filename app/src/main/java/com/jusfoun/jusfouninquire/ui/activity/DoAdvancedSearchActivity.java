package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BusinessItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchResultModel;
import com.jusfoun.jusfouninquire.net.route.SearchRequestRouter;
import com.jusfoun.jusfouninquire.ui.adapter.SearchIndustryAdapter;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.view.BackAndRightImageTitleView;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.ResultAppendView;
import com.jusfoun.jusfouninquire.ui.view.XListView;
import com.jusfoun.library.animationadapter.adapter.ScaleInAnimationAdapter;

import java.util.HashMap;

import netlib.util.EventUtils;
import com.jusfoun.jusfouninquire.TimeOut;

/**
 * Created by Albert on 2015/11/18.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:根据高级搜索的条件进行高级搜索页面
 */
public class DoAdvancedSearchActivity extends BaseInquireActivity implements XListView.IXListViewListener{
    public final static String CONDITION = "AdvancedSearchCondition";
    public final static String NAME = "entname";
    public final static String TSG = "tsg";
    public final static String LEGALNAME = "legalname";
    public final static String INDUSTRYID = "industryid";
    public final static String AREA = "area";
    public final static String STREETADDRESS = "streetaddress";
    public final static String OFFICEBUILDING = "officebuilding";
    public final static String REGISTEREDDCAPITAL = "registeredcapital";
    public final static String REGTIME = "regtime";
    public final static String PROVINCE = "province";

    private BackAndRightImageTitleView mTitleView;
    private XListView mList;
    private LinearLayout mSearchNoneView;
    private NetWorkErrorView mNetWorkErrorView;

    private ResultAppendView mAppendView;

    private SearchIndustryAdapter mAdapter;
    private ScaleInAnimationAdapter mAnimAdapter;

    private RelativeLayout mFrameLayout;
    private ImageView mFrameImage;
    private SceneAnimation mSceneAnimation;

    private String mName,mTSG,mLegalName,mIndustry,mArea,mStreetAddress,mOfficeBuilding,mRegisteredDcapital,mRegTime,mProvince;
    private int mPageIndex;

    public static final int MAX_PAGE = 5;


    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getBundleExtra(CONDITION);
            if (bundle != null){
                mName = bundle.getString(NAME,"");
                mTSG = bundle.getString(TSG,"");
                mLegalName = bundle.getString(LEGALNAME,"");
                mIndustry = bundle.getString(INDUSTRYID,"");
                mArea = bundle.getString(AREA,"");
                mStreetAddress = bundle.getString(STREETADDRESS,"");
                mOfficeBuilding = bundle.getString(OFFICEBUILDING,"");
                mRegisteredDcapital = bundle.getString(REGISTEREDDCAPITAL,"");
                mRegTime = bundle.getString(REGTIME,"");
                mProvince = bundle.getString(PROVINCE,"全国");
            }
        }
        mPageIndex = 1;
        mAdapter = new SearchIndustryAdapter(this);
        mAnimAdapter = new ScaleInAnimationAdapter(mAdapter);


        mAppendView = new ResultAppendView(mContext);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_do_advanced_search_layout);
        mTitleView = (BackAndRightImageTitleView)findViewById(R.id.titleview);
        mList = (XListView)findViewById(R.id.result_list);
        mList.setPullRefreshEnable(false);
        mList.setPullLoadEnable(false);
        mList.setXListViewListener(this);
        mAnimAdapter.setAbsListView(mList);
        mList.setAdapter(mAnimAdapter);

        mFrameLayout = (RelativeLayout) findViewById(R.id.image_frame_layout);
        mFrameImage = (ImageView) findViewById(R.id.image_frame);
        mSceneAnimation = new SceneAnimation(mFrameImage,75);

        mSearchNoneView = (LinearLayout) findViewById(R.id.searched_none);
        mNetWorkErrorView = (NetWorkErrorView) findViewById(R.id.net_work_error);
    }

    @Override
    protected void initWidgetActions() {
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchIndustryAdapter.ViewHolder holder = (SearchIndustryAdapter.ViewHolder) view.getTag();
                if (holder == null) {
                    return;
                }

                BusinessItemModel model = holder.getData();
                if (model != null) {

                    EventUtils.event(mContext, EventUtils.SEARCH15);

                    Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                    intent.putExtra(CompanyDetailActivity.COMPANY_NAME, model.getCompanyname());
                    intent.putExtra(CompanyDetailActivity.COMPANY_ID, model.getCompanyid());
                    startActivity(intent);
                }

            }
        });

        mNetWorkErrorView.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                mNetWorkErrorView.setVisibility(View.GONE);
                doAdvancedSearch(false, true);
            }
        });

        /*mAppendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebSearchCompanyActivity.class);
                intent.putExtra(WebSearchCompanyActivity.COMPANY_NAME, mName);
                intent.putExtra(WebSearchCompanyActivity.COMPANY_AREA, mProvince);
                mContext.startActivity(intent);
            }
        });*/

        doAdvancedSearch(false, true);
    }

    private void doAdvancedSearch(final boolean isLoadMore,boolean showdialog){
        if (showdialog){
            //showLoading();
            mList.setVisibility(View.GONE);
            mFrameLayout.setVisibility(View.VISIBLE);
            mSceneAnimation.start();
        }

        TimeOut timeOUt = new TimeOut(mContext);

        HashMap<String,String> map = new HashMap<String,String>();
        map.put("entname",mName);
        map.put("tsg",mTSG);
        map.put("legalname",mLegalName);
        map.put("area",mArea);
        map.put("streetaddress",mStreetAddress);
        map.put("officebuilding",mOfficeBuilding);
        map.put("industryid",mIndustry);
        map.put("registeredcapital",mRegisteredDcapital);
        map.put("regtime",mRegTime);
        map.put("pageSize", "10");
        map.put("province",mProvince);
        if (isLoadMore){
            map.put("pageIndex",mPageIndex + 1 + "");
        }else {
            map.put("pageIndex",mPageIndex + "");
        }
        map.put("t", timeOUt.getParamTimeMollis()+"");
        map.put("m", timeOUt.MD5time()+"");
        SearchRequestRouter.doAdvancedSearch(mContext, map,getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                mSceneAnimation.stop();
                mFrameLayout.setVisibility(View.GONE);
                if (data instanceof SearchResultModel){
                    SearchResultModel model = (SearchResultModel) data;
                    if (model.getResult() == 0){
                        mList.removeFooterView(mAppendView);

                        if (isLoadMore){
                            mList.stopLoadMore();
                            mPageIndex = mPageIndex + 1;
                            mList.setPullLoadEnable(mPageIndex < MAX_PAGE);
                        }else {
                            mList.stopRefresh();
                            mPageIndex = 1;
                        }

                        if (!TextUtils.isEmpty(model.getCount())){
                            int count = Integer.parseInt(model.getCount());
                            mList.setPullLoadEnable((count > 10 * mPageIndex) && (mPageIndex < MAX_PAGE));
                        }

                        if (model.getBusinesslist() != null){
                            if (model.getBusinesslist().size() > 0){
                                mList.setVisibility(View.VISIBLE);
                                mNetWorkErrorView.setVisibility(View.GONE);
                                mSearchNoneView.setVisibility(View.GONE);

                                if (isLoadMore){
                                    mAdapter.addData(model.getBusinesslist());
                                }else {
                                    mAdapter.refresh(model.getBusinesslist());
                                }

                            }else {
                                if (!isLoadMore){
                                    mAppendView.setmViewType(ResultAppendView.OPTMISE_MANUAL,mProvince,mName);
                                    mSearchNoneView.addView(mAppendView);
                                    mSearchNoneView.setVisibility(View.VISIBLE);
                                    mList.setVisibility(View.GONE);
                                    mNetWorkErrorView.setVisibility(View.GONE);
                                }

                            }
                        }
                        dealSearchResult(model);
                    }else {
                        if (!isLoadMore){
                            mNetWorkErrorView.setServerError();
                            mList.setVisibility(View.GONE);
                            mNetWorkErrorView.setVisibility(View.VISIBLE);
                            mSearchNoneView.setVisibility(View.GONE);
                        }else {
                            showToast(model.getMsg());
                        }

                    }
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                mList.stopLoadMore();
                mList.stopRefresh();
                mSceneAnimation.stop();
                mFrameLayout.setVisibility(View.GONE);
                if (!isLoadMore){
                    mNetWorkErrorView.setNetWorkError();
                    mList.setVisibility(View.GONE);
                    mNetWorkErrorView.setVisibility(View.VISIBLE);
                    mSearchNoneView.setVisibility(View.GONE);
                }
                showToast(error);
            }
        });
    }

    private void dealSearchResult(SearchResultModel model){
        mList.removeFooterView(mAppendView);
       // mList.setPullLoadEnable(mPageIndex < MAX_PAGE);
        if (!mList.isEnablePullLoad()){
            mAppendView.setmViewType(ResultAppendView.OPTMISE_MANUAL,mProvince,mName);
            mList.addFooterView(mAppendView);
        }
    }

    @Override
    public void onRefresh() {
        //doAdvancedSearch(false,false);
    }

    @Override
    public void onLoadMore() {
        doAdvancedSearch(true,false);
    }
}
