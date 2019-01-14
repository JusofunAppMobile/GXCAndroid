package com.gxc.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1410:05
 * @Email zyp@jusfoun.com
 * @Description ${报告提交成功}
 */
public class ReportSubmitActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleView titlebar;
    @BindView(R.id.text_finish)
    TextView textSend;
    @BindView(R.id.text_look)
    TextView textLook;

    @Override
    protected int getLayoutId() {
        return R.layout.activit_report_submit;
    }

    @Override
    public void initActions() {
        titlebar.setTitle("提交成功");
    }

    @OnClick({R.id.text_finish, R.id.text_look})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_finish:
                finish();
                break;
            case R.id.text_look:
                break;
        }
    }
}
