package com.gxc.ui.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListFragment;
import com.gxc.ui.adapter.RiskTabAdapter;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 19:21
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class RiskListFragment extends BaseListFragment {

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new RiskTabAdapter();
    }

    @Override
    protected void initUi() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
    }

    @Override
    protected void startLoadData() {
//        completeLoadData(AppUtils.getTestList(RiskTabModel.class, 20));
    }
}
