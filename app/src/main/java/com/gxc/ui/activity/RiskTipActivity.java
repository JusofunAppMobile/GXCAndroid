package com.gxc.ui.activity;

import com.gxc.base.BaseActivity;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 17:42
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class RiskTipActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;

    @Override
    protected int getLayoutId() {
        return R.layout.act_risk_tip;
    }

    @Override
    public void initActions() {
        titleView.setTitle("企业风险分析");
    }
}
