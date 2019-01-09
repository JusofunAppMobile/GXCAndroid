package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.jusfoun.jusfouninquire.R;

/**
 * @author zhaoyapeng
 * @version create time:16/8/1110:31
 * @Email zyp@jusfoun.com
 * @Description ${首页热门 企业view}
 */
public class HomeHotCompanyView extends RelativeLayout {
    private Context mContext;
    public HomeHotCompanyView(Context context) {
        super(context);
        mContext = context;
        initViews();
        initAction();
    }

    public HomeHotCompanyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
        initAction();
    }

    public HomeHotCompanyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initViews();
        initAction();
    }

    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_home_hot_company,this,true);
    }

    private void initAction() {

    }
}
