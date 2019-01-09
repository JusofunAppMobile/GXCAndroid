package com.gxc.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.HomeMenuModel;
import com.jusfoun.jusfouninquire.R;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 11:40
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class HomeMenuAdapter extends BaseQuickAdapter<HomeMenuModel, BaseViewHolder> {

    public HomeMenuAdapter() {
        super(R.layout.item_home_menu);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeMenuModel homeMenuModel) {
        TextView textView = holder.getView(R.id.textView);
        ImageView image = holder.getView(R.id.image);
        textView.setText(homeMenuModel.menuName);
        if (!TextUtils.isEmpty(homeMenuModel.menuImage)) {
            if (homeMenuModel.menuImage.startsWith("http")) {

            } else {
                image.setImageResource(Integer.parseInt(homeMenuModel.menuImage));
            }
        }
    }
}
