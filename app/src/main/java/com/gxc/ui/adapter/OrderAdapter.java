package com.gxc.ui.adapter;

import android.support.constraint.Group;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.OrderModel;
import com.siccredit.guoxin.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 9:37
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class OrderAdapter extends BaseQuickAdapter<OrderModel, BaseViewHolder> {

    public OrderAdapter() {
        super(R.layout.item_order);
    }

    @Override
    protected void convert(BaseViewHolder holder, OrderModel model) {
        TextView tvTitle = holder.getView(R.id.tvTitle);
        TextView tvStatus = holder.getView(R.id.tvStatus);
        TextView tvLabel1 = holder.getView(R.id.tvLabel1);
        TextView tvLabel2 = holder.getView(R.id.tvLabel2);
        TextView tvLabel3 = holder.getView(R.id.tvLabel3);
        TextView tvLabel4 = holder.getView(R.id.tvLabel4);
        TextView tvValue1 = holder.getView(R.id.tvValue1);
        TextView tvValue2 = holder.getView(R.id.tvValue2);
        TextView tvValue3 = holder.getView(R.id.tvValue3);
        TextView tvValue4 = holder.getView(R.id.tvValue4);
        TextView tvMoney = holder.getView(R.id.tvMoney);
        TextView tvSend = holder.getView(R.id.tvSend);
        TextView tvCheck = holder.getView(R.id.tvCheck);
        TextView tvCompany = holder.getView(R.id.tvCompany);
        Group group = holder.getView(R.id.group);

        tvTitle.setText(model.title);

        tvCompany.setText(model.name);

        tvValue1.setText(model.no);
        tvValue2.setText(model.time);
        tvMoney.setText("¥" + model.money);

        if (model.type == 0) { // 企业报告

            tvLabel3.setText("接收邮箱：");
            tvLabel4.setText("报告格式：");

            // 企业报告生成状态 0：未生成 1：已生成
            tvStatus.setText(model.status ==  1 ? "已生成":"未生成");

            tvLabel3.setText(model.email);
            tvLabel3.setText(model.format);

            group.setVisibility(View.VISIBLE);
            tvCompany.setVisibility(View.VISIBLE);
        } else { // 会员服务

            tvLabel3.setText("服务时长：");
            tvValue3.setText(model.duration);

            group.setVisibility(View.GONE);
            tvCompany.setVisibility(View.GONE);
            tvStatus.setText(model.orderState);
        }
    }

}
