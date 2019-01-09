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
    protected void convert(BaseViewHolder holder, HomeNewsModel homeMenuModel) {
        ImageView iv1 = holder.getView(R.id.iv1);
        ImageView iv2 = holder.getView(R.id.iv2);
        ImageView iv3 = holder.getView(R.id.iv3);
        ImageView iv4 = holder.getView(R.id.iv4);
        ImageView ivBig = holder.getView(R.id.ivBig);

        iv1.getLayoutParams().width = imageWidth;

        Glide.with(InquireApplication.application).load("http://t2.hddhhn.com/uploads/tu/201512/264/137.jpg").into(iv1);
        Glide.with(InquireApplication.application).load("http://t2.hddhhn.com/uploads/tu/201812/317/yishuzi-007111.jpg").into(iv2);
        Glide.with(InquireApplication.application).load("http://t2.hddhhn.com/uploads/tu/201812/420/2106035964-3.jpg").into(iv3);
        Glide.with(InquireApplication.application).load("http://t2.hddhhn.com/uploads/tu/201512/252/177.jpg").into(iv4);
        Glide.with(InquireApplication.application).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546931637745&di=c2b4a56050c1864bca1d11a2a326c40e&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F1c950a7b02087bf49661186dffd3572c10dfcfa1.jpg").into(ivBig);

        handlerImageVisiable(holder.getLayoutPosition() % 5, iv1, iv2, iv3, iv4, ivBig);

    }

    private void handlerImageVisiable(int size, ImageView iv1, ImageView iv2, ImageView iv3, ImageView iv4, ImageView ivBig) {
        ivBig.setVisibility(View.GONE);
        if (size == 0) { //
            iv1.setVisibility(View.GONE);
            iv2.setVisibility(View.GONE);
            iv3.setVisibility(View.GONE);
            iv4.setVisibility(View.GONE);
        } else if (size == 1) {
            iv1.setVisibility(View.VISIBLE);
            iv2.setVisibility(View.GONE);
            iv3.setVisibility(View.GONE);
            iv4.setVisibility(View.GONE);
        } else if (size == 2 || size == 3) {
            iv1.setVisibility(View.GONE);
            iv2.setVisibility(size >= 2 ? View.VISIBLE : View.INVISIBLE);
            iv3.setVisibility(size >= 2 ? View.VISIBLE : View.INVISIBLE);
            iv4.setVisibility(size >= 3 ? View.VISIBLE : View.INVISIBLE);
        } else {
            iv1.setVisibility(View.GONE);
            iv2.setVisibility(View.GONE);
            iv3.setVisibility(View.GONE);
            iv4.setVisibility(View.GONE);
            ivBig.setVisibility(View.VISIBLE);
        }
    }
}
