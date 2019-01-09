package com.gxc.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.ui.view.EditReportInfoItemView;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/516:53
 * @Email zyp@jusfoun.com
 * @Description ${自主填报信息}
 */
public class ReportInfoActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleView titlebar;
    @BindView(R.id.text_company)
    TextView textCompany;
    @BindView(R.id.view_info)
    EditReportInfoItemView viewInfo;
    @BindView(R.id.view_product)
    EditReportInfoItemView viewProduct;
    @BindView(R.id.view_rongyu)
    EditReportInfoItemView viewRongyu;
    @BindView(R.id.view_hezuohuoban)
    EditReportInfoItemView viewHezuohuoban;
    @BindView(R.id.view_chengyuan)
    EditReportInfoItemView viewChengyuan;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_info;
    }

    @Override
    public void initActions() {
        setStatusBarEnable(ContextCompat.getColor(this, R.color.white));
        titlebar.setTitle("自主填报信息");
        textCompany.setText("大洋科技");
        textCompany.setTypeface(Typeface.DEFAULT_BOLD);

        viewInfo.setData(EditReportInfoActivity.TYPE_INFO);
        viewProduct.setData(EditReportInfoActivity.TYPE_PRODUCE);
        viewRongyu.setData(EditReportInfoActivity.TYPE_RY);
        viewHezuohuoban.setData(EditReportInfoActivity.TYPE_HB);
        viewChengyuan.setData(EditReportInfoActivity.TYPE_CY);


    }

    @Override
    public boolean isBarDark() {
        return super.isBarDark();
    }

}
