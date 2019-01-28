package com.jusfoun.jusfouninquire.ui.view.PropagandaView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.util.TouchUtil;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

/**
 * Created by lsq on 2016/8/15.
 * 企业详情头部View
 */
public class BackAndImageTitleView extends RelativeLayout {
    private TextView mTitleView;
    private ImageView mShare, mHeart, mLeftView, errorReportImg;
    private FollowListener mFollowListener;
    private ShareListener mShareListener;
    private Context mContext;
    private RelativeLayout layout;

    public BackAndImageTitleView(Context context) {
        super(context);
        mContext = context;
        initView(context);
        initWidgetAction(context);
    }

    public BackAndImageTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
        initWidgetAction(context);
    }

    public BackAndImageTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context);
        initWidgetAction(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BackAndImageTitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView(context);
        initWidgetAction(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.back_and_image_title_view, this, true);
        mLeftView = (ImageView) findViewById(R.id.back_image_view_left);
        mShare = (ImageView) findViewById(R.id.share);
        mHeart = (ImageView) findViewById(R.id.heart);
        mTitleView = (TextView) findViewById(R.id.back_image_view_title);
        layout = (RelativeLayout) findViewById(R.id.layout);
        errorReportImg = (ImageView) findViewById(R.id.img_error_report);
    }

    private void initWidgetAction(final Context context) {
        TouchUtil.createTouchDelegate(mLeftView, PhoneUtil.dip2px(context, 10));
        mLeftView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });
        mShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShareListener != null)
                    mShareListener.onClick();
            }
        });
        mHeart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFollowListener != null)
                    mFollowListener.onClick();
            }
        });

    }

    public void setLeftViewBackgroundResource(int resid) {
        mLeftView.setBackgroundResource(resid);
    }

    public void setFollow(boolean follow) {
        mHeart.setSelected(follow);
    }

    public void setTitleAlpha(int alpha) {
        if (layout.getBackground() != null)
            layout.getBackground().setAlpha(alpha);
    }

    public void setTitleText(String titleText) {
        mTitleView.setText(titleText);
    }

    public void setTitleText(int resid) {
        mTitleView.setText(resid);
    }

    public void setmFollowListener(FollowListener mFollowListener) {
        this.mFollowListener = mFollowListener;
    }

    public void setmShareListener(ShareListener mShareListener) {
        this.mShareListener = mShareListener;
    }

    public void setmTitleClickListener(OnClickListener listener) {
        mTitleView.setOnClickListener(listener);
    }


    public void setReportClickListener(OnClickListener listener) {
        errorReportImg.setOnClickListener(listener);
    }

    public interface FollowListener {
        public void onClick();
    }

    public interface ShareListener {
        public void onClick();
    }

    public void setVGone(int state) {
//        mShare.setVisibility(state);
        mHeart.setVisibility(state);

//        errorReportImg.setVisibility(state);
    }
}
