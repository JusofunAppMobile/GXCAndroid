package com.jusfoun.jusfouninquire.ui.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.sharedpreference.NoticeImagePreference;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

import netlib.util.EventUtils;

/**
 * @author zhaoyapeng
 * @version create time:16/3/30下午4:56
 * @Email zyp@jusfoun.com
 * @Description ${首页 广告 image dialog}
 */
public class HomeAdImageDialog extends Dialog {
    private Context mContext;
    private ImageView contentImg, goImage,closeImage;
    private String intentURL,title;
    private boolean hasShown = false;

    public HomeAdImageDialog(Context context) {
        super(context);
        mContext = context;
        initViews();
        initAation();
    }

    public HomeAdImageDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        initViews();
        initAation();
    }

    protected HomeAdImageDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initViews();
        initAation();
    }

    private void initViews() {
        setContentView(R.layout.dialog_home_ad_image);
        contentImg = (ImageView) findViewById(R.id.img_conetnt);
        goImage = (ImageView) findViewById(R.id.img_intent);
        closeImage = (ImageView) findViewById(R.id.close_notice);

        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (PhoneUtil.getDisplayWidth(mContext) * 0.9);
        lp.height = PhoneUtil.getDisplayHeight(mContext);
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);

    }

    private void initAation() {
        goImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(intentURL)) {
                    cancel();
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra(WebActivity.URL, intentURL);
                    intent.putExtra(WebActivity.TITLE, title);
                    mContext.startActivity(intent);
                    EventUtils.event(mContext,EventUtils.NOTICE03);
                }
            }
        });


        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                EventUtils.event(mContext,EventUtils.NOTICE02);
            }
        });
    }

    public boolean isHasShown() {
        return hasShown;
    }

    public void setHasShown(boolean hasShown) {
        this.hasShown = hasShown;
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

    public void setData() {
        contentImg.setImageURI(Uri.parse("file://" + NoticeImagePreference.getNoticeImageUrl(mContext)));
        goImage.setImageURI(Uri.parse("file://" + NoticeImagePreference.getNoticeBtnUrl(mContext)));

    }

    public void setIntentURL(String title,String url){
        this.title = title;
        this.intentURL = url;
    }

}
