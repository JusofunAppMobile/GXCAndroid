package com.gxc.ui.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListFragment;
import com.gxc.model.MonitorModel;
import com.gxc.ui.activity.MonitorDetailActivity;
import com.gxc.ui.adapter.MonitorAdpater;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/4/004 19:39
 * @Email lgd@jusfoun.com
 * @Description ${监控动态}
 */
public class MonitorListFragment extends BaseListFragment {

    @Override
    public int getLayoutId() {
        return R.layout.frag_monitor;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new MonitorAdpater();
    }

    @Override
    protected void initUi() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        startActivity(MonitorDetailActivity.class);
    }

    @Override
    protected void startLoadData() {
        completeLoadData(AppUtils.getTestList(MonitorModel.class, 20));
    }

}
