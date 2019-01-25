package com.gxc.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.model.CreditReportModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.util.RegularUtils;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1117:54
 * @Email zyp@jusfoun.com
 * @Description ${获取报告}
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
//    @BindView(R.id.text_money)
//    TextView textMoney;
    @BindView(R.id.text_finish)
    TextView textSend;

    private CreditReportModel.CreditReportItemModel creditReportItemModel;
    private int type;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initActions() {
        titlebar.setTitle("获取报告");
        creditReportItemModel =(CreditReportModel.CreditReportItemModel)getIntent().getSerializableExtra("model");

        type = getIntent().getIntExtra("type",0);
        if (type == 0) {
            textTitle.setText(Html.fromHtml("企业信用报告-<font  color=\"#fca249\">标准版</font>"));
        } else if (type == 1) {
            textTitle.setText(Html.fromHtml("企业信用报告-<font  color=\"#fca249\">专业版</font>"));
        }

        textCompany.setText(creditReportItemModel.companyName);

    }

    @OnClick({R.id.text_pdf, R.id.text_word, R.id.text_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_pdf:
                textPdf.setBackgroundResource(R.drawable.bg_xinyongbaogao_selecter);
                textPdf.setTextColor(Color.parseColor("#df282d"));

                textWord.setBackgroundResource(R.drawable.bg_xinyongbaogao_normal);
                textWord.setTextColor(Color.parseColor("#333333"));
                break;
            case R.id.text_word:

                textWord.setBackgroundResource(R.drawable.bg_xinyongbaogao_selecter);
                textWord.setTextColor(Color.parseColor("#df282d"));

                textPdf.setBackgroundResource(R.drawable.bg_xinyongbaogao_normal);
                textPdf.setTextColor(Color.parseColor("#333333"));
                break;
            case R.id.text_finish:
                send();
                break;
        }
    }
    private void send() {

        if(TextUtils.isEmpty(editEmail.getText().toString())){
            ToastUtils.show("请输入邮箱");
            return;
        }


        if (!RegularUtils.checkEmail(editEmail.getText().toString())) {
            ToastUtils.show("请输入正确的邮箱");
            return;
        }


        showLoading();
        HashMap<String, Object> map = new HashMap<>();
//        map.put("companyId", creditReportItemModel.companyId);
        map.put("companyName", creditReportItemModel.companyName);
        if (type == 0) {
            map.put("type", "1");
        }else{
            map.put("type", "2");
        }
        map.put("url", editEmail.getText().toString());
//        map.put("OStype", "1");

        RxManager.http(RetrofitUtils.getApi().createCreditWord(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("message", model.dataToObject("message", String.class));
                    startActivity(ReportSubmitActivity.class, bundle);
                    finishDelay();
                } else {
                    ToastUtils.show(model.msg);
                }
            }

            @Override
            public void error() {
                hideLoadDialog();
                ToastUtils.showHttpError();
            }
        });

    }
}
