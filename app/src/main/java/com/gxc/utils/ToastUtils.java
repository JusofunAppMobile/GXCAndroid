package com.gxc.utils;

import android.widget.Toast;

import com.jusfoun.jusfouninquire.InquireApplication;

import static android.R.attr.duration;


public class ToastUtils {

    private static Toast mToast;

    public static void show(String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(String text, int duration) {
        if (text == null)
            text = "NULL";
        if (mToast == null) {
            mToast = Toast.makeText(InquireApplication.application, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void show(int resid) {
        show(InquireApplication.application.getResources().getString(resid));
    }

    public static void show(int resid, int duration) {
        show(InquireApplication.application.getResources().getString(resid), duration);
    }

    public static void showHttpError() {
        show("网络连接异常，请检查网络", duration);
    }


}
