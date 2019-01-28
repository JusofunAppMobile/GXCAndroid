package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.util.TouchUtil;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class BackAndRightImageTitleView extends LinearLayout {
    private TextView mTitleView,mImageTxt;
    private ImageView mLeftView,mRightView;
    private LeftClickListener mLeftClickListener;
    private RightClickListener mRightClickListener;
    private Context mContext;
    private View vRoot;
    public BackAndRightImageTitleView(Context context) {
        super(context);
        mContext = context;
        initView(context);
        initWidgetAction(context);
    }

    public BackAndRightImageTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
        initWidgetAction(context);
    }

    public BackAndRightImageTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context);
        initWidgetAction(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BackAndRightImageTitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView(context);
        initWidgetAction(context);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.back_and_right_image_title_view, this, true);
        vRoot = findViewById(R.id.vRoot);
        mLeftView = (ImageView)findViewById(R.id.back_image_view_left);
        mRightView = (ImageView)findViewById(R.id.back_image_view_right);
        mTitleView = (TextView)findViewById(R.id.back_image_view_title);
        mImageTxt= (TextView) findViewById(R.id.image_txt);
    }

    private void initWidgetAction(final Context context){
        TouchUtil.createTouchDelegate(mLeftView, PhoneUtil.dip2px(context, 10));
        mLeftView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLeftClickListener != null){
                    mLeftClickListener.onClick();
                }else {
                    ((Activity)mContext).finish();
                }
            }
        });

        mRightView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRightClickListener != null){
                    mRightClickListener.onClick();
                }
            }
        });

        mImageTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRightClickListener != null){
                    mRightClickListener.onClick();
                }
            }
        });

    }

    public void setRootViewBg(int color) {
        vRoot.setBackgroundColor(color);
    }

    public void setRightTextColor(int color){
        mImageTxt.setTextColor(color);
    }

    public void setLeftViewBackgroundResource(int resid){
        mLeftView.setBackgroundResource(resid);
    }

    public void setLeftSrc(int resid){
        mLeftView.setImageResource(resid);
    }

    public void setRightViewBackgroundResource(int resid){
        mRightView.setBackgroundResource(resid);
    }

    public void setRightImageSrc(int resid){
        mRightView.setImageResource(resid);
    }

    public void setTitleText(String titleText){
        mTitleView.setText(titleText);
    }

    public void setTitleText(int resid){
        mTitleView.setText(resid);
    }

    public void setmImageTxt(String string){
        mImageTxt.setText(string);
    }

    public void setmLeftClickListener(LeftClickListener mLeftClickListener) {
        this.mLeftClickListener = mLeftClickListener;
    }

    public void setmRightClickListener(RightClickListener mRightClickListener) {
        this.mRightClickListener = mRightClickListener;
    }

    public void setmTitleClickListener(OnClickListener listener){
        mTitleView.setOnClickListener(listener);
    }

    public interface LeftClickListener{
        public void onClick();
    }

    public interface RightClickListener{
        public void onClick();
    }

}
