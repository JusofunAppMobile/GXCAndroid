package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siccredit.guoxin.R;

import java.lang.ref.PhantomReference;

/**
 * Author  wangchenchen
 * CreateDate 2016/8/15.
 * Email wcc@jusfoun.com
 * Description 个人信息组件
 */
public class PersonInfoView extends RelativeLayout {
    private String title;
    private TextView titleTxt,contentTxt;
    private Context context;

    public PersonInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
        initAction();
    }

    public PersonInfoView(Context context, AttributeSet attrs, int defStyleAttr, Context context1) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context,AttributeSet attrs){
        this.context=context;
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.PersonInfoView);
        title=typedArray.getString(R.styleable.PersonInfoView_title_text);
        typedArray.recycle();
        LayoutInflater.from(context).inflate(R.layout.layout_person_info,this,true);
        titleTxt= (TextView) findViewById(R.id.title);
        contentTxt= (TextView) findViewById(R.id.content);
    }

    private void initAction(){
        titleTxt.setText(title);
    }

    public void setContentTxt(String content) {
        contentTxt.setText(content);
    }

    public void setTitleTxt(String title){
        titleTxt.setText(title);
    }
}
