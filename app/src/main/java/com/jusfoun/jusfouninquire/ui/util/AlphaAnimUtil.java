package com.jusfoun.jusfouninquire.ui.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.service.event.NoticeImageLoadEvent;
import com.jusfoun.jusfouninquire.service.event.NoticeTextEvent;
import com.jusfoun.jusfouninquire.sharedpreference.FirstStartAppSharePreference;

import de.greenrobot.event.EventBus;

/**
 * @author zhaoyapeng
 * @version create time:15/11/26下午3:51
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class AlphaAnimUtil {

    private ValueAnimator valueAnimatorIn, valueAnimatorOut,valueAnimatorStay;
    private AnimatorSet animatorSet;
    private AnimatorSet dismissAnimatorSet;
    private final View view;

    private boolean isDissmissing;

    public AlphaAnimUtil(View v) {
        this.view = v;
        isDissmissing = false;
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        valueAnimatorIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 0.8f);
        valueAnimatorStay = ObjectAnimator.ofFloat(view, "alpha", 0.8f, 0.8f);
        valueAnimatorOut = ObjectAnimator.ofFloat(view, "alpha", 0.8f, 0f);

        valueAnimatorIn.setDuration(1000);
        valueAnimatorStay.setDuration(3000);
        valueAnimatorOut.setDuration(1000);

        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playSequentially(valueAnimatorIn, valueAnimatorStay,valueAnimatorOut);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                if (view.getId() == R.id.home_lead){
                    FirstStartAppSharePreference.saveHomeFirstStart(view.getContext());
                    EventBus.getDefault().post(new NoticeImageLoadEvent(0));
                    EventBus.getDefault().post(new NoticeTextEvent());
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatorSet.cancel();
                view.setVisibility(View.GONE);
                if (view.getId() == R.id.home_lead){
                    FirstStartAppSharePreference.saveHomeFirstStart(view.getContext());
                    EventBus.getDefault().post(new NoticeImageLoadEvent(0));
                    EventBus.getDefault().post(new NoticeTextEvent());
                }
            }
        });

    }

    public void start() {
        animatorSet.start();
    }


    public void dismiss(){
        if (!isDissmissing){
            animatorSet.cancel();
            animatorSet.removeAllListeners();
            isDissmissing = true;
            dismissAnimatorSet = new AnimatorSet();
            dismissAnimatorSet.setInterpolator(new LinearInterpolator());
            dismissAnimatorSet.playSequentially(valueAnimatorOut);
            dismissAnimatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                }
            });
        }
        dismissAnimatorSet.start();
    }

    public boolean isDissmissing() {
        return isDissmissing;
    }

    public void setIsDissmissing(boolean isDissmissing) {
        this.isDissmissing = isDissmissing;
    }
}
