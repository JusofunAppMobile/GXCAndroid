package com.jusfoun.jusfouninquire.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.model.SystemMsgItemModel;
import com.jusfoun.jusfouninquire.net.model.SystemMsgModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.route.PersonCenterHelper;
import com.jusfoun.jusfouninquire.service.event.MsgChangeEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.adapter.SystemMsgAdapter;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.view.XListView;
import com.jusfoun.jusfouninquire.ui.widget.SwipeRightMenuLayout;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * @author zhaoyapeng
 * @version create time:15/10/30下午4:27
 * @Email zyp@jusfoun.com
 * @Description ${系统消息}
 */
public class SystemMessageActivity extends BaseInquireActivity implements XListView.IXListViewListener {
    /**常量*/


    /**
     * 组件
     */
    private XListView mlistView;
    private TitleView titleView;
    private LinearLayout mLookany;
    private NetWorkErrorView netErrorLayout;


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
    private SystemMsgAdapter adapter;
    @Override
    protected void initData() {
        if((LoginSharePreference.getUserInfo(mContext) != null) && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())){
            userId = LoginSharePreference.getUserInfo(mContext).getUserid();
        }
        adapter = new SystemMsgAdapter(mContext);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_system_message);
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("我的消息");
//        titleView.setLeftSrc(R.mipmap.back_image);
        mlistView = (XListView) findViewById(R.id.system_msg_xlistview);
        mLookany=(LinearLayout) findViewById(R.id.look_any);
        netErrorLayout = (NetWorkErrorView) findViewById(R.id.neterrorlayout);

        mFrameLayout = (RelativeLayout) findViewById(R.id.image_frame_layout);
        mFrameImage = (ImageView)findViewById(R.id.image_frame);
        mSceneAnimation = new SceneAnimation(mFrameImage,75);
    }

    @Override
    protected void initWidgetActions() {
        mlistView.setAdapter(adapter);
        mlistView.setXListViewListener(this);
        mlistView.setPullRefreshEnable(false);
        mlistView.setPullLoadEnable(false);

        //OnItemClick事件被"滑动删除"逻辑掩盖,采用adapter中的onclick事件进行接口回调
//        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                if (view.getTag() instanceof SystemMsgAdapter.ViewHolder) {
//                    SystemMsgItemModel model = ((SystemMsgAdapter.ViewHolder) view.getTag()).mSysMsgModel;
//                    if (model != null){
//                        if (!model.isRead()){
//                            MsgChangeEvent event = new MsgChangeEvent();
//                            event.setDelta(-1);
//                            EventBus.getDefault().post(event);
//                            adapter.setRead(position);
//                            dealMsgStatus(model.getId(),"2",-1);
//
//                        }
//                    }
//                    if ("1".equals(model.getType())) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString(CompanyDetailActivity.COMPANY_ID, model.getObjectid());
//                        bundle.putString(CompanyDetailActivity.COMPANY_NAME, "");
//                        goActivity(CompanyDetailActivity.class, bundle);
//                    } else if ("2".equals(model.getType())) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString(WebActivity.TITLE, model.getTitle());
//                        bundle.putString(WebActivity.URL, model.getH5url());
//                        goActivity(WebActivity.class, bundle);
//                    }
//                }
//            }
//        });
        netErrorLayout.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                netErrorLayout.setVisibility(View.GONE);
                getSystemMessage();
            }
        });

        adapter.setListener(new SwipeRightMenuLayout.OnClosedMenuListener() {
            @Override
            public void onClose() {
                mlistView.setClose();
            }
        });


        adapter.setDeleteListener(new SystemMsgAdapter.OnDeleteListener() {
            @Override
            public void onDelete(int position,String msgid) {
                dealMsgStatus(msgid,"1",position);
            }

            @Override
            public void onStatusChanged(SystemMsgItemModel model,int position) {
                if (!model.isRead()){
                    dealMsgStatus(model.getId(),"2",position);
                }
                if ("1".equals(model.getType())) {
                    Bundle bundle = new Bundle();
                    bundle.putString(CompanyDetailActivity.COMPANY_ID, model.getObjectid());
                    bundle.putString(CompanyDetailActivity.COMPANY_NAME, "");
                    goActivity(CompanyDetailActivity.class, bundle);
                } else if ("2".equals(model.getType())) {
                    Bundle bundle = new Bundle();
                    bundle.putString(WebActivity.TITLE, model.getTitle());
                    bundle.putString(WebActivity.URL, model.getH5url());
                    goActivity(WebActivity.class, bundle);
                }
            }
        });

        getSystemMessage();
        //dealCenterView();

    }

    /**
     * 处理消息状态
     * @param msgid 消息id
     * @param status "1" 代表删除  "2"代表已读
     */
    private void dealMsgStatus(String msgid, final String status, final int position){
        //标记已读或者删除消息,都需要发送事件,刷新个人中心显示
        MsgChangeEvent event = new MsgChangeEvent();
        event.setDelta(-1);
        EventBus.getDefault().post(event);
        adapter.setRead(position);

        UserInfoModel userInfoModel = LoginSharePreference.getUserInfo(mContext);
        if (userInfoModel != null && !TextUtils.isEmpty(userInfoModel.getUserid())){
            HashMap<String,String> params = new HashMap<>();
            params.put("userid", userId);
            params.put("messageid", msgid);
            params.put("type", status);
            PersonCenterHelper.dealSystemMsg(mContext, params, getLocalClassName(), new NetWorkCallBack() {
                @Override
                public void onSuccess(Object data) {
                    if (data instanceof BaseModel){
                        BaseModel model = (BaseModel) data;
                        if (model.getResult() == 0){
                            //删除操作
                            if ("1".equals(status)){
                                View view = getViewByPosition(position + 1,mlistView);
                                if (view != null){
                                    adapter.resetData(position,view);
                                }
                            }
                        }else {
                            showToast(model.getMsg());
                        }
                    }
                }

                @Override
                public void onFail(String error) {
                    showToast(error);
                }
            });


        }


    }



    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }


    /**
     * 业务逻辑: 进入此页面之后,个人中心页面不再显示有未读消息的提示
     */
    private void dealCenterView(){
        UserInfoModel userinfo = LoginSharePreference.getUserInfo(mContext);
        if ((userinfo != null) && (!TextUtils.isEmpty(userinfo.getUserid()))){
            userinfo.setSystemmessageunread("0");
            LoginSharePreference.saveUserInfo(userinfo,mContext);
        }
    }



    //获取我的关注或刷新
    private void getSystemMessage() {
        mFrameLayout.setVisibility(View.VISIBLE);
        mSceneAnimation.start();

        HashMap<String,String> map = new HashMap<>();
        map.put("userid", userId);
        map.put("pageindex", "1");
        map.put("pagesize", "20");
       // showLoading();

        PersonCenterHelper.doNetGetSystemMsg(mContext, map, getLocalClassName(),new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                mFrameLayout.setVisibility(View.GONE);
                mSceneAnimation.stop();
                finishLoadMoreOrRefresh();
                SystemMsgModel model = (SystemMsgModel) data;
                if (model.getResult() == 0) {
                    pagenum = 1;
                    if ("true".equals(model.getIsmore())) {
                        mlistView.setPullLoadEnable(true);
                    } else {
                        mlistView.setPullLoadEnable(false);
                    }
                    if (model.getSystemlist() != null && model.getSystemlist().size() > 0) {
                        mlistView.setVisibility(View.VISIBLE);
                        netErrorLayout.setVisibility(View.GONE);
                        mLookany.setVisibility(View.GONE);
                        adapter.refresh(model.getSystemlist());
                    } else {
                        mlistView.setVisibility(View.GONE);
                        netErrorLayout.setVisibility(View.GONE);
                        mLookany.setVisibility(View.VISIBLE);
                    }
                } else {
                    mlistView.setVisibility(View.GONE);
                    netErrorLayout.setServerError();
                    netErrorLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(String error) {
                Log.d("TAG", error + "");
                mFrameLayout.setVisibility(View.GONE);
                mSceneAnimation.stop();
                finishLoadMoreOrRefresh();
                mlistView.setVisibility(View.GONE);
                netErrorLayout.setNetWorkError();
                netErrorLayout.setVisibility(View.VISIBLE);

            }

        });
    }



    //获取更多的系统消息
    private void getMoreSystemMsg(int pageIndex){
        HashMap<String,String> map = new HashMap<>();
        map.put("userid",userId);
        map.put("pageindex",pageIndex+"");
        map.put("pagesize","10");

        showLoading();
        PersonCenterHelper.doNetGetSystemMsg(mContext, map, getLocalClassName(),new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                finishLoadMoreOrRefresh();
                SystemMsgModel model = (SystemMsgModel) data;
                if (model.getResult() == 0) {
                    pagenum++;
                    if ("true".equals(model.getIsmore())) {
                        mlistView.setPullLoadEnable(true);
                    } else {
                        mlistView.setPullLoadEnable(false);
                    }
                    if (model.getSystemlist() != null && model.getSystemlist().size() > 0) {
                        adapter.addMore(model.getSystemlist());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                finishLoadMoreOrRefresh();
                showToast("获取失败");
            }
        });
    }

    private void finishLoadMoreOrRefresh(){
        hideLoadDialog();

        mlistView.stopRefresh();
        mlistView.stopLoadMore();
    }

    @Override
    public void onRefresh() {
        //getSystemMessage();
    }

    @Override
    public void onLoadMore() {
        getMoreSystemMsg(pagenum+1);
    }
}
