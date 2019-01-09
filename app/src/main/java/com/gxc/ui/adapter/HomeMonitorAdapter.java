package com.gxc.ui.adapter;

import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.HomeMonitorModel;
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
    protected void convert(BaseViewHolder holder, HomeMonitorModel homeMenuModel) {
        TextView tvTitle = holder.getView(R.id.tvTitle);
        TextView tvInfo = holder.getView(R.id.tvInfo);
        TextView tvTime = holder.getView(R.id.tvTime);
        ImageView ivLogo = holder.getView(R.id.ivLogo);

        tvInfo.setText(Html.fromHtml("变更开庭公告等<font color='#E2292F'>3</font>条动态"));
    }
}
