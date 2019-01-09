package com.gxc.ui.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.model.RiskModel;
import com.gxc.ui.adapter.RiskAdapter;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.CommonSearchTitleView;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 17:35
 * @Email lgd@jusfoun.com
 * @Description ${风险分析}
 */
public class RiskListActivity extends BaseListActivity {

    @BindView(R.id.titleView)
    CommonSearchTitleView titleView;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new RiskAdapter();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        startActivity(RiskTabActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_risk_list;
    }

    @Override
    protected void initUi() {
        titleView.setEditHint("请输入企业名称");
        setStatusBarRed();
    }

    @Override
    public boolean isBarDark() {
        return false;
    }

    @Override
    protected void startLoadData() {
        completeLoadData(AppUtils.getTestList(RiskModel.class, 20));
    }
}
