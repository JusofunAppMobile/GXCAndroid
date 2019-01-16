package com.gxc.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.GlideApp;
import com.gxc.model.HistoryModel;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 14:10
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class MyHistoryAdapter extends BaseQuickAdapter<HistoryModel, BaseViewHolder> {
    private RequestOptions options;

    public MyHistoryAdapter() {
        super(R.layout.item_my_history);
        options = new RequestOptions()
                .placeholder(R.drawable.home_icon_gongsi)
                .error(R.drawable.home_icon_gongsi);
    }

    @Override
    protected void convert(BaseViewHolder holder, final HistoryModel model) {
        TextView tvTitle = holder.getView(R.id.tvName);
        ImageView ivLogo = holder.getView(R.id.ivLogo);

        tvTitle.setText(model.companyname);

        if (TextUtils.isEmpty(model.logo))
            ivLogo.setImageResource(R.drawable.img_default_clogo);
        else
            GlideApp.with(InquireApplication.application).load(model.logo).apply(options).into(ivLogo);
    }

}
