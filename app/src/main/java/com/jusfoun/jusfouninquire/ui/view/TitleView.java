package com.jusfoun.jusfouninquire.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.util.TouchUtil;
import com.jusfoun.jusfouninquire.ui.util.KeyBoardUtil;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

/**
 * @author lee
 * @version create time:2015/11/2015:43
 * @Email email
 * @Description ${TODO}
 */

public class TitleView extends LinearLayout {

    private OnRightClickListener listener;
    private OnLeftClickListener leftListener;

    private TextView titleText, rightText;
    private RelativeLayout titleBackGroud;
    private Context context;
    private ImageView leftText, rightImage;


    public TitleView(Context context) {
        super(context);
        initView(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(final Context context, AttributeSet attrs) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_title_view, this, true);
        titleBackGroud = (RelativeLayout) view.findViewById(R.id.titleBackgroud);
        leftText = (ImageView) findViewById(R.id.left_textview);
        rightImage = (ImageView) findViewById(R.id.rightImage);
        TouchUtil.createTouchDelegate(leftText, PhoneUtil.dip2px(context, 10));
        leftText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (leftListener != null) {
                    leftListener.onClick(v);
                } else {
                    ((Activity) context).finish();
                    KeyBoardUtil.hideSoftKeyboard((Activity) context);
                }

            }
        });


        titleText = (TextView) findViewById(R.id.title_text);
        rightText = (TextView) findViewById(R.id.rightText);
        rightText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
        rightImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.title_view);
            boolean isRed= array.getBoolean(R.styleable.title_view_title_is_red, false);
            if (isRed) {
                titleBackGroud.setBackgroundColor(Color.parseColor("#E2292F"));
                leftText.setColorFilter(Color.WHITE);
                rightText.setTextColor(Color.WHITE);
                titleText.setTextColor(Color.WHITE);
                rightImage.setColorFilter(Color.WHITE);
            }
        }

    }

    public void setTitleBackGroud(int id) {
        titleBackGroud.setBackgroundColor(id);
    }

    public void setTitle(int id) {
        titleText.setText(id);
    }

    public void setTitle(String title) {
        titleText.setText(title);
    }

    public void setRightText(int id) {
        rightText.setVisibility(View.VISIBLE);
        rightText.setText(id);
    }

    public void setRightText(String title) {
        rightText.setVisibility(View.VISIBLE);
        rightText.setText(title);
    }

//    public void setLeftText(int id) {
//        leftText.setVisibility(View.VISIBLE);
//        leftText.setText(id);
//    }
//
//    public void setLeftText(String title) {
//        leftText.setVisibility(View.VISIBLE);
//        leftText.setText(title);
//    }
//
//    public void setLeftTextColor(int color){
//        leftText.setTextColor(color);
//    }

    public void setRightImage(int resid) {
        rightImage.setVisibility(View.VISIBLE);
        rightImage.setImageResource(resid);
    }

    public void setLeftImage(int resid) {
        leftText.setVisibility(View.VISIBLE);
        leftText.setImageResource(resid);
    }

    public void setLeftIsShow(boolean isShow) {
        if (isShow) {
            leftText.setVisibility(View.VISIBLE);
        } else {
            leftText.setVisibility(View.GONE);
        }
    }

    public void setTitleDrawableRight(int resid) {
        Drawable drawable = context.getResources().getDrawable(resid);
        drawable.setBounds(0, 0, 0, 0);
        titleText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public void setRightClickable(boolean clickable) {
        rightText.setClickable(clickable);
    }


    public void setRightClickListener(OnRightClickListener listener) {
        this.listener = listener;
    }

    public void setLeftClickListener(OnLeftClickListener leftListener) {
        this.leftListener = leftListener;
    }

    public void setTitleClickListener(OnClickListener listener) {
        if (listener != null)
            titleText.setOnClickListener(listener);
    }

    public interface OnRightClickListener {
        public void onClick(View v);
    }

    public interface OnLeftClickListener {
        public void onClick(View v);
    }

    public void setBackgroundTranlate() {
        findViewById(R.id.titleBackgroud).setBackgroundColor(Color.TRANSPARENT);
        findViewById(R.id.vLine).setVisibility(View.GONE);
    }

}
