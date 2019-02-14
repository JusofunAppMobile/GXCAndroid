package com.jusfoun.jusfouninquire.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxc.ui.activity.LoginActivity;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.FocusedItemModel;
import com.jusfoun.jusfouninquire.net.model.FocusedModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.route.SearchRequestRouter;
import com.jusfoun.jusfouninquire.service.event.CompleteLoginEvent;
import com.jusfoun.jusfouninquire.service.event.FollowSucceedEvent;
import com.jusfoun.jusfouninquire.service.event.HideUpdateEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailActivity;
import com.jusfoun.jusfouninquire.ui.adapter.AttentionAdapter;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.XListView;
import com.jusfoun.library.animationadapter.adapter.AnimationAdapter;
import com.jusfoun.library.animationadapter.adapter.ScaleInAnimationAdapter;
import com.siccredit.guoxin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :搜索页面我的关注fragment
 */
public class MyFocusesFragment extends BaseInquireFragment implements XListView.IXListViewListener{

    private AttentionAdapter mFocusesAdapter;
    private AnimationAdapter mAnimAdapter;

    private XListView mFocusesList;
    private LinearLayout mUnLogin;
    private TextView mLogin;

    private LinearLayout mNoFoucus;
    private TextView mNoFocusTip;

    private NetWorkErrorView mNetWorkError;

    private List<FocusedItemModel> mFocusedListData;
    private int mPageIndex;

    private UserInfoModel userInfoModel;
    private boolean mAlreadyRequested;

    private RelativeLayout mFrameLayout;
    private ImageView mFrameImage;
    private SceneAnimation mSceneAnimation;

    public static final int LOGIN_REQUEST = 1;


    @Override
    protected void initData() {
        mFocusesAdapter = new AttentionAdapter(mContext);
        mAnimAdapter = new ScaleInAnimationAdapter(mFocusesAdapter);
        mFocusedListData = new ArrayList<>();
        mPageIndex = 1;

        userInfoModel = LoginSharePreference.getUserInfo(mContext);

        mAlreadyRequested = false;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_focuses_fragment_layout, container, false);
        mFocusesList = (XListView)view.findViewById(R.id.my_focuses_list);
        mFocusesList.setXListViewListener(this);
        mFocusesList.setPullLoadEnable(false);
        mFocusesList.setPullRefreshEnable(true);
        mAnimAdapter.setAbsListView(mFocusesList);
        mFocusesList.setAdapter(mAnimAdapter);
        mUnLogin = (LinearLayout)view.findViewById(R.id.unlogin_layout);
        mLogin = (TextView)view.findViewById(R.id.my_focus_login);

