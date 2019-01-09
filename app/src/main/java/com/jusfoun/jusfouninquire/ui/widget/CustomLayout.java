package com.jusfoun.jusfouninquire.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.jusfoun.jusfouninquire.service.event.RecyclerShowEvent;

import de.greenrobot.event.EventBus;

/**
 * Author  JUSFOUN
 * CreateDate 2015/12/1.
 * Description 检测虚拟按键手机，虚拟按键的显示和隐藏的Layout
 */
public class CustomLayout extends LinearLayout {
    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (onSizeChangeLisener!=null)
            onSizeChangeLisener.onSizeChanged(w,h,oldw,oldh);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed)
            if (onSizeChangeLisener!=null)
                onSizeChangeLisener.onLayoutChanged(l,t,r,b);
    }

    private OnSizeChangeLisener onSizeChangeLisener;

    public void setOnSizeChangeLisener(OnSizeChangeLisener onSizeChangeLisener){
        this.onSizeChangeLisener=onSizeChangeLisener;
    }

    /**
     * 监听虚拟按键的更改
     */
    public static interface OnSizeChangeLisener{
        public void onSizeChanged(int w,int h,int oldw,int oldh);
        public void onLayoutChanged(int l, int t, int r, int b);
    }
}
