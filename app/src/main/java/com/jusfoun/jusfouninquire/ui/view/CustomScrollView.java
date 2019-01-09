package com.jusfoun.jusfouninquire.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Author  JUSFOUN
 * CreateDate 2015/12/2.
 * Description
 */
public class CustomScrollView extends ScrollView {

    private final static int TOP = 0;
    private final static int CENTRE = 1;
    private final static int BOTTOM = 0;

    private final String TAG = CustomScrollView.class.getSimpleName();
    private float touchY;// 点击时Y坐标
    private float deltaY;// Y轴滑动的距离
    private View contentView;

    private boolean mScaling = false; // 标志 位，获取 按下时的 坐标用
    private float zuni = 0.8f;
    private float aplah = 0.6f;

    /***
     * 构造方法
     *
     * @param context
     */
    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0)
            contentView = getChildAt(0);
    }

    /**
     * touch 事件处理
     **/
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
               // Log.e("tag", "onTouchEvent=ACTION_DOWN");
            case MotionEvent.ACTION_MOVE:
                //Log.e("tag","onTouchEvent=ACTION_MOVE");
                if (!mScaling) {
                    if (getScrollY() == 0) {
                        touchY = ev.getY();
                        deltaY = 0;
                        if (anim != null && anim.isRunning()) {
                            anim.cancel();
                            if (onScrollListener != null)
                                onScrollListener.onPullScroll(0);
                        }
                    } else {
                        break;
                    }
                }

                float y = ev.getY();
                deltaY = (y - touchY);
                if (deltaY >= 0) {
                    mScaling = true;
                    if (onScrollListener != null) {
                        onScrollListener.onPullScroll((int) (deltaY * zuni));
                    } else {
                        break;
                    }
                    return false;
                } else {
                    mScaling = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mScaling) {
                    // 还原
                    mScaling = false;
                    if (deltaY >= 0) {
                        animation((int) (deltaY * zuni));
                    }
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScrollChangedListener(0, (int) (t*aplah), 0, 0);
        }
    }

    private float downIntercept;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downIntercept=ev.getY();
                touchY = ev.getY();
                deltaY = 0;
////                return  super.onInterceptTouchEvent(ev);
//            case MotionEvent.ACTION_MOVE:
//                Log.e(TAG,"top=="+(downIntercept - ev.getY()));
//                if (Math.abs(downIntercept - ev.getY())>5)
//                    return true;
////                if (Math.abs(velocityTracker.getYVelocity())>10)
////                    return true;
//
        }
        return super.onInterceptTouchEvent(ev);
    }

    private int scrollStatue() {
        if (getScrollY() == 0)
            return TOP;
        if (contentView != null && contentView.getMeasuredHeight() == getScrollY() + getHeight())
            return BOTTOM;
        return CENTRE;
    }

    private ValueAnimator anim = null;

    public void animation(int y) {
        anim = ValueAnimator.ofInt(y, 0);
        anim.setDuration(200);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer y = (Integer) animation.getAnimatedValue();
                deltaY = y;
                if (onScrollListener != null)
                    onScrollListener.onPullScroll((int) deltaY);
            }
        });
        anim.start();
    }

    public interface OnScrollListener {
        void onScrollChangedListener(int leftOfVisibleView, int topOfVisibleView, int oldLeftOfVisibleView, int oldTopOfVisibleView);

        void onPullScroll(int height);
    }

    private OnScrollListener onScrollListener;

    public void setCallBack(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * 滑动事件
     */
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 2);
    }
}
