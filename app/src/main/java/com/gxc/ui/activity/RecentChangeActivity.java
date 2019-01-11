package com.gxc.ui.activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseActivity;
import com.gxc.base.BaseListActivity;
import com.gxc.ui.adapter.CreditReportAdapter;
import com.gxc.ui.adapter.RecentChangeAdapter;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1117:32
 * @Email zyp@jusfoun.com
 * @Description ${近期变更}
 */
public class RecentChangeActivity extends BaseListActivity {
    @Override
    protected BaseQuickAdapter getAdapter() {
        return new RecentChangeAdapter();
    }

    @Override
    protected void initUi() {

    }

    @Override
    protected void startLoadData() {

    }
}
