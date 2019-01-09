package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author zhaoyapeng
 * @version create time:15/11/23下午1:59
 * @Email zyp@jusfoun.com
 * @Description ${}
 */
public abstract class BaseTopPublic extends LinearLayout {
    public BaseTopPublic(Context context) {
        super(context);
    }

    public BaseTopPublic(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseTopPublic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置资源图片
     */
    public abstract void setImgRes(int normalImgId, int selecterImgId);

    // 设置选中状态透明度
    public abstract void setSelectAlpah(float alpah);

    //设置 正常态透明度
    public abstract void setNormalAlpah(float alpah);


    public abstract void setBig(float zoom, int postion);


    public abstract int getImageWidth();
}
