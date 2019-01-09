package com.jusfoun.jusfouninquire.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.adapter.SystemMsgAdapter;

/**
 * Author  wangchenchen
 * CreateDate 2016/10/17.
 * Email wcc@jusfoun.com
 * Description 右滑菜单
 */
public class SwipeRightMenuLayout extends FrameLayout implements SystemMsgAdapter.OnCloseMenuListener {
    public static final int DEFAULT_SCROLLER_DURATION = 200;

    private int rightViewId;
    private int contentViewId;
    private int mLastX;
    private int mLastY;
    private int mDownX;
    private int mDownY;
    private View contentView, rightView;

    private boolean dragging;
    private OverScroller mScroller;
    private int mScaledTouchSlop;
    private SwipeRightHorizontal rightHorizontal;
    private VelocityTracker velocityTracker;
    private int mScaledMinimumFlingVelocity;
    private int mScaledMaximumFlingVelocity;
    private boolean shouldResetSwipe;

    public SwipeRightMenuLayout(Context context) {
        this(context,null);
    }

    public SwipeRightMenuLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SwipeRightMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.SwipeRightMenuLayout);
        rightViewId =typedArray.getResourceId(R.styleable.SwipeRightMenuLayout_right_view,0);
        contentViewId =typedArray.getResourceId(R.styleable.SwipeRightMenuLayout_content_view,0);
        typedArray.recycle();

        mScroller =new OverScroller(getContext());
        ViewConfiguration viewConfiguration=ViewConfiguration.get(context);
        mScaledTouchSlop=viewConfiguration.getScaledTouchSlop();
        mScaledMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mScaledMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipeRightMenuLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean isRightMenuOpenNotEqual(){
        return rightHorizontal !=null&& rightHorizontal.isMenuOpenNotEqual(getScrollX());
    }

    public boolean isRightMenuOpen() {
        return rightHorizontal != null && rightHorizontal.isMenuOpen(getScrollX());
    }

    private void smoothOpenMenu(int duration) {
        if (rightHorizontal != null) {
            rightHorizontal.autoOpenMenu(mScroller, getScrollX(), duration);
            invalidate();
        }
    }

    public void smoothCloseMenu(int duration){
        if (rightHorizontal !=null) {
            if (listener!=null){
                listener.onClose();
            }
            rightHorizontal.autoCloseMenu(mScroller, getScrollX(), duration);
            invalidate();
        }
    }

    /**
     * 滑动完成，计算速度.
     *
     * @param ev       up event.
     * @param velocity velocity x.
     * @return finish duration.
     */
    private int getSwipeDuration(MotionEvent ev, int velocity) {
        int sx = getScrollX();
        int dx = (int) (ev.getX() - sx);
        final int width = rightHorizontal.getMenuWidth();
        final int halfWidth = width / 2;
        final float distanceRatio = Math.min(1f, 1.0f * Math.abs(dx) / width);
        final float distance = halfWidth + halfWidth * distanceInfluenceForSnapDuration(distanceRatio);
        int duration;
        if (velocity > 0) {
            duration = 4 * Math.round(1000 * Math.abs(distance / velocity));
        } else {
            final float pageDelta = (float) Math.abs(dx) / width;
            duration = (int) ((pageDelta + 1) * 100);
        }
        duration = Math.min(duration, DEFAULT_SCROLLER_DURATION);
        return duration;
    }

    float distanceInfluenceForSnapDuration(float f) {
        f -= 0.5f; // center the values about 0.
        f *= 0.3f * Math.PI / 2.0f;
        return (float) Math.sin(f);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (rightViewId !=0){
            rightView =findViewById(rightViewId);
            rightHorizontal =new SwipeRightHorizontal(rightView);
        }

        if (contentViewId!=0){
            contentView=findViewById(contentViewId);
        }
    }

    private void judgeOpenClose(int dx, int dy) {
        if (rightHorizontal != null) {
            if (Math.abs(getScrollX()) >= (rightHorizontal.getMenuView().getWidth() * 0.5f)) { // auto open
                if (Math.abs(dx) > mScaledTouchSlop || Math.abs(dy) > mScaledTouchSlop) { // swipe up
                    if (rightHorizontal.isMenuOpenNotEqual(getScrollX())) smoothCloseMenu(DEFAULT_SCROLLER_DURATION);
                    else smoothOpenMenu(DEFAULT_SCROLLER_DURATION);
                } else { // normal up
                    if (rightHorizontal.isMenuOpenNotEqual(getScrollX())) smoothCloseMenu(DEFAULT_SCROLLER_DURATION);
                    else smoothOpenMenu(DEFAULT_SCROLLER_DURATION);
                }
            } else { // auto close
                smoothCloseMenu(DEFAULT_SCROLLER_DURATION);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercepted=super.onInterceptTouchEvent(ev);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownX=mLastX= (int) ev.getX();
                mDownY= (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int disX= (int) (ev.getX()-mDownX);
                int disY= (int) (ev.getY()-mDownY);
                isIntercepted=Math.abs(disX)>mScaledTouchSlop&&Math.abs(disX)>Math.abs(disY);
                break;
            case MotionEvent.ACTION_UP:
                isIntercepted=false;
                if (isRightMenuOpenNotEqual()
                        && rightHorizontal.isClickOnContentView(getWidth(),ev.getX())){
                    smoothCloseMenu(DEFAULT_SCROLLER_DURATION);
                    isIntercepted=true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                isIntercepted=false;
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                break;
        }
        return isIntercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker==null)
            velocityTracker=VelocityTracker.obtain();
        velocityTracker.addMovement(event);
        int dx;
        int dy;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX= (int) event.getX();
                mLastY= (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int disX= (int) (mLastX-event.getX());
                int disY= (int) (mLastY-event.getY());
                if (!dragging&&Math.abs(disX)>mScaledTouchSlop&&Math.abs(disX)>Math.abs(disY))
                    dragging=true;

                if (dragging){
                    scrollBy(disX,0);
                    mLastX= (int) event.getX();
                    mLastY= (int) event.getY();
                    shouldResetSwipe=false;
                }
                break;
            case MotionEvent.ACTION_UP:
                dx= (int) (mDownX-event.getX());
                dy= (int) (mDownY-event.getY());
                dragging=false;
                velocityTracker.computeCurrentVelocity(1000,mScaledMaximumFlingVelocity);
                int velocityX= (int) velocityTracker.getXVelocity();
                int velocity=Math.abs(velocityX);
                if (velocity>mScaledMinimumFlingVelocity){
                    int duration=getSwipeDuration(event,velocity);
                    if (velocityX<0){
                        smoothOpenMenu(duration);
                    }else {
                        smoothCloseMenu(duration);
                    }
                    android.support.v4.view.ViewCompat.postInvalidateOnAnimation(this);
                }else {
                    judgeOpenClose(dx,dy);
                }
                velocityTracker.clear();
                velocityTracker.recycle();
                velocityTracker=null;
                if (Math.abs(dx)<mScaledTouchSlop){
                    smoothCloseMenu(DEFAULT_SCROLLER_DURATION);
                }
              /*  if (Math.abs(mDownX - event.getX()) > mScaledTouchSlop || Math.abs(mDownY - event.getY()) > mScaledTouchSlop
                        || rightHorizontal.isMenuOpenNotEqual(getScrollX())) {
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(event);
                    return true;
                }*/
                break;
            case MotionEvent.ACTION_CANCEL:
                dragging = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                } else {
                    dx = (int) (mDownX - event.getX());
                    dy = (int) (mDownY - event.getY());
                    judgeOpenClose(dx, dy);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollTo(int x, int y) {
        if (rightHorizontal==null) {
            super.scrollTo(x, y);
        }else {
            SwipeHorizontal.Checker checker=rightHorizontal.checkXY(x,y);
            shouldResetSwipe=checker.shouldResetSwipe;
            if (checker.x!=getScrollX()){
                super.scrollTo(checker.x,checker.y);
            }
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()&&rightHorizontal!=null) {
            scrollTo(Math.abs(mScroller.getCurrX()), 0);
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int contentViewHeight=0;
        if (contentView!=null){
            int contentViewWidth=contentView.getMeasuredWidthAndState();
            contentViewHeight=contentView.getMeasuredHeightAndState();
            LayoutParams lp= (LayoutParams) contentView.getLayoutParams();
            int l=getPaddingLeft();
            int t=getPaddingTop()+lp.topMargin;
            contentView.layout(l,t,l+contentViewWidth,t+contentViewHeight);

        }

        if (rightHorizontal!=null){
            View rightMenu=rightHorizontal.getMenuView();
            int menuViewWidth=rightMenu.getMeasuredWidthAndState();
            int menuViewHeight=rightMenu.getMeasuredHeightAndState();
            FrameLayout.LayoutParams layoutParams= (LayoutParams) rightMenu.getLayoutParams();
            int t=getPaddingTop()+layoutParams.topMargin;
            int parentViewWidth=getMeasuredWidthAndState();
            rightMenu.layout(parentViewWidth,t,parentViewWidth+menuViewWidth,top+menuViewHeight);
        }
//        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void onClose() {
        smoothCloseMenu(DEFAULT_SCROLLER_DURATION);
    }

    /**
     * 向右滑动关闭菜单显示接口
     */
    public interface OnClosedMenuListener {
        void onClose();
    }

    public OnClosedMenuListener listener;

    public void setListener(OnClosedMenuListener listener) {
        this.listener = listener;
    }
}
