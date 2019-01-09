package com.gxc.ui.activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.ui.adapter.MonitorCompanyAdapter;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 14:10
 * @Email lgd@jusfoun.com
 * @Description ${我的监控、我的收藏}
 */
public class MonitorCompanyListActivity extends BaseListActivity {

    @BindView(R.id.titleView)
    TitleView titleView;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MonitorCompanyAdapter();
    }

    @Override
    protected void initUi() {
        titleView.setTitle("我的监控");
    }

    @Override
    protected void startLoadData() {
        completeLoadData(AppUtils.getTestList(String.class, 20));
    }
}
