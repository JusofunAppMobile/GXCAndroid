package com.gxc.ui.activity;

import com.gxc.base.BaseActivity;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/16/016 15:35
 * @Description ${关于}
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;

    @Override
    protected int getLayoutId() {
        return R.layout.act_about;
    }

    @Override
    public void initActions() {
        titleView.setTitle("关于我们");
    }
}
