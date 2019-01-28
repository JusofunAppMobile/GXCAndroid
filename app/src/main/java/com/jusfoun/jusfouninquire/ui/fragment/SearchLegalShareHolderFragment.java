package com.jusfoun.jusfouninquire.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;
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
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.adapter.SearchIndustryAdapter;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.util.KeyBoardUtil;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.ResultAppendView;
import com.jusfoun.jusfouninquire.ui.view.SearchScopeView;
import com.jusfoun.jusfouninquire.ui.view.XListView;
import com.jusfoun.library.animationadapter.adapter.AnimationAdapter;
import com.jusfoun.library.animationadapter.adapter.ScaleInAnimationAdapter;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import netlib.util.AppUtil;
import netlib.util.EventUtils;
import com.jusfoun.jusfouninquire.TimeOut;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :搜索法人股东fragment
 */
public class SearchLegalShareHolderFragment extends BaseSearchFragment implements XListView.IXListViewListener{
    private NetWorkErrorView mNetWorkError;

    private SearchIndustryAdapter mResultAdapter;
    private AnimationAdapter mAnimAdapter;
    private String mSearchKey,mSearchScope,mSearchScopeName;

    private RelativeLayout mSearchNoneView;

    private int mPageIndex;

    private RelativeLayout mFrameLayout;
    private ImageView mFrameImage;
    private SceneAnimation mSceneAnimation;

    private boolean mNeedSearch;

    @Override
    protected void initData() {
        super.initData();
        mPageIndex = 1;
        mSearchScope = "";
        mSearchScopeName = "全国";
        mResultAdapter = new SearchIndustryAdapter(mContext);
        mAnimAdapter = new ScaleInAnimationAdapter(mResultAdapter);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_legal_shareholder_fragment_layout, container, false);
        mSearchResultList = (XListView)view.findViewById(R.id.search_legal_shareholder_list);
        mSearchResultList.setXListViewListener(this);
        mSearchResultList.setPullLoadEnable(false);
        mSearchResultList.setPullRefreshEnable(false);
        mAnimAdapter.setAbsListView(mSearchResultList);
        mSearchResultList.setAdapter(mAnimAdapter);

        mScopeView = (SearchScopeView)view.findViewById(R.id.legal_search_scope_view);
        mScopeListView = (ListView)view.findViewById(R.id.legal_scope_listview);
        mSearchScopeName = mScopeView.getSearchScope();

        mSearchNoneView = (RelativeLayout) view.findViewById(R.id.search_none_view);

        mNetWorkError = (NetWorkErrorView) view.findViewById(R.id.net_work_error);

        mFrameLayout = (RelativeLayout) view.findViewById(R.id.image_frame_layout);
        mFrameImage = (ImageView) view.findViewById(R.id.image_frame);
        mSceneAnimation = new SceneAnimation(mFrameImage,75);
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

                        EventUtils.event(mContext,EventUtils.SEARCH13);

