package com.jusfoun.bigdata;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.jusfoun.jusfouninquire.R;


/**
 * @author lee
 * @version create time:2016/7/116:23
 * @Email email
 */

public class OverProgressDialog extends Dialog {
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;

    private RotateAnimation mRotateAnimation1,mRotateAnimation2,mRotateAnimation;

    public OverProgressDialog(Context context) {
        super(context);
    }

    public OverProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected OverProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        image1 = (ImageView) findViewById(R.id.load1);
        image2 = (ImageView) findViewById(R.id.load2);
        image3 = (ImageView) findViewById(R.id.load3);


    }

    /* private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        width = (int) getResources().getDimension(R.dimen.default_progress_bar_width);
        height = (int) getResources().getDimension(R.dimen.default_progress_bar_height);
    }*/



    @Override
    public void show() {
        try{
            super.show();
            mRotateAnimation = new RotateAnimation(0, 360 * 1000000, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            mRotateAnimation.setDuration(1400 * 1000000);
            mRotateAnimation.setInterpolator(new LinearInterpolator());
            mRotateAnimation.setFillAfter(true);
            image1.startAnimation(mRotateAnimation);

            mRotateAnimation1 = new RotateAnimation(0, -360 * 1000000, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            mRotateAnimation1.setDuration(1600 * 1000000);
            mRotateAnimation1.setInterpolator(new LinearInterpolator());
            mRotateAnimation1.setFillAfter(true);
            image2.startAnimation(mRotateAnimation1);

            mRotateAnimation2 = new RotateAnimation(0, 360 * 1000000, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            mRotateAnimation2.setDuration(2000 * 1000000);
            mRotateAnimation2.setInterpolator(new LinearInterpolator());
            mRotateAnimation2.setFillAfter(true);
            image3.startAnimation(mRotateAnimation2);
        }catch (Exception e){

        }

    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(mRotateAnimation != null){
            image1.clearAnimation();
            image2.clearAnimation();
            image3.clearAnimation();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                cancel();
        }
        return super.onKeyDown(keyCode, event);
    }

}
