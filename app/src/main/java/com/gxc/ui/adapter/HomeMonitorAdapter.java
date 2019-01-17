package com.gxc.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.GlideApp;
import com.gxc.model.HomeMonitorModel;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 11:40
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class HomeMonitorAdapter extends BaseQuickAdapter<HomeMonitorModel, BaseViewHolder> {

    private RequestOptions options;

    public HomeMonitorAdapter() {
        super(R.layout.item_home_monitor);
        options = new RequestOptions()
                .placeholder(R.drawable.home_icon_gongsi)
                .error(R.drawable.home_icon_gongsi);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeMonitorModel model) {
        TextView tvTitle = holder.getView(R.id.tvTitle);
        TextView tvInfo = holder.getView(R.id.tvInfo);
        TextView tvTime = holder.getView(R.id.tvTime);
        ImageView ivLogo = holder.getView(R.id.ivLogo);

        tvTitle.setText(model.companyName);
        tvTime.setText(model.changeDate);

        if (TextUtils.isEmpty(model.logo))
            ivLogo.setImageResource(R.drawable.img_default_clogo);
        else
            GlideApp.with(InquireApplication.application).load(model.logo).apply(options).into(ivLogo);

        tvInfo.setText(AppUtils.getNumFont(mContext, model.changeCount));
    }
}
