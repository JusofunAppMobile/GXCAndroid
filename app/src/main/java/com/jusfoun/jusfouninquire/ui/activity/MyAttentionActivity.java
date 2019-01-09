package com.jusfoun.jusfouninquire.ui.activity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.FocusedItemModel;
import com.jusfoun.jusfouninquire.net.model.FocusedModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.route.PersonCenterHelper;
import com.jusfoun.jusfouninquire.service.event.FollowSucceedEvent;
import com.jusfoun.jusfouninquire.service.event.HideUpdateEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.adapter.AttentionAdapter;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:15/10/30下午4:26
 * @Email zyp@jusfoun.com
 * @Description ${我的关注}
 */
public class MyAttentionActivity extends BaseInquireActivity implements XListView.IXListViewListener {
    /**常量*/

    /**组件*/
    private TitleView titleView;
    private XListView mlistView;
    private LinearLayout mLookAny;
    private NetWorkErrorView netErrorLayout;
    private TextView mNoFocusTip;
    private RelativeLayout mFrameLayout;
    private ImageView mFrameImage;
    private SceneAnimation mSceneAnimation;

    /**变量*/
    private int pagenum = 1;

    private  String userId = "";

    private List<FocusedItemModel> datalist;

    /**对象*/
    private AttentionAdapter adapter;
    @Override
    protected void initData() {
        if(InquireApplication.getUserInfo()!=null
        &&!TextUtils.isEmpty(InquireApplication.getUserInfo().getUserid())){
            userId = InquireApplication.getUserInfo().getUserid();
        }
        adapter=new AttentionAdapter(mContext);
        datalist = new ArrayList<>();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_attention);
        titleView = (TitleView) findViewById(R.id.titleView);
//        titleView.setLeftViewBackgroundResource(R.mipmap.back_image);
        titleView.setTitle("我的关注");
        mlistView = (XListView) findViewById(R.id.attention_xlistview);
        mLookAny=(LinearLayout) findViewById(R.id.look_any);
        netErrorLayout = (NetWorkErrorView) findViewById(R.id.neterrorlayout);

