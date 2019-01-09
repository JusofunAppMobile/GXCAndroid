package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.util.TouchUtil;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/24.
 * Description
 */
public class CompanyDetialTitle extends LinearLayout {

    private Context context;
    private TextView back;
    private TextView oneTxt,twoTxt,threeTxt;
    public CompanyDetialTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public CompanyDetialTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CompanyDetialTitle(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context){
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.layout_company_detial_title,this,true);
        oneTxt= (TextView) findViewById(R.id.title_one_company);
        twoTxt= (TextView) findViewById(R.id.title_two_company);
        threeTxt= (TextView) findViewById(R.id.title_three_company);
        back = (TextView) findViewById(R.id.title_left_image_company);
        initWidgetAction();
    }

    private void initWidgetAction(){
        TouchUtil.createTouchDelegate(back, PhoneUtil.dip2px(context, 80));
        TouchUtil.createTouchDelegate(oneTxt, PhoneUtil.dip2px(context, 10));
        TouchUtil.createTouchDelegate(twoTxt, PhoneUtil.dip2px(context, 10));
        TouchUtil.createTouchDelegate(threeTxt, PhoneUtil.dip2px(context, 10));
    }

    public void setHide(int oneState,int twoState,int threeState){
        oneTxt.setVisibility(oneState);
        twoTxt.setVisibility(twoState);
        threeTxt.setVisibility(threeState);
    }

    public void setBackOnClickListener(View.OnClickListener listener){
        if (listener!=null){
            back.setOnClickListener(listener);
        }
    }

    public void setOneTxtOnClickListener(OnClickListener listener){
        if (listener!=null)
            oneTxt.setOnClickListener(listener);
    }

    public void setTwoTxtOnClickListener(OnClickListener listener){
        if (listener!=null)
            twoTxt.setOnClickListener(listener);
    }

    public void setThreeTxtOnClickListener(OnClickListener listener){
        if (listener!=null)
            threeTxt.setOnClickListener(listener);
    }
}
