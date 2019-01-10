package com.gxc.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.MonitorModel;
import com.jusfoun.jusfouninquire.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 11:43
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class MonitorAdpater extends BaseQuickAdapter<MonitorModel, BaseViewHolder> {

    public MonitorAdpater() {
        super(R.layout.item_monitor);
    }

    @Override
    protected void convert(BaseViewHolder holder, MonitorModel monitorModel) {
        TextView tvStatus = holder.getView(R.id.tvStatus);
        if (holder.getLayoutPosition() % 2 == 0) {
            tvStatus.setSelected(true);
            tvStatus.setText("监控");
        } else {
            tvStatus.setSelected(false);
            tvStatus.setText("取消监控");
        }
    }
}
