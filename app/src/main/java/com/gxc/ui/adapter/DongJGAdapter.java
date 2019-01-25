package com.gxc.ui.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.CorporateInfoModel;
import com.jusfoun.jusfouninquire.R;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/808:51
 * @Email zyp@jusfoun.com
 * @Description ${企业详情 股东列表adapter}
 */
public class DongJGAdapter extends BaseQuickAdapter<CorporateInfoModel.MainStaffItemModel, BaseViewHolder> {

    public DongJGAdapter() {
        super(R.layout.item_shareholder);
    }

    @Override
    protected void convert(BaseViewHolder holder, CorporateInfoModel.MainStaffItemModel riskModel) {
        TextView imgText = holder.getView(R.id.text_img);
        TextView nameText = holder.getView(R.id.text_name);
        TextView biliText = holder.getView(R.id.text_libi);
        TextView statusText = holder.getView(R.id.tvCompany);

        if (!TextUtils.isEmpty(riskModel.name)) {
            imgText.setText(riskModel.name.substring(0,1));
        } else {
            imgText.setText("");
        }
        if (!TextUtils.isEmpty(riskModel.name)) {
            nameText.setText(riskModel.name);
        } else {
            nameText.setText("");
        }

        biliText.setVisibility(View.INVISIBLE);

        statusText.setText(riskModel.job);
        statusText.setTextColor(Color.parseColor("#333333"));
    }

}
