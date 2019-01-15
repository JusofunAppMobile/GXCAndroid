package com.gxc.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.GlideApp;
import com.gxc.model.MonitorModel;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 14:10
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class MonitorCompanyAdapter extends BaseQuickAdapter<MonitorModel, BaseViewHolder> {

    private RequestOptions options;

    public MonitorCompanyAdapter() {
        super(R.layout.item_monitor_company);
        options = new RequestOptions()
                .placeholder(R.drawable.img_default_clogo)
                .error(R.drawable.img_default_clogo);
    }

    @Override
    protected void convert(BaseViewHolder holder, MonitorModel model) {

        ImageView ivLogo = holder.getView(R.id.ivLogo);
        TextView tvName = holder.getView(R.id.tvName);

        tvName.setText(model.companyName);

        GlideApp.with(InquireApplication.application).load(model.logo).apply(options).into(ivLogo);

    }
}