        mNoFocusTip = (TextView) findViewById(R.id.no_focus_tip);
        SpannableString spannableString   = new SpannableString(mNoFocusTip.getText());

        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.search_table_background)), 4,
                9 , Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.search_table_background)), 11,
                15 , Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mNoFocusTip.setText(spannableString);

        mFrameLayout = (RelativeLayout) findViewById(R.id.image_frame_layout);
        mFrameImage = (ImageView)findViewById(R.id.image_frame);
        mSceneAnimation = new SceneAnimation(mFrameImage,75);
    }

    @Override
    protected void initWidgetActions() {
        mlistView.setAdapter(adapter);
        mlistView.setPullRefreshEnable(true);
        mlistView.setPullLoadEnable(false);
        mlistView.setXListViewListener(this);

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getTag() instanceof AttentionAdapter.ViewHolder) {
                    FocusedItemModel model = ((AttentionAdapter.ViewHolder) view.getTag()).mFocuseModel;
                    Bundle bundle = new Bundle();
                    bundle.putString(CompanyDetailActivity.COMPANY_ID, model.getCompanyid());
                    bundle.putString(CompanyDetailActivity.COMPANY_NAME, model.getCompanyname());
                    goActivity(CompanyDetailActivity.class, bundle);
                }

            }
        });
        getAttention(true);

        netErrorLayout.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                netErrorLayout.setVisibility(View.GONE);
                getAttention(true);//点击错误页面重新再获取
            }
        });

        dealCenterView();
    }

    private void dealCenterView(){
        UserInfoModel userinfo = LoginSharePreference.getUserInfo(mContext);
        if ((userinfo != null) && (!TextUtils.isEmpty(userinfo.getUserid()))){
            userinfo.setMyfocusunread("0");
            LoginSharePreference.saveUserInfo(userinfo,mContext);
        }
    }

    //获取我的关注或刷新我的关注
    private void getAttention(boolean showloading){
        if (showloading){
            mFrameLayout.setVisibility(View.VISIBLE);
            mSceneAnimation.start();
        }
        HashMap<String,String> map = new HashMap<>();
        map.put("userid", userId);
        map.put("pageindex", "1");
        map.put("pagesize", "10");
        //showLoading();
        PersonCenterHelper.doNetGetMyAttention(mContext, map,getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {

                finishLoadMoreOrRefresh();
                pagenum = 1;
                FocusedModel model = (FocusedModel) data;
                if (model.getResult() == 0) {
                    if ("true".equals(model.getIsmore())) {
                        mlistView.setPullLoadEnable(true);
                    } else {
                        mlistView.setPullLoadEnable(false);
                    }
                    if (model.getMywatchlist() != null && model.getMywatchlist().size() > 0) {
                        mlistView.setVisibility(View.VISIBLE);
                        netErrorLayout.setVisibility(View.GONE);
                        mLookAny.setVisibility(View.GONE);
                        datalist.clear();
                        datalist.addAll(model.getMywatchlist());
                        adapter.refresh(datalist);

                    } else {
                        mLookAny.setVisibility(View.VISIBLE);
                        mlistView.setVisibility(View.GONE);
                        netErrorLayout.setVisibility(View.GONE);
                    }
                } else {
                    netErrorLayout.setServerError();
                    netErrorLayout.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFail(String error) {
                Log.d("TAG", error + "");
                finishLoadMoreOrRefresh();
                mlistView.setVisibility(View.GONE);
                mLookAny.setVisibility(View.GONE);
                netErrorLayout.setNetWorkError();
                netErrorLayout.setVisibility(View.VISIBLE);
            }
        });
    }
    //获取我的关注
    private void getMoreAttention(int pageIndex){
        HashMap<String,String> map = new HashMap<>();
        map.put("userid",userId);
        map.put("pageindex",pageIndex+"");
        map.put("pagesize","10");

        showLoading();
        PersonCenterHelper.doNetGetMyAttention(mContext, map,getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                finishLoadMoreOrRefresh();
                FocusedModel model = (FocusedModel) data;
                if (model.getResult() == 0) {
                    pagenum++;
                    if ("true".equals(model.getIsmore())) {
                        mlistView.setPullLoadEnable(true);
                    } else {
                        mlistView.setPullLoadEnable(false);
                    }
                    if(model.getMywatchlist()!= null && model.getMywatchlist().size() > 0){
                        mlistView.setVisibility(View.VISIBLE);
                        mLookAny.setVisibility(View.GONE);
                        netErrorLayout.setVisibility(View.GONE);
                        datalist.addAll(model.getMywatchlist());
                        adapter.addMore(model.getMywatchlist());
                    }else {
                       /* mlistView.setVisibility(View.GONE);
                        netErrorLayout.setVisibility(View.GONE);
                        nodatalayout.setVisibility(View.VISIBLE);*/
                    }
                }else {
                    mlistView.setVisibility(View.GONE);
                    netErrorLayout.setServerError();
                    netErrorLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(String error) {
                finishLoadMoreOrRefresh();
                mlistView.setVisibility(View.GONE);
                mLookAny.setVisibility(View.GONE);
                netErrorLayout.setNetWorkError();
                netErrorLayout.setVisibility(View.VISIBLE);
                showToast("获取失败");
            }
        });
    }

    private void finishLoadMoreOrRefresh(){
        hideLoadDialog();
        mFrameLayout.setVisibility(View.GONE);
        mSceneAnimation.stop();
        mlistView.stopRefresh();
        mlistView.stopLoadMore();
    }


    @Override
    public void onRefresh() {
        getAttention(false);
    }

    @Override
    public void onLoadMore() {
        getMoreAttention(pagenum + 1);
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof FollowSucceedEvent){
            getAttention(false);
        }else if (event instanceof HideUpdateEvent){
            HideUpdateEvent ev = (HideUpdateEvent) event;
            hideUpdateStatus(ev.getCompanyid());
        }
    }

    private void hideUpdateStatus(String companyid){
        for (FocusedItemModel model : datalist){
            if (companyid.equals(model.getCompanyid())){
                model.setIsupdate(0);
                break;
            }
        }
        adapter.refresh(datalist);
    }
}
