package com.gxc.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.siccredit.guoxin.R;


public class BaseDialog extends Dialog implements View.OnClickListener {

    protected Activity activity;

    public BaseDialog(Activity activity) {
        this(activity, R.style.MCTheme_Widget_Dialog);
    }

    public BaseDialog(Activity activity, int style) {
        super(activity, style);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        this.activity = activity;
        setOwnerActivity(activity);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * @param wid 范围值： 0-10,占屏幕宽的比例
     */
    public void setWindowStyle(int wid) {
        setWindowStyle(wid, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    }

    /**
     * @param wid          范围值： 0-10,占屏幕宽的比例
     * @param heightParams ViewGroup.LayoutParams.WRAP_CONTENT 或者ViewGroup.LayoutParams.MATCH_PARENT
     * @param gravity
     */
    public void setWindowStyle(int wid, int heightParams, int gravity) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int parentWidth = dm.widthPixels;
        int width = parentWidth;
        lp.width = width * wid / 10;
        lp.height = heightParams;
        lp.gravity = gravity;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {

    }
}
