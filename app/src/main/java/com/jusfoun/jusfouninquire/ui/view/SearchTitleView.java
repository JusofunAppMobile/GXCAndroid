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

import com.jusfoun.jusfouninquire.R;

/**
 * SearchTitleView
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/15
 * @Description :搜索结果页面 title view
 */
public class SearchTitleView extends RelativeLayout{
    private Context mContext;



    private TextView mRight;
    private ImageView mClear,mLeft;
    private TextView mSearchEditText;
    private RelativeLayout mEditLayout;


    private TitleListener titleListener;

    
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
        mRight = (TextView) findViewById(R.id.right_text);
        mClear = (ImageView) findViewById(R.id.clear_search_image);
        mSearchEditText = (TextView) findViewById(R.id.search_edittext);
        mEditLayout = (RelativeLayout) findViewById(R.id.search_title_layout);

    }

    private void initWidgetAction() {
        mLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleListener != null){
                    titleListener.onLeftClick();
                }else {
                    ((Activity)mContext).finish();
                }
            }
        });

        mRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                titleListener.onRightClick();
            }
        });



        mSearchEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleListener != null){
                    titleListener.onTypeSearch(mSearchEditText.getText().toString());
                }
            }
        });

        mClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleListener != null){
                    titleListener.onClear();
                }
            }
        });
    }

    public void setEditText(String key){
        mSearchEditText.setText(key);
    }


    public void setTitleListener(TitleListener titleListener) {
        this.titleListener = titleListener;
    }

    public interface TitleListener{
        void onLeftClick();
        void onRightClick();
        void onTypeSearch(String key);
        void onClear();

    }
}
