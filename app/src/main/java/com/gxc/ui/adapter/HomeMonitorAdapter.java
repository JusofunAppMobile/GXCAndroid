package com.gxc.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.HomeMonitorModel;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 11:40
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class HomeMonitorAdapter extends BaseQuickAdapter<HomeMonitorModel, BaseViewHolder> {

    public HomeMonitorAdapter() {
        super(R.layout.item_home_monitor);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeMonitorModel model) {
        TextView tvTitle = holder.getView(R.id.tvTitle);
        TextView tvInfo = holder.getView(R.id.tvInfo);
        TextView tvTime = holder.getView(R.id.tvTime);

        tvTitle.setText(model.companyName);
        tvTime.setText(model.changeDate);

        tvInfo.setText(AppUtils.getNumFont(mContext, model.changeCount));
    }
}
