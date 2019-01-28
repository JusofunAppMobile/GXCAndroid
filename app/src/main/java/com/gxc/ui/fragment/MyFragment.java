package com.gxc.ui.fragment;

import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseFragment;
import com.gxc.inter.OnCallListener;
import com.gxc.model.GlideApp;
import com.gxc.model.HomeMenuModel;
import com.gxc.model.UserModel;
import com.gxc.model.VersionModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.activity.CertifiedCompanyActivity;
import com.gxc.ui.activity.InforActivity;
import com.gxc.ui.activity.LoginActivity;
import com.gxc.ui.activity.MyCollectListActivity;
import com.gxc.ui.activity.MyHistoryListActivity;
import com.gxc.ui.activity.MyMonitorListActivity;
import com.gxc.ui.activity.OrderListActivity;
import com.gxc.ui.activity.PayActivity;
import com.gxc.ui.activity.SettingActivity;
import com.gxc.ui.activity.WebActivity;
import com.gxc.ui.adapter.HomeMenuAdapter;
import com.gxc.ui.dialog.VersionDialog;
import com.gxc.utils.AppUtils;
import com.gxc.utils.HtmlUrlUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/4/004 19:39
 * @Email lgd@jusfoun.com
 * @Description ${我的}
 */
public class MyFragment extends BaseFragment {

