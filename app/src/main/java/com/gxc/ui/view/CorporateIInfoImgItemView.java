package com.gxc.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gxc.ui.activity.CertifiedCompanyActivity;
import com.gxc.utils.AppUtils;
import com.gxc.utils.GlideRoundTransform;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.BaseView;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/717:55
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class CorporateIInfoImgItemView extends BaseView {
    protected TextView titleImgText, desText;
    private ImageView photoImg;
    private int type;
    private RequestOptions requestOptions;
    public CorporateIInfoImgItemView(Context context) {
        super(context);
    }

    public CorporateIInfoImgItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CorporateIInfoImgItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {
        requestOptions = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(R.color.white)
                .error(R.color.white)
                .transform(new GlideRoundTransform(mContext,5));
    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_corporate_info_img_item, this, true);
        titleImgText = (TextView) findViewById(R.id.text_img_title);
        desText = (TextView) findViewById(R.id.text_img_des);
        photoImg = (ImageView) findViewById(R.id.img_photo);
    }

    @Override
    protected void initActions() {
        photoImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.pictureSelect((Activity) mContext, false, 1, null, type);
            }
        });
    }

    public void setData(String title, String des, int type) {
        this.type = type;
        titleImgText.setText(title);
        desText.setText(des);

        if(type== CertifiedCompanyActivity.PHOTO_IDENTITY){
            photoImg.setImageResource(R.drawable.id_zhengmian);
        }else{
            photoImg.setImageResource(R.drawable.id_fanmian);
        }
    }

    public void setImageSrc(String imageUrl){
        Glide.with(mContext).load(imageUrl).apply(requestOptions).into(photoImg);
    }
}
