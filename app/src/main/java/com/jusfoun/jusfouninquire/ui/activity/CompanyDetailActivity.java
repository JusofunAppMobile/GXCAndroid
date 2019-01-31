package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gxc.base.BaseActivity;
import com.gxc.event.CollectChangeEvent;
import com.gxc.event.LoginChangeEvent;
import com.gxc.event.MonitorChangeEvent;
import com.gxc.model.CorporateInfoModel;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.activity.CertifiedCompanyActivity;
import com.gxc.ui.activity.CreditReportActivity;
import com.gxc.ui.activity.LoginActivity;
import com.gxc.ui.adapter.DongJGAdapter;
import com.gxc.ui.adapter.ShareholderAdapter;
import com.gxc.ui.dialog.VIPDialog;
import com.gxc.ui.view.BottomBarView;
import com.gxc.ui.view.CompanyMapView;
import com.gxc.utils.AppUtils;
import com.gxc.utils.ToastUtils;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.TimeOut;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.net.model.ContactinFormationModel;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.net.model.ShareModel;
import com.jusfoun.jusfouninquire.net.route.NetWorkCompanyDetails;
import com.jusfoun.jusfouninquire.service.event.CompleteLoginEvent;
import com.jusfoun.jusfouninquire.service.event.HideUpdateEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.service.event.UpdateAttentionEvent;
import com.jusfoun.jusfouninquire.ui.adapter.CompanyMenuAdapter;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.util.ShareUtil;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;
import com.jusfoun.jusfouninquire.ui.view.CompanyDetailHeaderView;
import com.jusfoun.jusfouninquire.ui.view.CompanyDetailMenuView;
import com.jusfoun.jusfouninquire.ui.view.MyHeaderView;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.PropagandaView.BackAndImageTitleView;
import com.jusfoun.jusfouninquire.ui.widget.FullyGridLayoutManger;
import com.jusfoun.jusfouninquire.ui.widget.ShareDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;
import netlib.util.EventUtils;

/**
 * @author zhaoyapeng
 * @version create time:15/11/2下午4:55
 * @Email zyp@jusfoun.com
 * @Description ${公司详情页面}
 */
public class CompanyDetailActivity extends BaseActivity {

    public static final String COMPANY_ID = "company_id";
    public static final String COMPANY_NAME = "company_name";
    private CompanyDetailHeaderView headerView;
    private HashMap<String, String> params;
    private NetWorkErrorView netWorkError;
    private RecyclerView mCompanyMenu;
    private CompanyMenuAdapter adapter;
    private CompanyDetailModel model;
    private BackAndImageTitleView title, loadingBack;
    private String mCompanyId = "", mCompanyName;
    private ShareDialog shareDialog;

    private ShareUtil shareUtil;
    private MyHeaderView myHeaderView;
    private RelativeLayout loadingLayout;
    private ImageView imageView;

    private SceneAnimation sceneAnimation;
    private List<ContactinFormationModel> contactinList;
    private CompanyDetailMenuView riskInfoVide, operatingConditionsView, intangibleAssetsView;
    private Handler updateHandler;
    private final int UPDATE_MSG = 1;
    private final int UPDATE_DELAY = 20 * 1000;

    public final static int TYPE_FENGXIAN = 1;
    public final static int TYPE_GUQUAN = 2;
    public final static int TYPE_QIYEBAOGAO = 3;

    private RecyclerView shareHolderRecycle, dongshiRecycle;
    private ShareholderAdapter shareholderAdapter;
    private DongJGAdapter dongshiAdaper;

    private BottomBarView navigation;
    private CompanyMapView companyMapView;
    private CorporateInfoModel corporateInfoModel;
    private ImageView fengxianImg;
    private LinearLayout gxcLayout;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarEnable(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_detail;
    }


    public static Intent getIntent(Context context, String id, String name) {
        Intent intent = new Intent(context, CompanyDetailActivity.class);
        intent.putExtra(CompanyDetailActivity.COMPANY_ID, id);
        intent.putExtra(CompanyDetailActivity.COMPANY_NAME, name);
        return intent;
    }


