package com.gxc.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author liuguangdan
 * @version create at 2018/9/7/007 16:55
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class ScrollableViewPager extends NoPreloadViewPager {

    /**
     * 是否不让viewpager滑动
     */
    private boolean scrollable = false;

    public ScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableViewPager(Context context) {
        super(context);
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!scrollable)
            return false;
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!scrollable)
            return false;
        return super.onInterceptTouchEvent(ev);
    }
}

