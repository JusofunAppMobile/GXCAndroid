package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;

/**
 * Created by Albert on 2016/1/12.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description: 设置页面底部view，由服务器控制
 */
public class SettingBottomView extends RelativeLayout {
    private Context mContext;
    private String mViewType;
    private TextView mContent;
//    private ImageView logo;
    public SettingBottomView(Context context) {
        super(context);
        initData(context);
        initView();
        initWidgetAction();
    }

    public SettingBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initView();
        initWidgetAction();
    }


    public SettingBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SettingBottomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context);
        initView();
        initWidgetAction();
    }


    private void initData(Context context) {
        mContext = context;
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.setting_bottom_view_layout, this, true);
        mContent = (TextView) findViewById(R.id.view_text);

    }

    private void initWidgetAction() {

    }

    public void setViewText(String content){
        mContent.setText(content);
    }

    public String getViewType() {
        return mViewType;
    }

    public void setViewType(String mViewType) {
        this.mViewType = mViewType;
    }
}
