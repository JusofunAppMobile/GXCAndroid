package com.gxc.ui.adapter;

import android.app.Activity;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.HomeNewsModel;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;


/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 11:40
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class HomeNewsAdapter extends BaseQuickAdapter<HomeNewsModel, BaseViewHolder> {

    private int imageWidth;

    public HomeNewsAdapter(Activity activity) {
        super(R.layout.item_home_news);
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int emptyWidth = (int) InquireApplication.application.getResources().getDimension(R.dimen.item_image_empty);
        imageWidth = (width - emptyWidth) / 3;
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeNewsModel model) {
        ImageView iv1 = holder.getView(R.id.iv1);
        ImageView iv2 = holder.getView(R.id.iv2);
        ImageView iv3 = holder.getView(R.id.iv3);
        ImageView iv4 = holder.getView(R.id.iv4);
        ImageView ivBig = holder.getView(R.id.ivBig);

        iv1.getLayoutParams().width = imageWidth;

        handlerImageVisiable(model, model.newsImage == null ? 0 : model.newsImage.size(), iv1, iv2, iv3, iv4, ivBig);
    }

    private void handlerImageVisiable(HomeNewsModel model, int size, ImageView iv1, ImageView iv2, ImageView iv3, ImageView iv4, ImageView ivBig) {
        if (size == 0) { //
            iv1.setVisibility(View.GONE);
            iv2.setVisibility(View.GONE);
            iv3.setVisibility(View.GONE);
            iv4.setVisibility(View.GONE);
        } else {
            if (model.newsType == 1) { // 直连  显示图片
                iv1.setVisibility(View.GONE);
                iv2.setVisibility(View.GONE);
                iv3.setVisibility(View.GONE);
                iv4.setVisibility(View.GONE);
                ivBig.setVisibility(View.VISIBLE);
                loadImage(model.newsImage.get(0), ivBig);
            } else {
                ivBig.setVisibility(View.GONE);
                if (size == 1) {
                    iv1.setVisibility(View.VISIBLE);
                    iv2.setVisibility(View.GONE);
                    iv3.setVisibility(View.GONE);
                    iv4.setVisibility(View.GONE);
                    loadImage(model.newsImage.get(0), iv1);
                } else {
                    iv1.setVisibility(View.GONE);
                    iv2.setVisibility(size >= 2 ? View.VISIBLE : View.INVISIBLE);
                    iv3.setVisibility(size >= 2 ? View.VISIBLE : View.INVISIBLE);
                    iv4.setVisibility(size >= 3 ? View.VISIBLE : View.INVISIBLE);
                    loadImage(model.newsImage.get(0), iv1);
                    loadImage(model.newsImage.get(1), iv2);
                    if (size >= 3)
                        loadImage(model.newsImage.get(2), iv3);
                }
            }
        }
    }

    private void loadImage(String url, ImageView imageView) {
        Glide.with(InquireApplication.application).load(url).into(imageView);
    }

}