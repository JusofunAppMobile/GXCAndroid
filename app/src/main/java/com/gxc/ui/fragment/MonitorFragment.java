package com.gxc.ui.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListFragment;
import com.gxc.model.HomeMonitorModel;
import com.gxc.ui.activity.MonitorCompanyListActivity;
import com.gxc.ui.adapter.HomeMonitorAdapter;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;

import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/4/004 19:39
 * @Email lgd@jusfoun.com
 * @Description ${监控动态}
 */
public class MonitorFragment extends BaseListFragment {

    @Override
    public int getLayoutId() {
        return R.layout.frag_monitor;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new HomeMonitorAdapter();
    }

    @Override
    protected void initUi() {

    }

    @OnClick(R.id.vMonitorCompany)
    public void showCompany() {
        startActivity(MonitorCompanyListActivity.class);
    }

    @Override
    protected void startLoadData() {
        completeLoadData(AppUtils.getTestList(HomeMonitorModel.class, 20));
    }

}
