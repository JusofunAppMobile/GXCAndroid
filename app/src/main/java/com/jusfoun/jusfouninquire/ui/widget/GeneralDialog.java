package com.jusfoun.jusfouninquire.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

/**
 * @author lee
 * @version create time:2015/11/3011:26
 * @Email email
 * @Description 普通（遍）性的Dialog
 */

public class GeneralDialog extends Dialog{

    private TextView titleTextView,descriptTextView,leftButton,rightButton;
    private ImageView icon;


    public GeneralDialog(Context context) {
        super(context);
        initView(context);
    }

    public GeneralDialog(Context context, int theme) {
        super(context, theme);
        initView(context);
    }

    protected GeneralDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private void initView(Context context){
        setContentView(R.layout.dialog_general_view);

        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (PhoneUtil.getDisplayWidth(context) * 0.8);
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);

        titleTextView = (TextView) findViewById(R.id.titleTextView);
        descriptTextView = (TextView) findViewById(R.id.decriptTextView);
        leftButton = (TextView) findViewById(R.id.leftButton);
        rightButton = (TextView) findViewById(R.id.rightButton);
        icon = (ImageView) findViewById(R.id.icon);
    }

    public void setRightListener(View.OnClickListener listener) {
        if (listener!=null) {
            rightButton.setOnClickListener(listener);
        }
    }
    public void setLeftListener(View.OnClickListener listener) {
        if (listener!=null) {
            leftButton.setOnClickListener(listener);
        }
    }

    public void setTitleText(String title) {
        titleTextView.setText(title);
    }

    public void setMessageText( String message) {
        descriptTextView.setText(message);
    }

    public void setTitleTextViewIsShow(boolean isShow){
        if(isShow){
            titleTextView.setVisibility(View.VISIBLE);
        }else {
            titleTextView.setText("");
        }
    }

    public void setButtonText(String left, String right) {
        leftButton.setText(left);
        rightButton.setText(right);
    }

    public void setImageVisiable(boolean isVisiable){
        if(isVisiable){
            icon.setVisibility(View.VISIBLE);
        }else {
            icon.setVisibility(View.GONE);
        }
    }

}