    @Override
    public void initActions() {

        scrollView = findViewById(R.id.scrollView);
        mCompanyMenu = findViewById(R.id.company_menu);
        title = findViewById(R.id.title);
        loadingBack = findViewById(R.id.title_back);
        netWorkError = findViewById(R.id.net_work_error);
        riskInfoVide = findViewById(R.id.view_risk_info);
        operatingConditionsView = findViewById(R.id.view_operating_conditions);
        intangibleAssetsView = findViewById(R.id.view_intangible_assets);
        myHeaderView = new MyHeaderView(activity);

        loadingLayout = findViewById(R.id.loading);
        imageView = findViewById(R.id.loading_img);
        headerView = findViewById(R.id.headerview);
        shareHolderRecycle = findViewById(R.id.recyclerview_shareholder);
        dongshiRecycle = findViewById(R.id.recyclerview_dongshi);
        navigation = findViewById(R.id.navigation);
        companyMapView = findViewById(R.id.view_company_map);
        fengxianImg = findViewById(R.id.img_fengxian);
        gxcLayout = findViewById(R.id.layout_gxc);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString(COMPANY_ID) != null)
                mCompanyId = bundle.getString(COMPANY_ID);
            mCompanyName = bundle.getString(COMPANY_NAME);
        }
        adapter = new CompanyMenuAdapter(activity);
        shareUtil = new ShareUtil(activity);

        shareDialog = new ShareDialog(activity, R.style.tool_dialog);
        shareDialog.setCancelable(true);

        shareholderAdapter = new ShareholderAdapter();
        dongshiAdaper = new DongJGAdapter();

        initWidgetActions();
    }

    @Override
    public boolean isBarDark() {
        return false;
    }

    @Override
    public boolean isSetStatusBar() {
        return false;
    }


    protected void initWidgetActions() {
        title.setTitleText("企业详情");
        setStatusBar(R.id.vStatusBar);

        navigation.setTabSelectedListener(new BottomBarView.TabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (corporateInfoModel == null || corporateInfoModel.companyInfo == null)
                    return;
                UserModel user = AppUtils.getUser();
                if (user == null) {
                    startActivity(com.gxc.ui.activity.LoginActivity.class);
                    return;
                }
                if (position == 0) {
                    Intent intent = new Intent(CompanyDetailActivity.this, CreditReportActivity.class);
                    intent.putExtra("companyId", mCompanyId);
                    intent.putExtra("companyName", mCompanyName);
                    startActivity(intent);
                } else if (position == 1) {
                    if (model != null) {
                        EventUtils.event(activity, EventUtils.BUSINESSDETAILS01);
                        Intent intent = new Intent(CompanyDetailActivity.this, CompanyAmendActivity.class);
                        intent.putExtra("companyId", mCompanyId);
                        intent.putExtra("companyName", mCompanyName);
                        intent.putExtra("taxid", model.taxid);
                        intent.putExtra("states", model.getStates());
                        intent.putExtra(CompanyAmendActivity.TYPE, CompanyAmendActivity.TYPE_ERROR);
                        startActivity(intent);
                    }
                } else if (position == 2) {
                    monitorHandle();
                } else if (position == 3) {
                    if (corporateInfoModel.companyInfo.CertificationType == 0)
                        startActivity(CertifiedCompanyActivity.class);
                }
            }
        });
        netWorkError.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                loadingLayout.setVisibility(View.VISIBLE);
                if (sceneAnimation == null)
                    sceneAnimation = new SceneAnimation(imageView, 75);
                sceneAnimation.start();
                getCompanyDetail();
            }
        });

        mCompanyMenu.setLayoutManager(new FullyGridLayoutManger(this, 4));
        mCompanyMenu.setAdapter(adapter);
        AppUtils.addItemDecoration(this, mCompanyMenu);
        adapter.setOnItemClickListener(new CompanyMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String umeng) {
                EventUtils.event2(activity, umeng);

                if (model == null)
                    return;
                Bundle argument = new Bundle();

                argument.putSerializable(CompanyDetailsActivity.COMPANY, model);
                argument.putInt(CompanyDetailsActivity.POSITION, position);
                startActivity(CompanyDetailsActivity.class, argument);
            }
        });

        headerView.setUpdateListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCompanyInfo();
            }
        });

        contactinList = new ArrayList<ContactinFormationModel>();

        title.setmFollowListener(new BackAndImageTitleView.FollowListener() {
            @Override
            public void onClick() {
                if (model == null)
                    return;
                EventUtils.event(activity, EventUtils.DETAIL57);
//                putFollowState(model.getIsattention());
                collectHandle();
            }
        });

        title.setmShareListener(new BackAndImageTitleView.ShareListener() {
            @Override
            public void onClick() {
                EventUtils.event(activity, EventUtils.DETAIL58);
                showShareDialog();
                EventUtils.event(activity, EventUtils.BUSINESSDETAILS03);
            }
        });
        shareDialog.setShareListener(new ShareDialog.ShareListener() {
            @Override
            public void onFriendersShare() {
                if (model == null)
                    return;
                ShareModel shareModel = new ShareModel();
                if (!TextUtils.isEmpty(model.getCompanyname()))
                    shareModel.setContent(getString(R.string.share_content_company) + model.getCompanyname());
                shareModel.setTitle(getString(R.string.share_title_company));
                if (!TextUtils.isEmpty(model.getShareurl()))
                    shareModel.setUrl(model.getShareurl());
                shareUtil.shareByType(activity, shareModel, SHARE_MEDIA.WEIXIN_CIRCLE);
            }

            @Override
            public void onWechatShare() {
                if (model == null)
                    return;
                ShareModel shareModel = new ShareModel();
                if (!TextUtils.isEmpty(model.getCompanyname()))
                    shareModel.setContent(getString(R.string.share_content_company) + model.getCompanyname());
                shareModel.setTitle(getString(R.string.share_title_company));
                if (!TextUtils.isEmpty(model.getShareurl()))
                    shareModel.setUrl(model.getShareurl());
                shareUtil.shareByType(activity, shareModel, SHARE_MEDIA.WEIXIN);
            }

            @Override
            public void onSinaShare() {
                if (model == null)
                    return;
                ShareModel shareModel = new ShareModel();
                if (!TextUtils.isEmpty(model.getCompanyname()))
                    shareModel.setContent(getString(R.string.share_content_company) + model.getCompanyname());
                shareModel.setTitle(getString(R.string.share_title_company));
                if (!TextUtils.isEmpty(model.getShareurl()))
                    shareModel.setUrl(model.getShareurl());
                shareUtil.shareByType(activity, shareModel, SHARE_MEDIA.SINA);
            }
        });

        loadingLayout.setVisibility(View.VISIBLE);
        if (sceneAnimation == null)
            sceneAnimation = new SceneAnimation(imageView, 75);
        sceneAnimation.start();
        getCompanyDetail();
        loadingBack.setVGone(View.GONE);

        title.setReportClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model != null) {
                    EventUtils.event(activity, EventUtils.BUSINESSDETAILS01);
                    Intent intent = new Intent(CompanyDetailActivity.this, CompanyAmendActivity.class);
                    intent.putExtra("companyId", mCompanyId);
                    intent.putExtra("companyName", mCompanyName);
                    intent.putExtra("taxid", model.taxid);
                    intent.putExtra("states", model.getStates());
                    intent.putExtra(CompanyAmendActivity.TYPE, CompanyAmendActivity.TYPE_ERROR);
                    startActivity(intent);
                }
            }
        });


        updateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == UPDATE_MSG) {
                    getUpdateState();
                }
            }
        };


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        shareHolderRecycle.setLayoutManager(linearLayoutManager);
        shareHolderRecycle.setAdapter(shareholderAdapter);


        LinearLayoutManager dongshiManager = new LinearLayoutManager(activity);
        dongshiManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dongshiRecycle.setLayoutManager(dongshiManager);
        dongshiRecycle.setAdapter(dongshiAdaper);


        fengxianImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVipUser() && corporateInfoModel != null && corporateInfoModel.companyInfo != null) {
                    if (model != null && !TextUtils.isEmpty(corporateInfoModel.companyInfo.RiskH5Address)) {
                        activity.startActivity(com.gxc.ui.activity.WebActivity.getIntent(activity, "风险分析", corporateInfoModel.companyInfo.RiskH5Address));
                    }
                }
            }
        });

        scrollView.setNestedScrollingEnabled(false);
    }

    /**
     * 是否为VIP用户
     *
     * @return
     */
    private boolean isVipUser() {
        UserModel user = AppUtils.getUser();
        if (user == null) {
            startActivity(LoginActivity.class);
            return false;
        }
        if (user.vipStatus == 1)
            return true;
        new VIPDialog(this).show();
        return false;
    }

    public void showShareDialog() {
        shareDialog.show();
    }

    /**
     * 下载企业详情
     */
    private void getCompanyDetail() {
        TimeOut timeOut = new TimeOut(activity);
        params = new HashMap<>();
        params.put("companyid", mCompanyId);
        params.put("companyname", mCompanyName == null ? "" : mCompanyName);
        params.put("entname", mCompanyName == null ? "" : mCompanyName);

//        if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserid()))
//            params.put("userid", userInfo.getUserid());
//        else {
        params.put("userid", "");
//        }

        params.put("t", timeOut.getParamTimeMollis() + "");
        params.put("m", timeOut.MD5time() + "");
        NetWorkCompanyDetails.getCompanyDetails(activity, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                if (data instanceof CompanyDetailModel) {
                    UserModel userModel = AppUtils.getUser();
                    if (userModel != null && userModel.vipStatus == 1) {
                        updateView((CompanyDetailModel) data);
                    } else {
                        updateView((CompanyDetailModel) data);
                        loadingLayout.setVisibility(View.GONE);
                        sceneAnimation.stop();
                    }
                    getGxcDetailData();
                }
            }

            @Override
            public void onFail(String error) {
                sceneAnimation.stop();
                title.setVGone(View.GONE);
                loadingLayout.setVisibility(View.GONE);
                netWorkError.setNetWorkError();
                netWorkError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getGxcDetailData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("companyid", mCompanyId);
        params.put("companyName", mCompanyName == null ? "" : mCompanyName);
        RxManager.http(RetrofitUtils.getApi().GetCorporateInfo(params), new ResponseCall() {

            @Override
            public void success(NetModel data) {
                corporateInfoModel = data.dataToObject(CorporateInfoModel.class);
                if (corporateInfoModel != null && corporateInfoModel.companyInfo != null) {
                    shareholderAdapter.setNewData(corporateInfoModel.companyInfo.shareholder);
                    dongshiAdaper.setNewData(corporateInfoModel.companyInfo.mainStaff);
                    companyMapView.setData(corporateInfoModel.companyInfo);
                    headerView.setGxcData(corporateInfoModel.companyInfo);
                }
                updateView();
            }

            @Override
            public void error() {
                sceneAnimation.stop();
                title.setVGone(View.GONE);
                loadingLayout.setVisibility(View.GONE);
                netWorkError.setNetWorkError();
                netWorkError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void updateView() {
        gxcLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        sceneAnimation.stop();
        if (corporateInfoModel != null && corporateInfoModel.companyInfo != null) {
            title.setFollow(corporateInfoModel.companyInfo.isCollect == 1);
            navigation.setMonitorText(corporateInfoModel.companyInfo.monitorType == 1);
            navigation.setCreditText(corporateInfoModel.companyInfo.CertificationType == 1);
        }
    }

    private void updateView(final CompanyDetailModel model) {
        this.model = model;


        //企业信息无数据时处理
        if (model.getResult() == 0) {
            if (model.getHasCompanyData() == 1) {
                Intent intent = new Intent(activity, SearchResultActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra(SearchResultActivity.SEARCH_KEY, mCompanyName);
                intent.putExtra(SearchResultActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_COMMON);
                startActivity(intent);
                finish();
                return;
            }
        }


        if (model.getResult() == 0) {
//            title.setTitleAlpha(0);
            title.setVGone(View.VISIBLE);
            if (TextUtils.isEmpty(model.getCompanyid())) {
                //企业ID为空，隐藏分享、打电话、关注
            } else {
                if (model.getCompanyphonelist() == null
                        || model.getCompanyphonelist().size() == 0
                        ) {

                } else {
                    contactinList.clear();
                    for (ContactinFormationModel contactinFormationModel : model.getCompanyphonelist()) {
                        if (!TextUtils.isEmpty(contactinFormationModel.getNumber())) {
                            contactinList.add(contactinFormationModel);
                        }
                    }
                    if (contactinList.size() == 0) {
                    }
                }
            }

            if ("true".equals(model.getIsattention())) {
                title.setFollow(true);
            } else {
                title.setFollow(false);
            }

            headerView.setInfo(model);
            netWorkError.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(model.getUpdatestate()))
                myHeaderView.setTxt(model.getUpdatestate());

            adapter.refresh(model.getSubclassMenu());
            riskInfoVide.setData(CompanyDetailMenuView.TYPE_RISKINFO, model, mCompanyId, mCompanyName);
            operatingConditionsView.setData(CompanyDetailMenuView.TYPE_OPERATINGCONDITIONS, model, mCompanyId, mCompanyName);
            intangibleAssetsView.setData(CompanyDetailMenuView.TYPE_INTANGIBLEASSETS, model, mCompanyId, mCompanyName);

            //查看公司详情后，更新“我的关注”公司更新显示
            HideUpdateEvent event = new HideUpdateEvent();
            if (!TextUtils.isEmpty(model.getCompanyid())) {
                event.setCompanyid(model.getCompanyid());
            } else {
                event.setCompanyid(mCompanyId);
            }

            EventBus.getDefault().post(event);

            UpdateAttentionEvent updateAttentionEvent = new UpdateAttentionEvent();
            if (!TextUtils.isEmpty(model.getCompanyid())) {
                updateAttentionEvent.setCompanyId(model.getCompanyid());
            } else {
                updateAttentionEvent.setCompanyId(mCompanyId);
            }
            updateAttentionEvent.setAttention(model.getAttentioncount());
            EventBus.getDefault().post(updateAttentionEvent);

        } else {
            sceneAnimation.stop();
            netWorkError.setServerError();
            netWorkError.setVisibility(View.VISIBLE);
            title.setVGone(View.GONE);
            Toast.makeText(activity, model.getMsg(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sceneAnimation != null)
            sceneAnimation.stop();
        updateHandler.removeMessages(UPDATE_MSG);


        VolleyUtil.getQueue(this).cancelAll("CompanyDetailsItemHttp");
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof CompleteLoginEvent) {
//            if (((CompleteLoginEvent) event).getIsLogin() == LoginConstant.REGISTER) {
//                userInfo = InquireApplication.getUserInfo();
//                putFollowState(followStatue);
//            }
        } else if (event instanceof LoginChangeEvent) {
            getGxcDetailData();
        }
    }


    public void updateCompanyInfo() {
        if (model == null)
            return;
        if (!TextUtils.isEmpty(model.getUpdatestate()))
            if (model.getUpdatestate().contains("已是最新")) {
                showToast("已是最新数据");
                return;
            }

        myHeaderView.setTxt(getString(R.string.refreshing));
        headerView.setUpdateText(getString(R.string.refreshing));
//        HashMap<String, String> params = new HashMap<>();
//        if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserid())) {
//            params.put("userId", userInfo.getUserid());
//        } else {
        params.put("userId", "");
//        }

        params.put("entid", model.getCompanyid());
        params.put("companyname", mCompanyName == null ? "" : mCompanyName);
        params.put("entname", mCompanyName == null ? "" : mCompanyName);
        NetWorkCompanyDetails.updateCompanyDetails(activity, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                BaseModel baseModel = (BaseModel) data;
                if (baseModel.getResult() == 0) {
                    getUpdateState();
//                    Toast.makeText(activity,"更新成功",Toast.LENGTH_SHORT).show();
//                    headerView.setUpdate();
                } else {
                    showToast(baseModel.getMsg());
                }
            }

            @Override
            public void onFail(String error) {
                showToast(error);
            }
        });
    }

    private void getUpdateState() {

        TimeOut timeOut = new TimeOut(activity);

        HashMap<String, String> params = new HashMap<>();
//        if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserid())) {
//            params.put("userId", userInfo.getUserid());
//        } else {
        params.put("userId", "");
//        }
        params.put("t", timeOut.getParamTimeMollis() + "");
        params.put("m", timeOut.MD5time() + "");
        params.put("entId", model.getCompanyid());
        params.put("entName", mCompanyName == null ? "" : mCompanyName);
        NetWorkCompanyDetails.getCompanyUpdateState(this, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                CompanyDetailModel model = (CompanyDetailModel) data;
                if (model.getResult() == 0) {
                    if ("1".equals(model.getCurrentstate())) {
                        // 更新成功
                        headerView.setUpdate(model);
                        updateHandler.removeMessages(UPDATE_MSG);
                    } else {
                        // 0 正在更新或者未更新
                        updateHandler.removeMessages(UPDATE_MSG);
                        updateHandler.sendEmptyMessageDelayed(UPDATE_MSG, UPDATE_DELAY);
                    }
                }
            }

            @Override
            public void onFail(String error) {
                updateHandler.removeMessages(UPDATE_MSG);
                updateHandler.sendEmptyMessageDelayed(UPDATE_MSG, UPDATE_DELAY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        shareUtil.onActivityResult(requestCode, resultCode, data);
//        presenter.onActivityResult(requestCode, resultCode, data, currentPath);

    }

    /**
     * 收藏、取消收藏
     */
    private void collectHandle() {
        UserModel user = AppUtils.getUser();
        if (user == null) {
            startActivity(com.gxc.ui.activity.LoginActivity.class);
            return;
        }

        if (corporateInfoModel == null || corporateInfoModel.companyInfo == null)
            return;

        final int type = corporateInfoModel.companyInfo.isCollect == 1 ? 0 : 1;

        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("companyid", mCompanyId);
        map.put("companyname", mCompanyName);
        map.put("monitorType", type);

        RxManager.http(RetrofitUtils.getApi().addOrCancelCollection(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    corporateInfoModel.companyInfo.isCollect = type;
                    showToast(type == 1 ? "收藏成功" : "已取消收藏");
                    title.setFollow(type == 1);
                    EventBus.getDefault().post(new CollectChangeEvent());
                } else {
                    showToast(model.msg);
                }
            }

            @Override
            public void error() {
                hideLoadDialog();
                ToastUtils.showHttpError();
            }
        });
    }

    /**
     * 监控处理
     */
    private void monitorHandle() {

        if (!isVipUser()) return;

        if (corporateInfoModel == null || corporateInfoModel.companyInfo == null)
            return;

        final int type = corporateInfoModel.companyInfo.monitorType == 1 ? 0 : 1;

        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("companyid", mCompanyId);
        map.put("companyname", mCompanyName);
        map.put("monitorType", type); // TEST

        RxManager.http(RetrofitUtils.getApi().monitorUpdate(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    corporateInfoModel.companyInfo.monitorType = type;
                    showToast(type == 1 ? "监控成功" : "已取消监控");
                    navigation.setMonitorText(type == 1);
                    EventBus.getDefault().post(new MonitorChangeEvent());
                } else {
                    showToast(model.msg);
                }
            }

            @Override
            public void error() {
                hideLoadDialog();
                ToastUtils.showHttpError();
            }
        });
    }
}
