package com.jusfoun.jusfouninquire.ui.view;

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

import com.jusfoun.jusfouninquire.service.event.GoTypeSearchEvent;
import com.siccredit.guoxin.R;

import de.greenrobot.event.EventBus;

/**
 * SearchTitleView
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/15
 * @Description :搜索结果页面 title view
 */
public class SearchTitleView extends RelativeLayout {
    private Context mContext;
    private ImageView mRight;
    private ImageView mClear, mLeft;
    private TextView mSearchEditText;


    public SearchTitleView(Context context) {
        super(context);
        initData(context);
        initView();
        initWidgetAction();
    }

    public SearchTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initView();
        initWidgetAction();
    }

    public SearchTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SearchTitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context);
        initView();
        initWidgetAction();
    }

    private void initData(Context context) {
        this.mContext = context;
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_search_title, this, true);
        mLeft = (ImageView) findViewById(R.id.left_text);
        mRight = (ImageView) findViewById(R.id.right_text);
        mClear = (ImageView) findViewById(R.id.clear_search_image);
        mSearchEditText = (TextView) findViewById(R.id.search_edittext);
    }

    private void initWidgetAction() {
        mLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (titleListener != null) {
//                    titleListener.onLeftClick();
//                } else {
                ((Activity) mContext).finish();
//                }
            }
        });


        mSearchEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                EventBus.getDefault().post(new SearchResultFinishEvent());
                finish();
            }
        });

        mClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                EventBus.getDefault().post(new SearchResultFinishEvent());
                finish();
            }
        });
    }

    private void finish() {
        GoTypeSearchEvent event = new GoTypeSearchEvent();
        event.setKey(mSearchEditText.getText().toString());
        EventBus.getDefault().post(event);
        ((Activity) mContext).finish();
    }

    public void hideRightView() {
        mRight.setVisibility(View.INVISIBLE);
        mRight.getLayoutParams().width = mRight.getWidth() / 2;
    }

    public void setEditText(String key) {
        mSearchEditText.setText(key);
    }

    public void setOnFilterClickListener(OnClickListener listener) {
        mRight.setOnClickListener(listener);
    }
}
