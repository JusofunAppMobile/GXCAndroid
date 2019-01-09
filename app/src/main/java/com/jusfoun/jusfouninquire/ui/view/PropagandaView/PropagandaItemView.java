package com.jusfoun.jusfouninquire.ui.view.PropagandaView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.AdItemModel;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;

import netlib.util.EventUtils;

/**
 * PropagandaItemView
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/2
 * @Description :首页运营 单个轮播显示，抽象成view，准备之后进行扩展
 */
public class PropagandaItemView extends LinearLayout{
    private Context mContext;
    private AdItemModel mPropaData;

    private SimpleDraweeView mPropaImage;

    public PropagandaItemView(Context context) {
        super(context);
        initData(context);
        initView();
        initWidgetAction();
    }

    public PropagandaItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initView();
        initWidgetAction();
    }

    public PropagandaItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PropagandaItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context);
        initView();
        initWidgetAction();
    }

    private void initData(Context context) {
        this.mContext = context;
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_item_propaganda, this, true);
        mPropaImage = (SimpleDraweeView) findViewById(R.id.propaganda_image);

    }

    private void initWidgetAction() {
        mPropaImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 进入webview且可以进行分享
                if (mPropaData != null && !TextUtils.isEmpty(mPropaData.getReUrl())){
                    EventUtils.event2(mContext,mPropaData.getUmeng());
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra(WebActivity.URL,mPropaData.getReUrl());
                    intent.putExtra(WebActivity.TITLE,mPropaData.getTitle());
                    intent.putExtra(WebActivity.SHAREDATA,mPropaData);
                    mContext.startActivity(intent);
                }
            }
        });
    }


    /**
     * 设置宣传viewpager 的 item view的内容
     * @param model
     */
    public void setData(AdItemModel model){
        this.mPropaData = model;
        try {

            mPropaImage.setImageURI(Uri.parse(model.getImgUrl()));

        }catch (Exception e){

        }
    }
}
