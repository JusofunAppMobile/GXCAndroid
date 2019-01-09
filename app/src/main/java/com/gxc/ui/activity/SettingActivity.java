package com.gxc.ui.activity;

import com.gxc.base.BaseActivity;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 19:18
 * @Email lgd@jusfoun.com
 * @Description ${设置}
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;

    @Override
    protected int getLayoutId() {
        return R.layout.act_setting;
    }

    @Override
    public void initActions() {
        titleView.setTitle("设置");
    }
}
