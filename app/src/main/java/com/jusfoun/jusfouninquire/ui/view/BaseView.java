package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import butterknife.BindView;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2817:03
 * @Email zyp@jusfoun.com
 * @Description ${ 自定义view 基类}
 */
public abstract  class BaseView extends RelativeLayout {
    protected  Context mContext;
    public BaseView(Context context) {
        super(context);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initViews();
        initActions();
    }
    protected  abstract  void initData();

    protected abstract  void initViews();

    protected  abstract  void initActions();
}