                        SearchHistoryItemModel savemodel = new SearchHistoryItemModel();
                        savemodel.setSearchkey(mSearchKey);
                        savemodel.setScope(mSearchScope);
                        savemodel.setScopename(mSearchScopeName);
                        savemodel.setTimestamp(System.currentTimeMillis());
                        //SearchHistoryPreference.saveItem(mContext, savemodel);
                        DBUtil.insertItem(mContext,savemodel);

                        Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                        intent.putExtra(CompanyDetailActivity.COMPANY_NAME, model.getCompanyname());
                        intent.putExtra(CompanyDetailActivity.COMPANY_ID, model.getCompanyid());
                        startActivity(intent);
                    }
                }
            }
        });

        mSearchNoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.SEARCH10);
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra(WebActivity.URL,mContext.getString(R.string.req_url) +
                        mContext.getString(R.string.optmise_searchkey_url)+"?appType=0&version="+ AppUtil.getVersionName(mContext));
                intent.putExtra(WebActivity.TITLE,"优化搜索词");
                mContext.startActivity(intent);
            }
        });

        mNetWorkError.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                mNetWorkError.setVisibility(View.GONE);
                doRequestLegalShareholder(mPageIndex > 1, true);
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
                        mSearchNoneView.setVisibility(View.GONE);
                    } else if (mResumeType == SEARCHED_NONE_TYPE) {
                        mSearchResultList.setVisibility(View.GONE);
                        mSearchNoneView.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (mSearchResultList.getVisibility() == View.VISIBLE){
                        EventUtils.event(mContext,EventUtils.SEARCH04);
                    }else {
                        EventUtils.event(mContext,EventUtils.SEARCH08);
                    }
                    mScopeListView.setVisibility(View.VISIBLE);
                    mScopeAdapter.refresh(InquireApplication.getLocationList());
                    mSearchResultList.setVisibility(View.GONE);
                    mSearchNoneView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mNeedSearch){
            mScopeView.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mSearchKey)){
                doRequestLegalShareholder(false,true);
            }
        }

        if ((mScopeListView != null) && (!isVisibleToUser)){
            mScopeListView.setVisibility(View.GONE);
            if ((mResumeType == RESULT_LIST_TYPE) && (mSearchResultList != null)){
                mSearchResultList.setVisibility(View.VISIBLE);
            }else if ((mResumeType == SEARCHED_NONE_TYPE) && (mSearchNoneView != null)){
                mSearchNoneView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRefresh() {
        //doRequestLegalShareholder(false, false);
    }

    @Override
    public void onLoadMore() {
        doRequestLegalShareholder(true, false);
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
                    doRequestLegalShareholder(false,true);
                }

            }else {
                if(mResultAdapter.getCount() > 0){
                    mSearchResultList.setVisibility(View.VISIBLE);
                }else {
                    mSearchResultList.setVisibility(View.GONE);
                    mSearchNoneView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void doRequestLegalShareholder(final boolean isLoadMore,boolean showdialog){
        TimeOut timeout = new TimeOut(mContext);
        if (showdialog){
            //showLoading();
            mSearchNoneView.setVisibility(View.GONE);
            mSearchResultList.setVisibility(View.GONE);
            mNetWorkError.setVisibility(View.GONE);
            mFrameLayout.setVisibility(View.VISIBLE);
            mSceneAnimation.start();
        }

        HashMap<String,String> map = new HashMap<String,String>();
        map.put("type", "2");
        map.put("searchkey",mSearchKey);
        map.put("area",mSearchScope);
        map.put("province",mSearchScopeName);
        map.put("pageSize", "10");
        if (isLoadMore) {
            map.put("pageIndex",mPageIndex + 1 + "");
        }else {
            map.put("pageIndex", mPageIndex + "");
        }
        map.put("t", timeout.getParamTimeMollis()+"");
        map.put("m", timeout.MD5time()+"");
        SearchRequestRouter.SearchIndustry(mContext, map,((Activity)mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                mFrameLayout.setVisibility(View.GONE);
                mSceneAnimation.stop();
                if (data instanceof  SearchResultModel){
                    SearchResultModel model = (SearchResultModel) data;
                    if (model.getResult() == 0) {
                        mNeedSearch = false;
                        mSearchResultList.removeFooterView(mAppendView);
                        mNetWorkError.setVisibility(View.GONE);

                        if (isLoadMore){
                            mSearchResultList.stopLoadMore();
                            mPageIndex = mPageIndex + 1;
                        }else {
                            mSearchResultList.stopRefresh();
                            mPageIndex = 1;
                        }

                        if (!TextUtils.isEmpty(model.getCount())){
                            int count = Integer.parseInt(model.getCount());
                            mSearchResultList.setPullLoadEnable((count > 10 * mPageIndex) && (mPageIndex < MAX_PAGE));
                        }
                        if (model.getBusinesslist() != null){
                            if (model.getBusinesslist().size() > 0){
                                mResumeType = RESULT_LIST_TYPE;
                                mSearchResultList.setPullRefreshEnable(false);
                                mSearchResultList.setVisibility(View.VISIBLE);
                                mSearchNoneView.setVisibility(View.GONE);
                                if (isLoadMore){
                                    mResultAdapter.addData(model.getBusinesslist());
                                }else {
                                    mResultAdapter.refresh(model.getBusinesslist());
                                }

                            }else {
                                if (!isLoadMore){
                                    mResumeType = SEARCHED_NONE_TYPE;
                                    mSearchResultList.setVisibility(View.GONE);
                                    mSearchNoneView.setVisibility(View.VISIBLE);
                                    mResultAdapter.clearAllData();
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
                        if (!isLoadMore){
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
                mSearchResultList.stopLoadMore();
                mSearchResultList.stopRefresh();
                mFrameLayout.setVisibility(View.GONE);
                mSceneAnimation.stop();
                if (!isLoadMore){
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
        }
        if (count == 0){
            if ("0".equals(mSearchScope)){
                mScopeView.setSelectProvinceTip("");
            }else {
                mScopeView.setSelectProvinceTip(mContext.getResources().getString(R.string.switch_province));
            }
        }else if ((count > 0) && (count < 50)){
            if (!mSearchResultList.isEnablePullLoad()){
                mAppendView.setmViewType(ResultAppendView.OPTMISE,"","");
                mSearchResultList.addFooterView(mAppendView);
            }
            if ("0".equals(mSearchScope)){
                mScopeView.setSelectProvinceTip("");
            }else {
                mScopeView.setSelectProvinceTip(mContext.getResources().getString(R.string.switch_province));

            }

        }else if (count > 50){
            if (mResultAdapter.getCount() == 50){
                mAppendView.setmViewType(ResultAppendView.OPTMISE_ADVANCED,"","");
                mSearchResultList.addFooterView(mAppendView);
            }
            mScopeView.setSelectProvinceTip(mContext.getString(R.string.searched_too_many));
        }
    }


}
