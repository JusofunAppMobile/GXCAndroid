package com.gxc.ui.activity;

import com.gxc.base.BaseActivity;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/14/014 10:00
 * @Description ${}
 */
public class PushSetActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;

    @Override
    protected int getLayoutId() {
        return R.layout.act_push_set;
    }

    @Override
    public void initActions() {
        titleView.setTitle("消息推送");
    }
}
