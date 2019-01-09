package com.jusfoun.jusfouninquire.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.database.DBUtil;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BusinessItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchResultModel;
import com.jusfoun.jusfouninquire.net.route.SearchRequestRouter;
import com.jusfoun.jusfouninquire.service.event.ChangeTouchScrollEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.service.event.SearchEvent;
import com.jusfoun.jusfouninquire.service.event.SearchFinishEvent;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebSearchCompanyActivity;
import com.jusfoun.jusfouninquire.ui.adapter.SearchIndustryAdapter;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.util.KeyBoardUtil;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.ResultAppendView;
import com.jusfoun.jusfouninquire.ui.view.SearchScopeView;
import com.jusfoun.jusfouninquire.ui.view.SearchedNoneView;
import com.jusfoun.jusfouninquire.ui.view.XListView;
import com.jusfoun.library.animationadapter.adapter.AnimationAdapter;
import com.jusfoun.library.animationadapter.adapter.ScaleInAnimationAdapter;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import netlib.util.EventUtils;
import com.jusfoun.jusfouninquire.TimeOut;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :搜索企业fragment
 */
public class SearchIndustryFragment extends BaseSearchFragment implements XListView.IXListViewListener{

    private NetWorkErrorView mNetWorkError;

    private SearchIndustryAdapter mSearchAdapter;
    private AnimationAdapter mAnimAdapter;

    private String mSearchKey = "";
    private String mSearchScope = "0";
    private String mSearchScopeName = "全国";

    private boolean mNeedSearch;

    private RelativeLayout mFrameLayout;
    private ImageView mFrameImage;
    private ScrollView mSearchNoneViewScroll;
    private SceneAnimation mSceneAnimation;

    private int mPageIndex;
    @Override
    protected void initData() {
        super.initData();
        mSearchAdapter = new SearchIndustryAdapter(mContext);
        mAnimAdapter = new ScaleInAnimationAdapter(mSearchAdapter);
        mPageIndex = 1;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_industry_fragment_layout, container, false);
        mSearchResultList = (XListView)view.findViewById(R.id.search_industry_list);
        mSearchResultList.setXListViewListener(this);
        mSearchResultList.setPullLoadEnable(false);
        mSearchResultList.setPullRefreshEnable(false);
        mAnimAdapter.setAbsListView(mSearchResultList);
        mSearchResultList.setAdapter(mAnimAdapter);

        mScopeView = (SearchScopeView)view.findViewById(R.id.industry_search_scope_view);
        mScopeListView = (ListView)view.findViewById(R.id.industry_scope_listview);
        mSearchScopeName = mScopeView.getSearchScope();

        mSearchNoneViewScroll = (ScrollView) view.findViewById(R.id.search_none_view);
        mSearchNoneView = (SearchedNoneView) view.findViewById(R.id.real_search_none_view);

        mFrameLayout = (RelativeLayout) view.findViewById(R.id.image_frame_layout);
        mFrameImage = (ImageView) view.findViewById(R.id.image_frame);
        mSceneAnimation = new SceneAnimation(mFrameImage,75);


