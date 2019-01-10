package com.gxc.ui.activity;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.event.UpdateReoprtInfoEvent;
import com.gxc.ui.view.EditReportInfoItemView;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;

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

        viewInfo.setData(EditReportInfoActivity.TYPE_INFO, null);
        viewProduct.setData(EditReportInfoActivity.TYPE_PRODUCE, null);
        viewRongyu.setData(EditReportInfoActivity.TYPE_RY, null);
        viewHezuohuoban.setData(EditReportInfoActivity.TYPE_HB, null);
        viewChengyuan.setData(EditReportInfoActivity.TYPE_CY, null);


    }

    @Override
    public boolean isBarDark() {
        return super.isBarDark();
    }


    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);

        Log.e("tag","onEventonEvent1");
        if (event instanceof UpdateReoprtInfoEvent) {
            if (((UpdateReoprtInfoEvent) event).type == EditReportInfoActivity.TYPE_INFO) {
                Log.e("tag","onEventonEvent2");
                viewInfo.setData(EditReportInfoActivity.TYPE_INFO, ((UpdateReoprtInfoEvent) event).editReportInfoTextModel);
            } else if (((UpdateReoprtInfoEvent) event).type == EditReportInfoActivity.TYPE_PRODUCE) {
                Log.e("tag","onEventonEvent3");
                viewProduct.setData(EditReportInfoActivity.TYPE_PRODUCE, ((UpdateReoprtInfoEvent) event).editReportInfoTextModel);
            }

        }
    }
}
