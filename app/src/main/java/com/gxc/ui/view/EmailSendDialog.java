package com.gxc.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.TimeOut;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.route.ReportRoute;
import com.jusfoun.jusfouninquire.service.event.RefreshMyReportEvent;
import com.jusfoun.jusfouninquire.ui.activity.BaseInquireActivity;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.util.RegularUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import netlib.util.PreferenceUtils;
import netlib.util.ToastUtils;

/**
 * @Description 发送邮件对话框
 */
public class EmailSendDialog extends Dialog implements View.OnClickListener {

    private Context activity;
    private EditText etEmail;

    private CallBack clickListener;

    /**
     * 发送企业通讯录
     *
     * @param activity
     * @param entId
     */
    public EmailSendDialog(Context activity, CallBack callBack) {
        super(activity);
        this.activity = activity;
        this.clickListener=callBack;
        initViews();
    }

    private void initViews() {
        setContentView(R.layout.dialog_email_send);
        findViewById(R.id.vCancel).setOnClickListener(this);
        findViewById(R.id.vSure).setOnClickListener(this);
        etEmail = (EditText) findViewById(R.id.etEmail);

        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (PhoneUtil.getDisplayWidth(activity) * 1);
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vCancel:
                dismiss();
                break;
            case R.id.vSure:
                String email = etEmail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    ToastUtils.show("请输入报告接收邮箱");
                    return;
                } else if (!RegularUtils.checkEmail(email)) {
                    ToastUtils.show("邮箱格式不正确");
                    return;
                }

                if(clickListener!=null) {
                    clickListener.onClick(email);
                }
                break;
        }
    }


    public interface  CallBack{
        void onClick(String email);
    }

    public  CallBack callBack;

}
