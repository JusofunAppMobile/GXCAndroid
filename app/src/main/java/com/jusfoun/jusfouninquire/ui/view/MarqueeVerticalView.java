package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ViewFlipper;

import com.siccredit.guoxin.R;

import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:15/11/11上午9:07
 * @Email zyp@jusfoun.com
 * @Description ${首页跑马灯 view}
 */
public class MarqueeVerticalView extends LinearLayout {
    private PmGestureDetectorLayout gestureDetectorLayout;
    private ViewFlipper viewFlipper;
    private Context mContext;
    private ScrollView scrollView;
    private int duration;
    private boolean enableTumble;//是否可以上下滚动


    public MarqueeVerticalView(Context context) {
        super(context);
        mContext = context;
        initViews();
        initActions();
    }

    public MarqueeVerticalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
        initActions();
    }

    public MarqueeVerticalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initViews();
        initActions();
    }

    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_paoma, this, true);
        gestureDetectorLayout = (PmGestureDetectorLayout) findViewById(R.id.gesturedetectorlayout);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
    }

    private void initActions() {
        setEnableTumble(true);
        gestureDetectorLayout.setCallBack(new PmGestureDetectorLayout.CallBack() {
            @Override
            public void toTop() {
                if (viewFlipper.getChildCount() == 1
                        || !enableTumble) {
                    return;
                }
                viewFlipper.stopFlipping();
                viewFlipper.setInAnimation(mContext, R.anim.paoma_in_bottom_to_top);
                viewFlipper.setOutAnimation(mContext, R.anim.paoma_out_top_to_bottom);
                viewFlipper.showNext();
                viewFlipper.startFlipping();
            }

            @Override
            public void toBottom() {
                if (viewFlipper.getChildCount() == 1
                        || !enableTumble) {
                    return;
                }
                viewFlipper.stopFlipping();
                viewFlipper.setInAnimation(mContext, R.anim.paoma_in_top_to_bottom);
                viewFlipper.setOutAnimation(mContext, R.anim.paoma_out_bottom_to_top);
                viewFlipper.showPrevious();
                viewFlipper.startFlipping();
                viewFlipper.setInAnimation(mContext, R.anim.paoma_in_bottom_to_top);
                viewFlipper.setOutAnimation(mContext, R.anim.paoma_out_top_to_bottom);

            }
        });

        viewFlipper.setInAnimation(mContext, R.anim.paoma_in_bottom_to_top);
        viewFlipper.setOutAnimation(mContext, R.anim.paoma_out_top_to_bottom);
        viewFlipper.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setFliperViews(List<View> views) {
        viewFlipper.removeAllViews();
        if (views != null) {
            for (int i = 0; i < views.size(); i++) {
                viewFlipper.addView(views.get(i));
            }
        }

        if (viewFlipper.getChildCount() == 1) {
            return;
        }

    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public boolean isEnableTumble() {
        return enableTumble;
    }

    public void setEnableTumble(boolean enableTumble) {
        this.enableTumble = enableTumble;
    }

    /**
     * @param duration 毫秒
     */
    public void startAnim(int duration, int delay) {

        if (viewFlipper != null) {
            if(viewFlipper.getChildCount()==0){
                return;
            }
            if (viewFlipper.getChildCount() == 1) {
                viewFlipper.stopFlipping();
                stopAnim();
                return;
            }
            if (delay > 0) {
                Message message = handler.obtainMessage();
                message.what = 0;
                message.arg1 = duration;
                handler.sendMessageDelayed(message, delay);
                return;
            }
            viewFlipper.setFlipInterval(duration);
            viewFlipper.startFlipping();
        }
    }

    public void stopAnim() {
        handler.removeMessages(0);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startAnim(msg.arg1, 0);
        }
    };

    public Object getCurrentModel() {
        View view = viewFlipper.getCurrentView();
        if(view == null) return null;
        return view.getTag();
    }

    public int getCount(){
        return  viewFlipper.getChildCount();
    }
}
