package com.gxc.ui.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.CreditReportModel;
import com.gxc.ui.activity.ConfirmOrderActivity;
import com.gxc.ui.activity.WebActivity;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/808:51
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class CreditReportAdapter extends BaseQuickAdapter<CreditReportModel.CreditReportItemModel, BaseViewHolder> {
    public CreditReportAdapter() {
        super(R.layout.item_credit_report);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CreditReportModel.CreditReportItemModel item) {
        TextView titleText = helper.getView(R.id.text_title);
        TextView desText = helper.getView(R.id.text_des);
        TextView contenteText = helper.getView(R.id.text_content);
        TextView previewText = helper.getView(R.id.textView25);
        TextView reportText = helper.getView(R.id.text_report);
        if (helper.getAdapterPosition() == 0) {
            titleText.setText(Html.fromHtml("企业信用报告-<font  color=\"#fca249\">标准版</font>"));
            desText.setText(Html.fromHtml("今天剩余<font  color=\"#fca249\">"+item.basicVersionDownloadNum+"</font>" + "次下载机会"));
            contenteText.setTextColor(Color.parseColor("#666666"));
            contenteText.setText(mContext.getString(R.string.text_credit_report_biaozhun));
        } else if (helper.getAdapterPosition() == 1) {
            titleText.setText(Html.fromHtml("企业信用报告-<font  color=\"#fca249\">专业版</font>"));
            desText.setText(Html.fromHtml("包括<font  color=\"#fca249\">基础版企业信用报告</font>" + "所有内容，以及："));
            contenteText.setText(mContext.getString(R.string.text_credit_report_zhuanye));
            contenteText.setTextColor(Color.parseColor("#fca249"));
        }
        previewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper.getAdapterPosition() == 0) {
                    mContext.startActivity(WebActivity.getIntent(mContext, "样例预览",item.basicVersionSamplePreview));
                }else{
                    mContext.startActivity(WebActivity.getIntent(mContext, "样例预览",item.professionVersionSamplePreview));
                }

            }
        });

        reportText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,ConfirmOrderActivity.class));
            }
        });


    }
}
