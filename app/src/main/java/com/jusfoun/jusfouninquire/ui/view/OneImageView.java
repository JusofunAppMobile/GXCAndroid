package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.jusfoun.jusfouninquire.R;

/**
 * @author zhaoyapeng
 * @version create time:15/11/23下午2:01
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class OneImageView extends BaseTopPublic {
    private Context mContext;
    private ImageView selecterImg, normalImg;
    private int width = 0;

    public OneImageView(Context context) {
        super(context);
        mContext = context;
        initViews();
    }

    public OneImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public OneImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_image, this, true);
        selecterImg = (ImageView) findViewById(R.id.img_selecter);
        normalImg = (ImageView) findViewById(R.id.img_normal);
    }

    /**
     * 设置资源图片
     */
    public void setImgRes(int normalImgId, int selecterImgId) {
        normalImg.setImageResource(normalImgId);
        selecterImg.setImageResource(selecterImgId);
        width = normalImg.getDrawable().getIntrinsicWidth();
    }

    // 设置选中状态透明度
    public void setSelectAlpah(float alpah) {
//        if (alpah < 0.3f && alpah > 0) {
//            alpah = 0.3f;
//        }
        selecterImg.setAlpha(alpah);
    }

    //设置 正常态透明度
    public void setNormalAlpah(float alpah) {
//        if (alpah < 0.3f && alpah > 0) {
//            alpah = 0.3f;
//        }
        normalImg.setAlpha(alpah);
    }


    public void setBig(float zoom, int postion) {
        normalImg.getLayoutParams().width = (int) (width * (1 - (1 - zoom) / 6.0));
        normalImg.requestLayout();
        selecterImg.getLayoutParams().height = (int) (width * (1 - (1 - zoom) / 6.0));
        normalImg.requestLayout();
    }

    public int getImageWidth() {
        return width;
    }
}
