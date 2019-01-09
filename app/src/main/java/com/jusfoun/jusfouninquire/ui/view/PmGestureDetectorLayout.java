package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author zhaoyapeng
 * @version create time:15/11/11上午9:58
 * @Email zyp@jusfoun.com
 * @Description ${用以手势监听}
 */
public class PmGestureDetectorLayout extends LinearLayout implements GestureDetector.OnGestureListener {

    private int OFFSET_TOP = -10;
    private int OFFSET_BOTTOM = 10;

    private GestureDetector mGesture;

    public PmGestureDetectorLayout(Context context) {
        super(context);
        init();
    }

    public PmGestureDetectorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PmGestureDetectorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        mGesture=new GestureDetector(this);
    }
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        Log.e("tag","onFling");
        if (e2.getY() - e1.getY() < OFFSET_TOP && Math.abs(velocityY) > 10) {
            callBack.toTop();
            return true;
        }

        if (e2.getY() - e1.getY() > OFFSET_BOTTOM && Math.abs(velocityY) > 10) {
            callBack.toBottom();
            return true;
        }


        return false;
    }

 

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mGesture.onTouchEvent(ev);
    }

    public interface CallBack {
        // 向上滑动
        void toTop();

        // 向下滑动
        void toBottom();
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
