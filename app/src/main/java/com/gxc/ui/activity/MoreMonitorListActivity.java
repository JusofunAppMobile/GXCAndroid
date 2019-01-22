package com.gxc.ui.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.model.HomeMonitorModel;
import com.gxc.ui.adapter.HomeMonitorAdapter;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 19:51
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class MoreMonitorListActivity extends BaseListActivity {

    @BindView(R.id.titleView)
    TitleView titleView;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new HomeMonitorAdapter();
    }

    @Override
    protected void initUi() {
        titleView.setTitle("监控动态");
    }

    @Override
    protected void startLoadData() {
//        completeLoadData(AppUtils.getTestList(HomeMonitorModel.class, 20));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        HomeMonitorModel model = (HomeMonitorModel) adapter.getItem(position);
        startActivity(MonitorDetailActivity.getIntent(activity, model.companyId, model.companyName));
    }
}
