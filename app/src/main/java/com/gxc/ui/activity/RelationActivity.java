package com.gxc.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 16:11
 * @Email lgd@jusfoun.com
 * @Description ${查关系}
 */
public class RelationActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.tvFirst)
    TextView tvFirst;
    @BindView(R.id.tvSecond)
    TextView tvSecond;

    @Override
    protected int getLayoutId() {
        return R.layout.act_relation;
    }

    @Override
    public void initActions() {
        titleView.setTitle("查关系");
    }


    @OnClick({R.id.tvFirst, R.id.tvSecond})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvFirst:
                startActivity(CompanySearchListActivity.class);
                break;
            case R.id.tvSecond:
                startActivity(CompanySearchListActivity.class);
                break;
        }
    }
}
