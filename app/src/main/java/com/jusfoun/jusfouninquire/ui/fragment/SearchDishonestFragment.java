package com.jusfoun.jusfouninquire.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.database.DBUtil;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.DisHonestItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchDisHonestModel;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.net.route.SearchRequestRouter;
import com.jusfoun.jusfouninquire.service.event.ChangeTouchScrollEvent;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.adapter.SearchDishonestAdapter;
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
 * TODO :搜索失信fragment
 */
public class SearchDishonestFragment extends BaseSearchFragment implements XListView.IXListViewListener {

    private NetWorkErrorView mNetWorkError;

    private SearchDishonestAdapter mResultAdapter;
    private AnimationAdapter mAnmiAdapter;
    private String mSearchKey = "", mSearchScope = "", mSearchScopeID = "0";

    private boolean mNeedSearch;

    private RelativeLayout mFrameLayout;
    private ImageView mFrameImage,mImg_back;
    private SceneAnimation mSceneAnimation;

    private RelativeLayout mSearchNoneView;
    private int mPageIndex;
    private EditText searchEdit;

    @Override
    protected void initData() {
        super.initData();
        mPageIndex = 1;
        mResultAdapter = new SearchDishonestAdapter(mContext);
        mAnmiAdapter = new ScaleInAnimationAdapter(mResultAdapter);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_dishonest_fragment_layout, container, false);
        mSearchResultList = (XListView) view.findViewById(R.id.search_dishonest_list);
        mSearchResultList.setXListViewListener(this);
        mSearchResultList.setPullLoadEnable(false);
        mSearchResultList.setPullRefreshEnable(false);
        mAnmiAdapter.setAbsListView(mSearchResultList);
        mSearchResultList.setAdapter(mAnmiAdapter);

        mScopeView = (SearchScopeView) view.findViewById(R.id.dishonest_search_scope_view);
        mScopeListView = (ListView) view.findViewById(R.id.dishonest_scope_listview);
        mSearchScope = mScopeView.getSearchScope();

        mSearchNoneView = (RelativeLayout) view.findViewById(R.id.search_none_view);

        mNetWorkError = (NetWorkErrorView) view.findViewById(R.id.net_work_error);

        mFrameLayout = (RelativeLayout) view.findViewById(R.id.image_frame_layout);
        mFrameImage = (ImageView) view.findViewById(R.id.image_frame);
        mImg_back=(ImageView) view.findViewById(R.id.img_back);
        mSceneAnimation = new SceneAnimation(mFrameImage, 75);
        searchEdit = (EditText) view.findViewById(R.id.edit_search);

