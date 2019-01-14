package com.gxc.ui.activity;

import android.view.View;

import com.gxc.base.BaseActivity;
import com.gxc.ui.widgets.ItemView;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 19:18
 * @Email lgd@jusfoun.com
 * @Description ${设置}
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.vCache)
    ItemView vCache;

    @Override
    protected int getLayoutId() {
        return R.layout.act_setting;
    }

    @Override
    public void initActions() {
        titleView.setTitle("设置");
    }

    @OnClick({R.id.vPush, R.id.vCache, R.id.vService, R.id.vSecret, R.id.vAbout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vPush:
                startActivity(PushSetActivity.class);
                break;
            case R.id.vCache:
                break;
            case R.id.vService:
                startActivity(WebActivity.getIntent(this, "服务协议", AppUtils.TEST_URL, false));
                break;
            case R.id.vSecret:
                startActivity(WebActivity.getIntent(this, "隐私政策", AppUtils.TEST_URL, false));
                break;
            case R.id.vAbout:
                startActivity(WebActivity.getIntent(this, "关于我们", AppUtils.TEST_URL, false));
                break;
        }
    }
}
