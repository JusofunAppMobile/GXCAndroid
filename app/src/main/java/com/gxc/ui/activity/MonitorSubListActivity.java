package com.gxc.ui.activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.model.MonitorDetailModel;
import com.gxc.ui.adapter.MonitorDetailAdapter;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/11/011 16:48
 * @Description ${}
 */
public class MonitorSubListActivity extends BaseListActivity {

    @BindView(R.id.titleView)
    TitleView titleView;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MonitorDetailAdapter(this);
    }

    @Override
    protected void initUi() {
        titleView.setTitle("动态详情");
    }

    @Override
    protected void startLoadData() {
        completeLoadData(AppUtils.getTestList(MonitorDetailModel.class, 20));
    }
}
