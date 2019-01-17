package com.gxc.ui.activity;

import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.utils.AppUtils;
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
    @BindView(R.id.tvVersion)
    TextView tvVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.act_about;
    }

    @Override
    public void initActions() {
        titleView.setTitle("关于我们");
        tvVersion.setText("版本号：" + AppUtils.getVersionName(this));
    }
}
