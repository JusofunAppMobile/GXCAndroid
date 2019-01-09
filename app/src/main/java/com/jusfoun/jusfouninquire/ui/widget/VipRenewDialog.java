package com.jusfoun.jusfouninquire.ui.widget;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.activity.BaseInquireActivity;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;


/**
 * @Description VIP续费对话框
 */
public class VipRenewDialog extends Dialog implements View.OnClickListener {

    private BaseInquireActivity activity;
    private TextView tipText;
    private LinearLayout mailLayout;



    private final static String EMAIL_LOG = "email_log";

    public VipRenewDialog(BaseInquireActivity activity) {
        super(activity);
        this.activity = activity;
        initViews();
    }

    private void initViews() {
        setContentView(R.layout.dialog_email_send);
        findViewById(R.id.vCancel).setOnClickListener(this);
        findViewById(R.id.vSure).setOnClickListener(this);
        tipText = (TextView) findViewById(R.id.text_vip_tip);
        mailLayout = (LinearLayout) findViewById(R.id.layout_mail);

        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (PhoneUtil.getDisplayWidth(activity) * 1);
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);

        tipText.setVisibility(View.VISIBLE);
        mailLayout.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vCancel:
                if (callBack != null) {
                    callBack.leftOnClick();
                } else {
                    dismiss();
                }
                break;
            case R.id.vSure:
                if (callBack != null) {
                    callBack.rightOnClick();
                }
                break;
        }
    }

    public interface CallBack {
        void leftOnClick();

        void rightOnClick();
    }

    public CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