    @BindView(R.id.menuRecycler)
    RecyclerView menuRecycler;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    HomeMenuAdapter homeMenuAdapter;
    @BindView(R.id.vLogin)
    ConstraintLayout vLogin;
    @BindView(R.id.vNormal)
    ConstraintLayout vNormal;
    @BindView(R.id.vCompany)
    ConstraintLayout vCompany;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.ivNormalLogo)
    ImageView ivNormalLogo;
    @BindView(R.id.ivCompanyLogo)
    ImageView ivCompanyLogo;
    @BindView(R.id.vVip)
    ImageView vVip;
    @BindView(R.id.tvNormalTip1)
    TextView tvNormalTip1;
    @BindView(R.id.tvNormalTip2)
    TextView tvNormalTip2;
    @BindView(R.id.tvCompany)
    TextView tvCompany;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.ivNormalVip)
    ImageView ivNormalVip;
    @BindView(R.id.ivCompanyVip)
    ImageView ivCompanyVip;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_my;
    }

    @Override
    protected void initView() {
        homeMenuAdapter = new HomeMenuAdapter();
        List<HomeMenuModel> list = new ArrayList<>();
        list.add(new HomeMenuModel(R.drawable.mine_icon_dingdan, "我的订单", -1001));
        list.add(new HomeMenuModel(R.drawable.mine_icon_jiankong, "我的监控", -1001));
        list.add(new HomeMenuModel(R.drawable.mine_icon_shoucang, "我的收藏", -1001));
        list.add(new HomeMenuModel(R.drawable.mine_icon_renzheng, "企业认证", -1001));
        list.add(new HomeMenuModel(R.drawable.mine_icon_vip, "VIP特权", -1001));
//        list.add(new HomeMenuModel(R.drawable.mine_icon_zengsong, "赠送好友VIP"));
        homeMenuAdapter.setNewData(list);
        menuRecycler.setAdapter(homeMenuAdapter);
        menuRecycler.setLayoutManager(new GridLayoutManager(activity, 4));
        homeMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                menuItemClick(i);
            }
        });

        scrollView.setNestedScrollingEnabled(false);
    }

    private void menuItemClick(int position) {
        UserModel user = AppUtils.getUser();
        switch (position) {
            case 0:// 我的订单
                if (user == null) {
                    startActivity(LoginActivity.class);
                    return;
                }
                startActivity(OrderListActivity.class);
                break;
            case 1:// 我的监控
                if (user == null) {
                    startActivity(LoginActivity.class);
                    return;
                }
                startActivity(MyMonitorListActivity.class);
                break;
            case 2:// 我的收藏
                if (user == null) {
                    startActivity(LoginActivity.class);
                    return;
                }
                startActivity(MyCollectListActivity.class);
                break;
            case 3:// 企业认证
                if (user == null) {
                    startActivity(LoginActivity.class);
                    return;
                }
                startActivity(CertifiedCompanyActivity.class);
                break;
            case 4:// VIP特权
                if (user == null) {
                    startActivity(LoginActivity.class);
                    return;
                }
                startActivity(WebActivity.getIntent(activity, 0));
                break;
//            case 5:// 赠送好友VIP
//                startActivity(PayActivity.class);
//                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUser();
        AppUtils.checkUserStatus(new OnCallListener() {
            @Override
            public void call() {
                loadUser();
            }
        });
    }

    private void loadUser() {
        UserModel user = AppUtils.getUser();

        if (user == null) {
            vCompany.setVisibility(View.GONE);
            vNormal.setVisibility(View.GONE);
            vLogin.setVisibility(View.VISIBLE);
        } else {
            vCompany.setVisibility(View.VISIBLE);
            vNormal.setVisibility(View.VISIBLE);
            vLogin.setVisibility(View.GONE);

            tvPhone.setText(user.phone);

            RequestOptions options = RequestOptions.bitmapTransform(new CircleCrop())
                    .placeholder(R.drawable.me_head_default_loggedin)
                    .error(R.drawable.me_head_default_loggedin);
            GlideApp.with(InquireApplication.application).load(user.headIcon).apply(options).into(ivNormalLogo);
            GlideApp.with(InquireApplication.application).load(user.headIcon).apply(options).into(ivCompanyLogo);

            // 用户vip状态 0：普通用户 1：vip用户
            ivNormalVip.setVisibility(user.vipStatus == 1 ? View.VISIBLE : View.GONE);
            ivCompanyVip.setVisibility(user.vipStatus == 1 ? View.VISIBLE : View.GONE);
            vVip.setVisibility(user.vipStatus == 0 ? View.VISIBLE : View.GONE);
            tvNormalTip1.setVisibility(user.vipStatus == 0 ? View.VISIBLE : View.GONE);
            tvNormalTip2.setVisibility(user.vipStatus == 0 ? View.VISIBLE : View.GONE);
            tvStatus.setText(getStatus(user.authStatus));

            if (user.authStatus == 3 || user.authStatus == 1)
                tvCompany.setText(user.authCompany);
            else
                tvCompany.setText(user.phone);

            // 0：未认证1：审核中 2：审核失败 3：审核成功
            if (user.authStatus == 0) {
                vCompany.setVisibility(View.GONE);
            } else {
                vNormal.setVisibility(View.GONE);
            }
        }
    }

    private String getStatus(int status) {
        // 0：未认证1：审核中 2：审核失败 3：审核成功
        if (status == 1)
            return "审核中";
        if (status == 2)
            return "认证失败";
        if (status == 3)
            return "已认证";
        return "未认证";
    }

    @OnClick({R.id.vHistory, R.id.vHelper, R.id.vVersion, R.id.vSetting, R.id.vVip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vHistory: // 浏览历史
                UserModel user = AppUtils.getUser();
                if (user == null) {
                    startActivity(LoginActivity.class);
                    return;
                }
                startActivity(MyHistoryListActivity.class);
                break;
            case R.id.vHelper: // 使用帮助
                startActivity(WebActivity.getIntent(activity, "使用帮助", HtmlUrlUtils.getHelpUrl()));
                break;
            case R.id.vVersion: // 检测更新
                checkUpdate();
                break;
            case R.id.vSetting: // 设置
                startActivity(SettingActivity.class);
                break;
            case R.id.vVip: // VIP
                startActivity(PayActivity.class);
                break;
        }
    }

    private void checkUpdate() {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("versionname", AppUtils.getVersionName(activity));
        map.put("versioncode", AppUtils.getVersionCode(activity));
        map.put("channel", AppUtils.getChannel());
        map.put("from", "Android");
        RxManager.http(RetrofitUtils.getApi().checkUpdate(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    VersionModel versionModel = model.dataToObject(VersionModel.class);
                    if (versionModel != null) {
                        new VersionDialog(activity, versionModel).show();
                        return;
                    }
                }
                showToast("当前已是最新版本");
            }

            @Override
            public void error() {
                hideLoadDialog();
                ToastUtils.showHttpError();
            }
        });
    }

    @OnClick(R.id.vLogin)
    public void vLogin() {
        startActivity(LoginActivity.class);
    }

    @OnClick({R.id.vNormal, R.id.vCompany})
    public void vNormal() {
        startActivity(InforActivity.class);
    }
}
