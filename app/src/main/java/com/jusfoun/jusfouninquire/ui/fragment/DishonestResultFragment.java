package com.jusfoun.jusfouninquire.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.DisHonestItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchDisHonestModel;
import com.jusfoun.jusfouninquire.net.route.SearchRequestRouter;
import com.jusfoun.jusfouninquire.service.event.DishonestResultEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.adapter.SearchDishonestAdapter;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;
import java.util.Map;

import netlib.util.EventUtils;

/**
 * Author  wangchenchen
 * CreateDate 2016/8/31.
 * Email wcc@jusfoun.com
 * Description
 */
public class DishonestResultFragment extends BaseInquireFragment {
    public static final String SEARCHKEY="searchkey";
    public static final String TYPE="type";
    public static final String MODEL="model";
    private XListView listView;
    private SceneAnimation mSceneAnimation;
    private ImageView mFrameImage;
    private RelativeLayout mFrameLayout;
    private SearchDishonestAdapter mResultAdapter;

    private boolean needResult=false;
    private HashMap<String,String> params=new HashMap<>();
    private int type;
    private int pageindex=1;
    private String searchKey="";
    private boolean isFirst=true;
    private SearchDisHonestModel model;

    private View footview;
    private boolean isAddFoot=false;

    public static DishonestResultFragment getInstance(Bundle argument){
        DishonestResultFragment fragment=new DishonestResultFragment();
        fragment.setArguments(argument);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&needResult){
            needResult=false;
            refresh(true);
        }
    }

    @Override
    protected void initData() {
        mResultAdapter=new SearchDishonestAdapter(mContext);
        if (getArguments()!=null){
            type=getArguments().getInt(TYPE);
            searchKey=getArguments().getString(SEARCHKEY);
            model= (SearchDisHonestModel) getArguments().getSerializable(MODEL);
        }
        footview = LayoutInflater.from(mContext).inflate(R.layout.item_search_dishonest_foot,null);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_dishonest_result,container,false);
        listView= (XListView) view.findViewById(R.id.list);
        mFrameLayout = (RelativeLayout) view.findViewById(R.id.image_frame_layout);
        mFrameImage = (ImageView) view.findViewById(R.id.image_frame);
        mSceneAnimation = new SceneAnimation(mFrameImage, 75);
        mFrameLayout.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (type==1&&isFirst&&model!=null&&model.getDishonestylist()!=null){
            mResultAdapter.refresh(model.getDishonestylist());
            if ("true".equals(model.getIsmore())) {
                listView.setPullLoadEnable(true);
                listView.removeFooterView(footview);
                isAddFoot=false;
            }
            else {
                listView.setPullLoadEnable(false);
                if (!isAddFoot) {
                    listView.addFooterView(footview);
                    isAddFoot=true;
                }
            }
            isFirst=false;
            return;
        }
        isFirst=false;
        if (getUserVisibleHint()){
            refresh(true);
        }else {
            needResult=true;
        }
    }

    @Override
    protected void initWeightActions() {
        listView.setAdapter(mResultAdapter);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                refresh(false);
            }

            @Override
            public void onLoadMore() {
                loadmore();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (view.getTag() instanceof SearchDishonestAdapter.ViewHolder) {
                    SearchDishonestAdapter.ViewHolder holder = (SearchDishonestAdapter.ViewHolder) view.getTag();
                    if (holder == null) {
                        return;
                    }

                    DisHonestItemModel model = holder.getData();
                    if (model != null) {

                        EventUtils.event(mContext, EventUtils.SEARCH28);

                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra(WebActivity.URL, model.getUrl());
                        intent.putExtra(WebActivity.TITLE, "失信详情");
                        mContext.startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof DishonestResultEvent){
            DishonestResultEvent resultEvent= (DishonestResultEvent) event;
            for (Map.Entry<String, String> entry : resultEvent.getParams().entrySet()) {
                params.put(entry.getKey(),entry.getValue());
            }
            if (getUserVisibleHint()){
                refresh(true);
            }else {
                needResult=true;
            }
        }

    }

    private void refresh(boolean isShow){
        showLoading();
        if (isShow){
            Log.e("refresh","true");
            mSceneAnimation.start();
            mFrameLayout.setVisibility(View.VISIBLE);
        }

        params.put("type",type+"");
        params.put("searchkey",searchKey);
        params.put("pageSize","20");
        params.put("pageIndex","1");
        if (LoginSharePreference.getUserInfo(mContext)!=null
                &&!TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid()))
            params.put("userid",LoginSharePreference.getUserInfo(mContext).getUserid());
        SearchRequestRouter.SearchDishonest(mContext, params, TAG, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                mSceneAnimation.stop();
                mFrameLayout.setVisibility(View.GONE);
                SearchDisHonestModel model= (SearchDisHonestModel) data;
                listView.stopRefresh();
                if (model.getResult()==0) {
                    if (model.getDishonestylist()!=null&&model.getDishonestylist().size()>0) {
                        mResultAdapter.refresh(model.getDishonestylist());
                        pageindex = 1;
                        if ("false".equals(model.getIsmore())) {
                            listView.setPullLoadEnable(false);
                            if (!isAddFoot) {
                                listView.addFooterView(footview);
                                isAddFoot=true;
                            }
                        } else if ("true".equals(model.getIsmore())) {
                            listView.setPullLoadEnable(true);
                            if (isAddFoot){
                                listView.removeFooterView(footview);
                                isAddFoot=false;
                            }
                        }
                    }else {
                        showToast(mContext.getString(R.string.search_result_none));
                        mResultAdapter.cleanAllData();
                    }
                }
                else {
                    showToast(model.getMsg());
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                mSceneAnimation.stop();
                mFrameLayout.setVisibility(View.GONE);
                listView.stopRefresh();
                showToast(error);
            }
        });
    }

    private void loadmore(){
        params.put("type",type+"");
        params.put("searchkey",searchKey);
        params.put("pageSize","20");
        params.put("pageIndex",(pageindex+1)+"");
        if (LoginSharePreference.getUserInfo(mContext)!=null
                &&!TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid()))
            params.put("userid",LoginSharePreference.getUserInfo(mContext).getUserid());
        SearchRequestRouter.SearchDishonest(mContext, params, TAG, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                listView.stopLoadMore();
                SearchDisHonestModel model= (SearchDisHonestModel) data;
                if (model.getResult()==0) {
                    if (model.getDishonestylist()!=null&&model.getDishonestylist().size()>0) {
                        mResultAdapter.addData(model.getDishonestylist());
                        pageindex++;
                        if ("false".equals(model.getIsmore())) {
                            listView.setPullLoadEnable(false);
                            if (!isAddFoot) {
                                listView.addFooterView(footview);
                                isAddFoot=true;
                            }
                        } else if ("true".equals(model.getIsmore())) {
                            listView.setPullLoadEnable(true);
                            if (isAddFoot){
                                listView.removeFooterView(footview);
                                isAddFoot=false;
                            }
                        }
                    }else {
                        showToast(mContext.getString(R.string.search_result_none));
                    }
                }else {
                    showToast(model.getMsg());
                }
            }

            @Override
            public void onFail(String error) {
                listView.stopLoadMore();
                showToast(error);
            }
        });
    }
}
