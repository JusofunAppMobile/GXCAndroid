package com.gxc.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1117:54
 * @Email zyp@jusfoun.com
 * @Description ${确认订单}
 */
public class ConfirmOrderActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleView titlebar;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_company)
    TextView textCompany;
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.text_pdf)
    TextView textPdf;
    @BindView(R.id.text_word)
    TextView textWord;
    @BindView(R.id.text_money)
    TextView textMoney;
    @BindView(R.id.text_finish)
    TextView textSend;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initActions() {
        titlebar.setTitle("确认订单");
    }

    @OnClick({R.id.text_pdf, R.id.text_word, R.id.text_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_pdf:
                break;
            case R.id.text_word:
                break;
            case R.id.text_finish:
                startActivity(ReportSubmitActivity.class);
                break;
        }
    }
}
