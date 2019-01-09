package com.gxc.ui.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.model.HomeMonitorModel;
import com.gxc.model.HomeNewsModel;
import com.gxc.ui.adapter.HomeNewsAdapter;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 19:55
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class MoreNewsListActivity extends BaseListActivity {

    @BindView(R.id.titleView)
    TitleView titleView;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new HomeNewsAdapter(this);
    }

    @Override
    protected void initUi() {
        titleView.setTitle("行业资讯");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        startActivity(WebActivity.getIntent(activity, "行业资讯", AppUtils.TEST_URL));
    }

    @Override
    protected void startLoadData() {
        completeLoadData(AppUtils.getTestList(HomeNewsModel.class, 20));
    }
}
