package com.gxc.ui.fragment;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseFragment;
import com.gxc.model.HomeMenuModel;
import com.gxc.ui.activity.CertifiedCompanyActivity;
import com.gxc.ui.activity.InforActivity;
import com.gxc.ui.activity.LoginActivity;
import com.gxc.ui.activity.MonitorCompanyListActivity;
import com.gxc.ui.activity.OrderListActivity;
import com.gxc.ui.activity.PayActivity;
import com.gxc.ui.activity.SettingActivity;
import com.gxc.ui.activity.WebActivity;
import com.gxc.ui.adapter.HomeMenuAdapter;
import com.gxc.ui.dialog.AuthDialog;
import com.gxc.ui.dialog.VIPDialog;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;

import java.util.ArrayList;
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

    @Override
    protected int getLayoutId() {
        return R.layout.frag_my;
    }

    @Override
    protected void initView() {
        homeMenuAdapter = new HomeMenuAdapter();
        List<HomeMenuModel> list = new ArrayList<>();
        list.add(new HomeMenuModel(R.drawable.mine_icon_dingdan, "我的订单"));
        list.add(new HomeMenuModel(R.drawable.mine_icon_jiankong, "我的监控"));
        list.add(new HomeMenuModel(R.drawable.mine_icon_shoucang, "我的收藏"));
        list.add(new HomeMenuModel(R.drawable.mine_icon_renzheng, "企业认证"));
        list.add(new HomeMenuModel(R.drawable.mine_icon_vip, "VIP特权"));
        list.add(new HomeMenuModel(R.drawable.mine_icon_zengsong, "赠送好友VIP"));
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
        switch (position) {
            case 0:// 我的订单
                startActivity(OrderListActivity.class);
                break;
            case 1:// 我的监控
                startActivity(MonitorCompanyListActivity.class);
                break;
            case 2:// 我的收藏
                startActivity(MonitorCompanyListActivity.class);
                break;
            case 3:// 企业认证
                new AuthDialog(activity).show();
                startActivity(CertifiedCompanyActivity.class);
                break;
            case 4:// VIP特权
                new VIPDialog(activity).show();
                startActivity(WebActivity.getIntent(activity, "VIP特权", AppUtils.TEST_URL));
                break;
            case 5:// 赠送好友VIP
                startActivity(PayActivity.class);
                break;
        }
    }

    @OnClick({R.id.vHistory, R.id.vHelper, R.id.vVersion, R.id.vSetting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vHistory: // 浏览历史
                break;
            case R.id.vHelper: // 使用帮助
                startActivity(WebActivity.getIntent(activity, "使用帮助", AppUtils.TEST_URL));
                break;
            case R.id.vVersion: // 检测更新
                break;
            case R.id.vSetting: // 设置
                startActivity(SettingActivity.class);
                break;
        }
    }

    @OnClick(R.id.vLogin)
    public void vLogin() {
        startActivity(LoginActivity.class);
    }

    @OnClick(R.id.vNormal)
    public void vNormal() {
        startActivity(InforActivity.class);
    }
}
