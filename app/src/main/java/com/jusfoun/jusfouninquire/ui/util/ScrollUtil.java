package com.jusfoun.jusfouninquire.ui.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.siccredit.guoxin.R;

/**
 * Author  JUSFOUN
 * CreateDate 2015/12/2.
 * Description scroll滚动是效果类
 */
public class ScrollUtil {

    public static void imageScale(View view,int h){

        ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
        layoutParams.height+= h;
        view.setLayoutParams(layoutParams);
    }

    public void imageScale(View view,ImageView childView,int h,int heigh){
        ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
        layoutParams.height= h+heigh;
//        layoutParams.width= h+width;
        view.setLayoutParams(layoutParams);
        view.requestLayout();
        ViewGroup.LayoutParams lp=  childView.getLayoutParams();
        lp.height=h+heigh;
//        lp.width=h+width;
        childView.setLayoutParams(lp);
    }

}
