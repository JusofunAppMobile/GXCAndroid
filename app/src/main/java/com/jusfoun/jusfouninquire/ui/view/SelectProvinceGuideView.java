package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.siccredit.guoxin.R;

/**
 * Created by Albert on 2015/11/30.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:首次搜索结大于50条，弹出选择省份的提示框
 */
public class SelectProvinceGuideView extends RelativeLayout {

    private Context mContext;

    public SelectProvinceGuideView(Context context) {
        super(context);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public SelectProvinceGuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public SelectProvinceGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SelectProvinceGuideView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    private void initData(){

    }

    private void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.select_province_guide_view_layout, this, true);
    }

    private void initWidgetAction(){

    }
}
