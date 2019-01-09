package com.gxc.ui.adapter;

import android.support.constraint.Group;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.OrderModel;
import com.jusfoun.jusfouninquire.R;

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
    protected void convert(BaseViewHolder holder, OrderModel orderModel) {
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
        Group group = holder.getView(R.id.group);

        if (holder.getLayoutPosition() % 2 == 0) {
            tvTitle.setText("企业信用服务报告-专业版");
            tvStatus.setText("已生成");

            tvLabel3.setText("接收邮箱：");
            tvLabel4.setText("报告格式：");

            group.setVisibility(View.VISIBLE);
        }else{
            tvTitle.setText("VIP会员服务");
            tvStatus.setText("已支付");

            tvLabel3.setText("服务时长：");

            group.setVisibility(View.GONE);
        }
    }

}
