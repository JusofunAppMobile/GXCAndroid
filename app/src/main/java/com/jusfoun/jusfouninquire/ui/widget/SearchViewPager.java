package com.jusfoun.jusfouninquire.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class SearchViewPager extends ViewPager {
    private boolean mNotTouchScoll = false;

    public SearchViewPager(Context context) {
        super(context);
    }

    public SearchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setmNotTouchScoll(boolean mNotTouchScoll) {
        this.mNotTouchScoll = mNotTouchScoll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mNotTouchScoll){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mNotTouchScoll){
            return false;
        }
        return super.onTouchEvent(ev);
    }
}
