package com.gxc.ui.activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.model.VisitorModel;
import com.gxc.ui.adapter.VisitorAdapter;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;

import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/10/010 9:14
 * @Description ${访客}
 */
public class VisitorListActivity extends BaseListActivity {

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new VisitorAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_visitor;
    }

    @Override
    protected void initUi() {

    }

    @Override
    public boolean isBarDark() {
        return false;
    }

    @Override
    public boolean isSetStatusBar() {
        return false;
    }

    @OnClick(R.id.ivLeft)
    public void click() {
        finish();
    }

    @Override
    protected void startLoadData() {
        completeLoadData(AppUtils.getTestList(VisitorModel.class, 20));
    }
}