        mNoFoucus = (LinearLayout) view.findViewById(R.id.no_focuses_layout);
        mNoFocusTip = (TextView) view.findViewById(R.id.no_focus_tip);
        SpannableString spannableString   = new SpannableString(mNoFocusTip.getText());

        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.search_table_background)), 4,
                9 , Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.search_table_background)), 11,
                15 , Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mNoFocusTip.setText(spannableString);

        mNetWorkError = (NetWorkErrorView) view.findViewById(R.id.net_work_error);

        mFrameLayout = (RelativeLayout) view.findViewById(R.id.image_frame_layout);
        mFrameImage = (ImageView) view.findViewById(R.id.image_frame);
        mSceneAnimation = new SceneAnimation(mFrameImage,75);

        return view;
    }

    @Override
    protected void initWeightActions() {
        mFocusesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttentionAdapter.ViewHolder holder = (AttentionAdapter.ViewHolder) view.getTag();
                if (holder == null) {
                    return;
                }
                FocusedItemModel model = holder.mFocuseModel;
                if (model != null) {
                    Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                    intent.putExtra(CompanyDetailActivity.COMPANY_NAME,model.getCompanyname());
                    intent.putExtra(CompanyDetailActivity.COMPANY_ID,model.getCompanyid());
                    startActivity(intent);
                }
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LoginActivity.class);
//                intent.putExtra(LoginActivity.FROM_MY_FOCUS, true);
                //startActivityForResult(intent, LOGIN_REQUEST);
                startActivity(intent);
            }
        });

        if ((userInfoModel != null) && (!TextUtils.isEmpty(userInfoModel.getUserid()))){
            mUnLogin.setVisibility(View.GONE);
            if (getUserVisibleHint()){
                doRequestFocus(false,true);
            }

        }else {

            mUnLogin.setVisibility(View.VISIBLE);
        }

        mNetWorkError.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                doRequestFocus(mPageIndex > 1, true);
            }
        });

       // doRequestFocus(false);
    }

    private void doRequestFocus(final boolean isAdd,boolean showdialog){
        if (showdialog){
            mNoFoucus.setVisibility(View.GONE);
            mNetWorkError.setVisibility(View.GONE);
            mFocusesList.setVisibility(View.GONE);
            mFrameLayout.setVisibility(View.VISIBLE);
            mSceneAnimation.start();
        }
        mAlreadyRequested = true;
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("userid",userInfoModel.getUserid());
        map.put("pagenumber",mPageIndex + "");
        map.put("pagesize", "50");
        SearchRequestRouter.getFocused(mContext, map, ((Activity)mContext).getLocalClassName(),new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                mFrameLayout.setVisibility(View.GONE);
                mSceneAnimation.stop();
                if (data != null) {
                    FocusedModel model = (FocusedModel) data;
                    if (model.getResult() == 0) {
                        mUnLogin.setVisibility(View.GONE);
                        mNetWorkError.setVisibility(View.GONE);
                        if (isAdd) {
                            mFocusesList.stopLoadMore();
                            mPageIndex = mPageIndex + 1;
                        } else {
                            mFocusesList.stopRefresh();
                            mPageIndex = 1;
                        }
                        if ("true".equals(model.getIsmore())) {
                            mFocusesList.setPullLoadEnable(true);
                        } else {
                            mFocusesList.setPullLoadEnable(false);
                        }
                        if (model.getMywatchlist() != null) {
                            if (model.getMywatchlist().size() > 0) {
                                mFocusesList.setVisibility(View.VISIBLE);
                                mNoFoucus.setVisibility(View.GONE);
                                if (isAdd) {
                                    mFocusedListData.addAll(model.getMywatchlist());
                                    mFocusesAdapter.addMore(model.getMywatchlist());
                                } else {
                                    mFocusesAdapter.refresh(model.getMywatchlist());
                                    mFocusedListData.clear();
                                    mFocusedListData.addAll(model.getMywatchlist());
                                }

                            } else {
                                mFocusesList.setVisibility(View.GONE);
                                mNoFoucus.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        mNetWorkError.setServerError();
                        mNetWorkError.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                mFrameLayout.setVisibility(View.GONE);
                mSceneAnimation.stop();
                mNetWorkError.setNetWorkError();
                mNetWorkError.setVisibility(View.VISIBLE);
            }
        });

    }

    private void hideUpdateStatus(String companyid){
        for (FocusedItemModel model : mFocusedListData){
            if (companyid.equals(model.getCompanyid())){
                model.setIsupdate(0);
                break;
            }
        }
        mFocusesAdapter.refresh(mFocusedListData);
    }

    @Override
    public void onRefresh() {

        doRequestFocus(false,false);
    }

    @Override
    public void onLoadMore() {
        doRequestFocus(true, false);
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof HideUpdateEvent){
            HideUpdateEvent ev = (HideUpdateEvent)event;
            hideUpdateStatus(ev.getCompanyid());
        }else if ((event instanceof CompleteLoginEvent) || (event instanceof FollowSucceedEvent)){
            userInfoModel = LoginSharePreference.getUserInfo(mContext);
            if ((userInfoModel != null) && (!TextUtils.isEmpty(userInfoModel.getUserid()))){
                doRequestFocus(false,true);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !mAlreadyRequested){
            if (userInfoModel != null && !TextUtils.isEmpty(userInfoModel.getUserid())){
                doRequestFocus(false,true);
            }

        }
    }

    /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == mContext.RESULT_OK){
            if (requestCode == LOGIN_REQUEST){
                if ((data != null) && data.getExtras() != null){
                    String value = data.getExtras().getString(LoginActivity.USER_INFO,"");
                    if (!TextUtils.isEmpty(value)){
                        userInfoModel = new Gson().fromJson(value,UserInfoModel.class);
                        if ((userInfoModel != null) && (!TextUtils.isEmpty(userInfoModel.getUserid()))){
                            mUnLogin.setVisibility(View.INVISIBLE);
                            doRequestFocus(false,true);
                        }else {
                            mUnLogin.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }
    }*/


}
