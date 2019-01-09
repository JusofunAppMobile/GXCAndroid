package com.gxc.ui.activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.model.OrderModel;
import com.gxc.ui.adapter.OrderAdapter;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 9:36
 * @Email lgd@jusfoun.com
 * @Description ${我的订单}
 */
public class OrderListActivity extends BaseListActivity {

    @BindView(R.id.titleView)
    TitleView titleView;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new OrderAdapter();
    }

    @Override
    protected void initUi() {
        titleView.setTitle("我的订单");
    }

    @Override
    protected void startLoadData() {
        completeLoadData(AppUtils.getTestList(OrderModel.class, 20));
    }
}
