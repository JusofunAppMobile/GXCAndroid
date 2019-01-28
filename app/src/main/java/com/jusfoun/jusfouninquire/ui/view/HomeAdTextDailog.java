package com.jusfoun.jusfouninquire.ui.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.AdResultModel;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

import netlib.util.EventUtils;

/**
 * @author zhaoyapeng
 * @version create time:16/3/30下午4:20
 * @Email zyp@jusfoun.com
 * @Description ${首页广告文字dialog}
 */
public class HomeAdTextDailog extends Dialog {
    private Context mContext;
    private TextView titileText, contentText;
    private TextView goImg;
    private ImageView closeImage;
    private AdResultModel model;
    private boolean readyToShow;
    private boolean hasShown = false;

    public HomeAdTextDailog(Context context) {
        super(context);
        mContext = context;
        initViews();
        initAation();
    }

    public HomeAdTextDailog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        initViews();
        initAation();
    }

    protected HomeAdTextDailog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initViews();
        initAation();
    }

    private void initViews() {
        setContentView(R.layout.activity_home_ad_text_dialog);
        goImg = (TextView) findViewById(R.id.img_go);
        contentText = (TextView) findViewById(R.id.text_content);
        closeImage = (ImageView) findViewById(R.id.close_notice);

        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (PhoneUtil.getDisplayWidth(mContext) * 0.9);
        lp.height = PhoneUtil.getDisplayHeight(mContext);

        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);



        contentText = (TextView) findViewById(R.id.text_content);
       // contentText.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void initAation() {

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                EventUtils.event(mContext,EventUtils.NOTICE02);
            }
        });
    }

    @Override
    public void show() {
        WindowManager.LayoutParams alp = ((Activity) mContext).getWindow().getAttributes();
        alp.alpha = 0.4f;
        ((Activity) mContext).getWindow().setAttributes(alp);
        setHasShown(true);
        super.show();
    }

    @Override
    public void cancel() {
        WindowManager.LayoutParams alp = ((Activity) mContext).getWindow().getAttributes();
        alp.alpha = 1f;
        ((Activity) mContext).getWindow().setAttributes(alp);
        super.cancel();
    }

    public boolean isReadyToShow() {
        return readyToShow;
    }

    public void setReadyToShow(boolean readyToShow) {
        this.readyToShow = readyToShow;
    }

    public boolean isHasShown() {
        return hasShown;
    }

    public void setHasShown(boolean hasShown) {
        this.hasShown = hasShown;
    }

    public void setData(final AdResultModel model) {
        this.model = model;
        setReadyToShow(true);
        if (model != null && model.getDataResult() != null) {
            try {
                contentText.setText(Html.fromHtml(model.getDataResult().getContent()));
            }catch (Exception e){
                contentText.setText("");
            }

            goImg.setText((!TextUtils.isEmpty(model.getDataResult().getUrl())) ? "查看详情" : "我知道了");
            goImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventUtils.event(mContext,EventUtils.NOTICE03);
                    if (!TextUtils.isEmpty(model.getDataResult().getUrl())) {
                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra(WebActivity.TITLE,model.getDataResult().getTitle());
                        intent.putExtra(WebActivity.URL,model.getDataResult().getUrl());
                        mContext.startActivity(intent);
                        cancel();
                    } else {
                        cancel();
                    }
                }
            });

        }
    }


}
