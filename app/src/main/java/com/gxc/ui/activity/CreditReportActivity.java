package com.gxc.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.model.CreditReportModel;
import com.gxc.ui.adapter.CreditReportAdapter;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/718:43
 * @Email zyp@jusfoun.com
 * @Description ${信用服务-信用报告页面}
 */
public class CreditReportActivity extends BaseListActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.text_title)
    TextView textTitle;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new CreditReportAdapter();
    }

    @Override
    protected void initUi() {
        titleView.setTitle("信用报告");
        textTitle.setText(Html.fromHtml("国信查支持<font  color=\"#df282d\">发票开取</font>，你可以在购买报告后开取发票"));
    }

    @Override
    protected void startLoadData() {
        completeLoadData(AppUtils.getTestList(CreditReportModel.class, 2));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_credit_report;
    }

}
