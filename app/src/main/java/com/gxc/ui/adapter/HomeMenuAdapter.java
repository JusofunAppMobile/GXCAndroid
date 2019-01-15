package com.gxc.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.GlideApp;
import com.gxc.model.HomeMenuModel;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 11:40
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class HomeMenuAdapter extends BaseQuickAdapter<HomeMenuModel, BaseViewHolder> {

    private RequestOptions options;

    public HomeMenuAdapter() {
        super(R.layout.item_home_menu);
        options = RequestOptions.bitmapTransform(new CircleCrop())
                .placeholder(R.drawable.img_default_icon)
                .error(R.drawable.img_default_icon);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeMenuModel homeMenuModel) {
        TextView textView = holder.getView(R.id.textView);
        ImageView image = holder.getView(R.id.image);
        textView.setText(homeMenuModel.menuName);
        if (!TextUtils.isEmpty(homeMenuModel.menuImage)) {
            if (homeMenuModel.menuImage.startsWith("http")) {
                GlideApp.with(InquireApplication.application).load(homeMenuModel.menuImage).apply(options).into(image);
            } else {
                image.setImageResource(Integer.parseInt(homeMenuModel.menuImage));
            }
        } else
            image.setImageResource(R.drawable.img_default_icon);
    }
}
