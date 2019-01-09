//package com.gxc.ui.adapter;
//
//import android.support.annotation.Nullable;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.gxc.model.MonitorModel;
//import com.jusfoun.jusfouninquire.R;
//
//import java.util.List;
//
///**
// * @author liuguangdan
// * @version create at 2019/1/7/007 11:43
// * @Email lgd@jusfoun.com
// * @Description ${}
// */
//public class MonitorAdpater extends BaseQuickAdapter<MonitorModel, BaseViewHolder> {
//
//    public MonitorAdpater() {
//        super(R.layout.item_monitor);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder holder, MonitorModel monitorModel) {
//        ImageView ivMonitorStatus = holder.getView(R.id.ivMonitorStatus);
//        TextView tvMonitorStatus = holder.getView(R.id.tvMonitorStatus);
//
//        if (holder.getLayoutPosition() % 2 == 0) {
//            ivMonitorStatus.setSelected(true);
//            tvMonitorStatus.setSelected(true);
//            tvMonitorStatus.setText("已监控");
//        } else {
//            ivMonitorStatus.setSelected(false);
//            tvMonitorStatus.setSelected(false);
//            tvMonitorStatus.setText("监控");
//
//        }
//    }
//}