        return view;
    }

    @Override
    protected void initWeightActions() {
        super.initWeightActions();
        mImg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)mContext).finish();

            }
        });

        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_DOWN)
                    return true;
                if (keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_UP){
                    searchDishonest(false, true);
                }
                return false;
            }
        });
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 2) {
                    searchDishonest(false, true);
                }

            }
        });

        mSearchResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (view.getTag() instanceof SearchDishonestAdapter.ViewHolder) {
                    SearchDishonestAdapter.ViewHolder holder = (SearchDishonestAdapter.ViewHolder) view.getTag();
                    if (holder == null) {
                        return;
                    }

                    DisHonestItemModel model = holder.getData();
                    if (model != null) {

                        EventUtils.event(mContext,EventUtils.SEARCH28);

                        SearchHistoryItemModel savemodel = new SearchHistoryItemModel();
                        savemodel.setSearchkey(mSearchKey);
                        savemodel.setScopename(mSearchScope);
                        savemodel.setScope(mSearchScopeID);
                        savemodel.setTimestamp(System.currentTimeMillis());
                        // SearchHistoryPreference.saveItem(mContext, savemodel);
                        DBUtil.insertItem(mContext, savemodel);

                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra(WebActivity.URL, model.getUrl());
                        intent.putExtra(WebActivity.TITLE, "失信详情");
                        mContext.startActivity(intent);
                    }
                }


            }
        });

        mSearchNoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.SEARCH10);
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra(WebActivity.URL, mContext.getString(R.string.req_url)
                        + mContext.getString(R.string.optmise_searchkey_url) + "?appType=0&version=" + AppUtil.getVersionName(mContext));
                intent.putExtra(WebActivity.TITLE, "优化搜索词");
                mContext.startActivity(intent);
            }
        });

        mNetWorkError.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                mNetWorkError.setVisibility(View.GONE);
                searchDishonest(mPageIndex > 1, true);
            }
        });

        mScopeView.setShowScopeListener(new SearchScopeView.OnShowScopeListListener() {
            @Override
            public void OnShowScopeList() {
                if (!mSceneAnimation.getIsStop()) {
                    return;
                }
                ChangeTouchScrollEvent event = new ChangeTouchScrollEvent();
                event.setmScrollable(!(mScopeListView.getVisibility() == View.VISIBLE));
                EventBus.getDefault().post(event);
                KeyBoardUtil.hideSoftKeyboard((Activity) mContext);
                if (mScopeListView.getVisibility() == View.VISIBLE) {
                    mScopeAnimAdapter.reset();
                    mScopeListView.setVisibility(View.GONE);
                    if (mResumeType == RESULT_LIST_TYPE) {
                        mSearchResultList.setVisibility(View.VISIBLE);
                        mSearchNoneView.setVisibility(View.GONE);
                    } else if (mResumeType == SEARCHED_NONE_TYPE) {
                        mSearchResultList.setVisibility(View.GONE);
                        mSearchNoneView.setVisibility(View.GONE);
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
                    mSearchNoneView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mNeedSearch) {
            mScopeView.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mSearchKey)) {
                searchDishonest(false, true);
            }
        }
        if ((mScopeListView != null) && (!isVisibleToUser)) {
            mScopeListView.setVisibility(View.GONE);
            if ((mResumeType == RESULT_LIST_TYPE) && (mSearchResultList != null)) {
                mSearchResultList.setVisibility(View.VISIBLE);
            } else if ((mResumeType == SEARCHED_NONE_TYPE) && (mSearchNoneView != null)) {
                mSearchNoneView.setVisibility(View.GONE);
            }
        }
    }

    private void searchDishonest(final boolean isLoadMore, boolean showdialog) {
        if (showdialog) {
            //showLoading();
            mSearchNoneView.setVisibility(View.GONE);
            mSearchResultList.setVisibility(View.GONE);
            mNetWorkError.setVisibility(View.GONE);
            mSceneAnimation.stop();
            mFrameLayout.setVisibility(View.VISIBLE);
            mSceneAnimation.start();
        }

        TimeOut timeOut = new TimeOut(mContext);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("searchkey", searchEdit.getText().toString());
//        if (!"全国".equals(mSearchScope)) {
//            map.put("scope", mSearchScope);
//        }
//        map.put("scope", "全国");
        map.put("pagesize", "10");
        if (isLoadMore) {
            map.put("pagenumber", mPageIndex + 1 + "");
        } else {
            map.put("pagenumber", mPageIndex + "");
        }
        map.put("t", timeOut.getParamTimeMollis() + "");
        map.put("m", timeOut.MD5time() + "");
        SearchRequestRouter.SearchDishonest(mContext, map, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                mFrameLayout.setVisibility(View.GONE);
                mSceneAnimation.stop();
                if (data instanceof SearchDisHonestModel) {
                    SearchDisHonestModel model = (SearchDisHonestModel) data;
                    if (model.getResult() == 0) {
                        mNeedSearch = false;
                        mSearchResultList.removeFooterView(mAppendView);
                        mNetWorkError.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(model.getIsmore())) {
                            mSearchResultList.setPullLoadEnable("true".equals(model.getIsmore()));
                        }

                        if (isLoadMore) {
                            mSearchResultList.stopLoadMore();
                            mPageIndex = mPageIndex + 1;
                        } else {
                            mSearchResultList.stopRefresh();
                            mPageIndex = 1;
                        }

                        if (model.getDishonestylist() != null) {
                            if (model.getDishonestylist().size() > 0) {
                                mResumeType = RESULT_LIST_TYPE;
                                mSearchResultList.setVisibility(View.VISIBLE);
                                mSearchNoneView.setVisibility(View.GONE);
                                mSearchResultList.setPullRefreshEnable(false);
                                if (isLoadMore) {
                                    mResultAdapter.addData(model.getDishonestylist());
                                } else {
                                    mResultAdapter.refresh(model.getDishonestylist());
                                }
                            } else {
                                if (!isLoadMore) {
                                    //第一页无结果
                                    mResumeType = SEARCHED_NONE_TYPE;
                                    mSearchResultList.setVisibility(View.GONE);
                                    mScopeListView.setVisibility(View.GONE);
                                    mSearchNoneView.setVisibility(View.GONE);
                                    Toast.makeText(mContext, "没有相关数据", Toast.LENGTH_SHORT).show();
                                    mResultAdapter.cleanAllData();
                                }
                            }
                        }

                        dealSearchResult(model);
                    } else {
                        //只有第一页显示加载失败显示网络异常
                        if (!isLoadMore) {
                            mNetWorkError.setServerError();
                            mNetWorkError.setVisibility(View.VISIBLE);
                        } else {
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
                mSceneAnimation.stop();
                mFrameLayout.setVisibility(View.GONE);
                if (!isLoadMore) {
                    mNetWorkError.setNetWorkError();
                    mNetWorkError.setVisibility(View.VISIBLE);
                }

                showToast(error);
            }
        });
    }

    private void dealSearchResult(SearchDisHonestModel model) {
        if (isDetached()) {
            return;
        }

        if (!isAdded()) {
            return;
        }
        if (!mSearchResultList.isEnablePullLoad()) {
            mAppendView.setmViewType(ResultAppendView.OPTMISE, "", "");
            mSearchResultList.addFooterView(mAppendView);
        }

        if ("全国".equals(mSearchScope)) {
            mScopeView.setSelectProvinceTip("");
        } else {
            mScopeView.setSelectProvinceTip(mContext.getString(R.string.switch_province));
        }
    }

    @Override
    public void onRefresh() {
        //searchDishonest(false,false);
    }

    @Override
    public void onLoadMore() {
        searchDishonest(true, false);
    }


}
