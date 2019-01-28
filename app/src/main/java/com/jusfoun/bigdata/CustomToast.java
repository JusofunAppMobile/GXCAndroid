package com.jusfoun.bigdata;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.siccredit.guoxin.R;


/**
 * Author  JUSFOUN
 * CreateDate 2015/9/14.
 * Description 自定义toast
 */
public class CustomToast {

    private static CustomToast customToast;
    private Toast toast;
    private TextView textView;
    private View view;
    private int lastYoffset;

    public static CustomToast getToast() {
        if (customToast == null)
            customToast = new CustomToast();
        return customToast;
    }

    public void toastShow(Context context, String toastString) {

        View view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(toastString);
        toast = new Toast(context);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, PhoneUtil.dip2px(context, 120));
        toast.setView(view);
        toast.show();
    }

    /**
     * 搜索企业数量提示窗口样式修改
     * Created by LiuGuangDan on 2017/5/4 14:58.
     */

    public void showCompanyNum(Context context, String toastString, int yOffset) {
        if (toast == null || yOffset != lastYoffset) {
            view = LayoutInflater.from(context).inflate(R.layout.toast_company_num, null);
            textView = (TextView) view.findViewById(R.id.txt);
            textView.setText(toastString);
            toast = new Toast(context);
            toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, yOffset);
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_SHORT);
        } else {
            textView.setText(toastString);
        }
        toast.show();
        lastYoffset = yOffset;
    }

    public void toastShow(Context context, String toastString, int toastLoc) {

        View view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(toastString);
        toast = new Toast(context);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, PhoneUtil.dip2px(context, toastLoc));
        toast.setView(view);
        toast.show();
    }

    /**
     * 取消Toast显示，但是有点定的延迟，不能立即取消
     * Created by LiuGuangDan on 2017/5/4 16:09.
     */

    public void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

}