        mNetWorkError = (NetWorkErrorView) view.findViewById(R.id.net_work_error);
        return view;
    }

    @Override
    protected void initWeightActions() {
        super.initWeightActions();
        mSearchResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getTag() instanceof SearchIndustryAdapter.ViewHolder) {
                    SearchIndustryAdapter.ViewHolder holder = (SearchIndustryAdapter.ViewHolder) view.getTag();
                    if (holder == null) {
                        return;
                    }

                    BusinessItemModel model = holder.getData();
                    if (model != null) {

                        EventUtils.event(mContext,EventUtils.SEARCH12);

                        SearchHistoryItemModel savemodel = new SearchHistoryItemModel();
                        savemodel.setSearchkey(mSearchKey);
                        savemodel.setScope(mSearchScope);
                        savemodel.setScopename(mSearchScopeName);
                        savemodel.setTimestamp(System.currentTimeMillis());
                        //SearchHistoryPreference.saveItem(mContext, savemodel);
                        DBUtil.insertItem(mContext, savemodel);
                        Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                        intent.putExtra(CompanyDetailActivity.COMPANY_ID, model.getCompanyid());
                        intent.putExtra(CompanyDetailActivity.COMPANY_NAME, model.getCompanyname());
                        startActivity(intent);
                    }

                }

            }
        });

        mSearchNoneView.setListener(new SearchedNoneView.OnSwitchScopeListener() {
            @Override
            public void OnScopeUnfold() {
                mSearchNoneViewScroll.setVisibility(View.GONE);
                mScopeListView.setVisibility(View.VISIBLE);
                mScopeAdapter.refresh(InquireApplication.getLocationList());
            }
        });

        mNetWorkError.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                mNetWorkError.setVisibility(View.GONE);
                doRequestIndustry(mPageIndex > 1, true);
            }
        });

        mScopeView.setShowScopeListener(new SearchScopeView.OnShowScopeListListener() {
            @Override
            public void OnShowScopeList() {
                if(!mSceneAnimation.getIsStop()) {
                    return;
                }
                ChangeTouchScrollEvent event = new ChangeTouchScrollEvent();
                event.setmScrollable(!(mScopeListView.getVisibility() == View.VISIBLE));
                EventBus.getDefault().post(event);
                KeyBoardUtil.hideSoftKeyboard((Activity)mContext);
                if (mScopeListView.getVisibility() == View.VISIBLE) {
                    mScopeAnimAdapter.reset();
                    mScopeListView.setVisibility(View.GONE);
                    if (mResumeType == RESULT_LIST_TYPE) {
                        mSearchResultList.setVisibility(View.VISIBLE);
                        mSearchNoneViewScroll.setVisibility(View.GONE);
                        mSearchNoneView.setSearchBtnVisibility(View.GONE);
                    } else if (mResumeType == SEARCHED_NONE_TYPE) {
                        mSearchResultList.setVisibility(View.GONE);
                        mSearchNoneViewScroll.setVisibility(View.VISIBLE);
                        mSearchNoneView.setSearchBtnVisibility(View.VISIBLE);
                    }
                } else {
                    if (mSearchResultList.getVisibility() == View.VISIBLE) {
                        EventUtils.event(mContext,EventUtils.SEARCH04);
                    } else {
                        EventUtils.event(mContext,EventUtils.SEARCH08);
                    }
                    mScopeListView.setVisibility(View.VISIBLE);
                    mScopeAdapter.refresh(InquireApplication.getLocationList());
                    mSearchResultList.setVisibility(View.GONE);
                    mSearchNoneViewScroll.setVisibility(View.GONE);
                    mSearchNoneView.setSearchBtnVisibility(View.GONE);
                }
            }
        });

        mSearchNoneView.setSearchBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebSearchCompanyActivity.class);
                intent.putExtra(WebSearchCompanyActivity.COMPANY_NAME, mSearchKey);
                intent.putExtra(WebSearchCompanyActivity.COMPANY_AREA, mSearchScopeName);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mNeedSearch){
            mScopeView.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mSearchKey)){
                doRequestIndustry(false,true);
            }
        }

        if ((mScopeListView != null) && (!isVisibleToUser)){
            mScopeListView.setVisibility(View.GONE);
            if ((mResumeType == RESULT_LIST_TYPE) && (mSearchResultList != null)){
                mSearchResultList.setVisibility(View.VISIBLE);
            }else if ((mResumeType == SEARCHED_NONE_TYPE) && (mSearchNoneView != null)){
                mSearchNoneViewScroll.setVisibility(View.VISIBLE);
                mSearchNoneView.setSearchBtnVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof SearchEvent){
            mScopeListView.setVisibility(View.GONE);
            SearchEvent ev = (SearchEvent)event;
            if (!TextUtils.isEmpty(ev.getScopename())){
                mSearchScopeName = ev.getScopename();
                mScopeView.setSearchScope(mSearchScopeName);
            }

            if (((!TextUtils.isEmpty(ev.getSearchkey())) && (!ev.getSearchkey().equals(mSearchKey))) ||
                    ((!TextUtils.isEmpty(ev.getScope())) && (!ev.getScope().equals(mSearchScope)))){
                mNeedSearch = true;
                if (!TextUtils.isEmpty(ev.getSearchkey())){
                    mSearchKey = ev.getSearchkey();
                }
                if (!TextUtils.isEmpty(ev.getScope())){
                    mSearchScope = ev.getScope();
                }

                if (getUserVisibleHint() && !TextUtils.isEmpty(mSearchKey)){
                    doRequestIndustry(false,true);
                }

            }else {
                if(mSearchAdapter.getCount() > 0){
                    mSearchResultList.setVisibility(View.VISIBLE);
                }else {
                    mSearchResultList.setVisibility(View.GONE);
                    mSearchNoneViewScroll.setVisibility(View.VISIBLE);
                    mSearchNoneView.setSearchBtnVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void doRequestIndustry(final boolean isAdd,boolean showdialog){
        if (showdialog){
           // showLoading();
            mSearchNoneViewScroll.setVisibility(View.GONE);
            mSearchNoneView.setSearchBtnVisibility(View.GONE);
            mSearchResultList.setVisibility(View.GONE);
            mNetWorkError.setVisibility(View.GONE);
            mFrameLayout.setVisibility(View.VISIBLE);
            mSceneAnimation.start();
        }
        TimeOut timeOut = new TimeOut(mContext);

        HashMap<String,String> map = new HashMap<String,String>();
        map.put("type","1");
        map.put("searchkey",mSearchKey);
        map.put("pageSize","10");
        map.put("area",mSearchScope);
        map.put("province",mSearchScopeName);
        if (isAdd){
            map.put("pageIndex",mPageIndex + 1 + "");
        }else {
            map.put("pageIndex",mPageIndex + "");
        }
        map.put("t", timeOut.getParamTimeMollis()+"");
        map.put("m", timeOut.MD5time()+"");
        SearchRequestRouter.SearchIndustry(mContext, map,((Activity)mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                mSceneAnimation.stop();
                mFrameLayout.setVisibility(View.GONE);
                if (data instanceof  SearchResultModel){
                    SearchResultModel model = (SearchResultModel) data;
                    if (model.getResult() == 0) {
                        mNeedSearch = false;
                        mSearchResultList.removeFooterView(mAppendView);
                        mNetWorkError.setVisibility(View.GONE);
                        if (isAdd){
                            mPageIndex = mPageIndex + 1;
                            mSearchResultList.stopLoadMore();

                        }else {
                            mPageIndex = 1;
                            mSearchResultList.stopRefresh();
                        }

                        if (!TextUtils.isEmpty(model.getCount())){
                            int count = Integer.parseInt(model.getCount());
                            mSearchResultList.setPullLoadEnable((count > 10 * mPageIndex) && (mPageIndex < MAX_PAGE));
                        }

                        if (model.getBusinesslist() != null){
                            if (model.getBusinesslist().size() > 0){
                                mResumeType = RESULT_LIST_TYPE;
                                mSearchResultList.setVisibility(View.VISIBLE);
                                mSearchResultList.setPullRefreshEnable(false);
                                mSearchNoneViewScroll.setVisibility(View.GONE);
                                mSearchNoneView.setSearchBtnVisibility(View.GONE);
                                if (isAdd){
                                    mSearchAdapter.addData(model.getBusinesslist());
                                }else {
                                    mSearchAdapter.refresh(model.getBusinesslist());
                                }
                            }else {
                                if (!isAdd){
                                    mResumeType = SEARCHED_NONE_TYPE;
                                    mSearchResultList.setVisibility(View.GONE);
                                    mSearchNoneViewScroll.setVisibility(View.VISIBLE);
                                    mSearchNoneView.setSearchBtnVisibility(View.VISIBLE);
                                    mSearchAdapter.clearAllData();
                                }
                            }
                        }

                        if (getUserVisibleHint()){
                            SearchFinishEvent event = new SearchFinishEvent();
                            event.setSearchkey(mSearchKey);
                            if (!TextUtils.isEmpty(model.getCount())){
                                int count = Integer.parseInt(model.getCount());
                                event.setResultcount(count);
                            }
                            EventBus.getDefault().post(event);
                        }
                        dealSearchResult(model);
                    }else {
                        if (!isAdd){
                            mNetWorkError.setServerError();
                            mNetWorkError.setVisibility(View.VISIBLE);
                        }else {
                            showToast(model.getMsg());
                        }

                    }

                }

            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                Log.e("tag ", "SearchIndustryFragment -- isAdd = " + isAdd);
                mSearchResultList.stopLoadMore();
                mSearchResultList.stopRefresh();
                mSceneAnimation.stop();
                mFrameLayout.setVisibility(View.GONE);
                if (!isAdd){
                    mNetWorkError.setNetWorkError();
                    mNetWorkError.setVisibility(View.VISIBLE);
                }

                showToast(error);
            }
        });
    }

    private void dealSearchResult(SearchResultModel model){
        if(isDetached()){
            return;
        }

        if (!isAdded()){
            return;
        }
        int count = -1;
        if(!TextUtils.isEmpty(model.getCount())){
            count  = Integer.parseInt(model.getCount());
            Log.d("TAG","count====="+count);
        }
        if (count == 0){
            if ("0".equals(mSearchScope)){
                mScopeView.setSelectProvinceTip(mContext.getResources().getString(R.string.select_province));
            }else {
                mScopeView.setSelectProvinceTip(mContext.getResources().getString(R.string.switch_province));
            }
        }else if ((count > 0) && (count <= 50)){
            if ("0".equals(mSearchScope)){
                mScopeView.setSelectProvinceTip(mContext.getResources().getString(R.string.select_province));
            }else {
                mScopeView.setSelectProvinceTip(getResources().getString(R.string.switch_province));
            }

            if (!mSearchResultList.isEnablePullLoad()){
                mAppendView.setmViewType(ResultAppendView.MANUAL,mSearchScopeName,mSearchKey);
                mSearchResultList.addFooterView(mAppendView);
            }

        }else if (count > 50){
            if (mSearchAdapter.getCount() == 50){
                mAppendView.setmViewType(ResultAppendView.MANUAL_ADVANCED,mSearchScopeName,mSearchKey);
                mSearchResultList.addFooterView(mAppendView);
            }
            mScopeView.setSelectProvinceTip(mContext.getString(R.string.searched_too_many));
        }
    }

    @Override
    public void onRefresh() {
        //doRequestIndustry(false,false);
    }

    @Override
    public void onLoadMore() {
        doRequestIndustry(true,false);
    }


}
