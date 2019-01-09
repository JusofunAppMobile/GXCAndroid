package com.jusfoun.jusfouninquire.ui.widget;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.model.LoginModel;
import com.jusfoun.jusfouninquire.net.route.LoginRegisterHelper;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.activity.BaseInquireActivity;
import com.jusfoun.jusfouninquire.ui.util.LogUtil;
import com.jusfoun.jusfouninquire.ui.util.Md5Util;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.util.RegexUtils;

import java.util.HashMap;

import netlib.util.SendMessage;
import netlib.util.ToastUtils;

import static com.jusfoun.bigdata.Toaster.showToast;

public class BindPhoneDialog extends Dialog implements View.OnClickListener {

    private BaseInquireActivity activity;

    private SendMessage sendMessage;

    private TextView vSendCode;
    private EditText etPhone, etCode;

    private OnSuccessListener listener;
    private String userid;

    public BindPhoneDialog(BaseInquireActivity activity, String userid, OnSuccessListener listener) {
        super(activity);
        this.activity = activity;
        this.listener = listener;
        this.userid = userid;
        initViews();
    }

    private void initViews() {
        setContentView(R.layout.dialog_bind_phone);
        findViewById(R.id.vSure).setOnClickListener(this);

        vSendCode = (TextView) findViewById(R.id.vSendCode);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etCode = (EditText) findViewById(R.id.etCode);
        vSendCode.setOnClickListener(this);
        findViewById(R.id.vCancel).setOnClickListener(this);

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (PhoneUtil.getDisplayWidth(activity) * 1);
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);

        sendMessage = SendMessage.newInstant(activity)
                .setClickView(vSendCode)
                .setInputView(etPhone)
                .setTipText("获取验证码")
                .setTime(60);
    }


    @Override
    public void onClick(View view) {
        String phone = etPhone.getText().toString();
        String code = etCode.getText().toString();
        switch (view.getId()) {
            case R.id.vCancel:
                dismiss();
                break;
            case R.id.vSendCode:
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号");
                    return;
                }
                if (!RegexUtils.checkMobile(phone)) {
                    showToast("请输入正确的手机号码");
                    return;
                }

                sendMessage.start();
                //  发送短信

                HashMap<String, String> map = new HashMap<>();
                map.put("phonenumber", phone);
                String random = getRandom();
                map.put("ran", random);
                map.put("encryption", Md5Util.getMD5Str(phone + random + "jiucifang"));
                LoginRegisterHelper.verifyCodeForByMd5(activity, map, new NetWorkCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        BaseModel model = (BaseModel) data;
                        if (model.getResult() == 0) {
                            showToast("手机验证码已发送成功，请注意查收");
                        } else {
                            showToast(model.getMsg());
                            sendMessage.reset();
                        }
                    }

                    @Override
                    public void onFail(String error) {
                        sendMessage.reset();
                        ToastUtils.showHttpError();
                    }
                });

                break;
            case R.id.vSure:

                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号");
                    return;
                }
                if (!RegexUtils.checkMobile(phone)) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    showToast("请输入验证码");
                    return;
                }

                HashMap<String, String> pmap = new HashMap<>();
                pmap.put("phone", phone);
                pmap.put("userid", userid);
                pmap.put("securitycode", code);
                LoginRegisterHelper.bindingPhone(activity, pmap, new NetWorkCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        LoginModel model = (LoginModel) data;
                        if (model.getResult() == 0) {
                            if (listener != null)
                                listener.suc(model);
                            dismiss();
                        } else {
                            showToast(model.getMsg());
                        }
                    }

                    @Override
                    public void onFail(String error) {
                        LogUtil.e("Bind", error);
                        ToastUtils.showHttpError();
                    }
                });
                break;
        }
    }

    public String getRandom() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 10000));
    }

    public interface OnSuccessListener {
        void suc(LoginModel model);
    }
}
