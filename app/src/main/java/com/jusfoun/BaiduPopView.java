package com.jusfoun;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.BaseView;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1817:05
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class BaiduPopView extends BaseView {
    protected TextView textPop;

    public BaiduPopView(Context context) {
        super(context);
    }

    public BaiduPopView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaiduPopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_baidu_pop, this, true);
        textPop = (TextView) findViewById(R.id.text_pop);
    }

    @Override
    protected void initActions() {
    }

    public void setData(String str){
        textPop.setText(str);
    }
}
