package com.jusfoun.jusfouninquire.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.AreaModel;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.service.event.SearchEvent;
import com.jusfoun.jusfouninquire.sharedpreference.FirstSearchPreference;
import com.jusfoun.jusfouninquire.ui.adapter.SearchScopeAdapter;
import com.jusfoun.jusfouninquire.ui.util.LibIOUtil;
import com.jusfoun.jusfouninquire.ui.view.ResultAppendView;
import com.jusfoun.jusfouninquire.ui.view.SearchScopeView;
import com.jusfoun.jusfouninquire.ui.view.SearchedNoneView;
import com.jusfoun.jusfouninquire.ui.view.XListView;
import com.jusfoun.library.animationadapter.adapter.AnimationAdapter;
import com.jusfoun.library.animationadapter.adapter.ScaleInAnimationAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Albert on 2015/11/24.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:搜索的base fragment，主要处理区域选择逻辑
 */
public class BaseSearchFragment extends BaseInquireFragment {

    protected SearchScopeView mScopeView;
    protected ListView mScopeListView;

    protected XListView mSearchResultList;

    protected SearchScopeAdapter mScopeAdapter;
    protected AnimationAdapter mScopeAnimAdapter;

    protected SearchedNoneView mSearchNoneView;

    protected ResultAppendView mAppendView;


    protected FirstSearchPreference firstSearchPreference;

    protected boolean mIsVisible;
    protected final int MAX_PAGE = 5;

    protected int mResumeType;
    protected final static int RESULT_LIST_TYPE = 1;
    protected final static int SEARCHED_NONE_TYPE = 2;



    @Override
    protected void initData() {
        mScopeAdapter = new SearchScopeAdapter(mContext);
        mScopeAnimAdapter = new ScaleInAnimationAdapter(mScopeAdapter);

        firstSearchPreference = new FirstSearchPreference(mContext);

        mAppendView = new ResultAppendView(mContext);



    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    protected void initWeightActions() {
        if (mScopeListView != null){
            mScopeAnimAdapter.setAbsListView(mScopeListView);
            mScopeListView.setAdapter(mScopeAnimAdapter);
            mScopeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mScopeListView.setVisibility(View.GONE);
                    mScopeAnimAdapter.reset();
                    if (view.getTag() instanceof SearchScopeAdapter.ViewHolder) {
                        SearchScopeAdapter.ViewHolder holder = (SearchScopeAdapter.ViewHolder) view.getTag();
                        if (holder == null) {
                            return;
                        }

                        AreaModel model = holder.getmData();
                        if (model != null) {
                            mScopeView.setSearchScope(model.getName());
                            SearchEvent event = new SearchEvent();
                            event.setScopename(model.getName());
                            event.setScope(model.getId());
                            EventBus.getDefault().post(event);
                        }
                    }
                }
            });
        }

      /*  if (mScopeView != null){
            mScopeView.setShowScopeListener(new SearchScopeView.OnShowScopeListListener() {
                @Override
                public void OnShowScopeList() {
                    ChangeTouchScrollEvent event = new ChangeTouchScrollEvent();
                    event.setmScrollable(mScopeListView.getVisibility() == View.VISIBLE);
                    EventBus.getDefault().post(event);
                }
            });
        }*/
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof SearchEvent){
            SearchEvent searchEvent = (SearchEvent) event;
            if (!TextUtils.isEmpty(searchEvent.getScopename())){
                mScopeAdapter.setSelected(searchEvent.getScopename());
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisible = isVisibleToUser;
    }

    @Override
    public void showLoading() {
        if (mIsVisible){
            super.showLoading();
        }

    }

    @Override
    protected void showToast(String msg) {
        if (mIsVisible){
            if (msg != null && !msg.equals("")) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        }

    }

}
