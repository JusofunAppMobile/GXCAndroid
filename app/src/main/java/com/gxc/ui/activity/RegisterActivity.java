package com.gxc.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.constants.Constants;
import com.gxc.event.LoginChangeEvent;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.utils.DESUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.TimeOut;
import com.jusfoun.jusfouninquire.ui.util.RegexUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import netlib.util.PreferenceUtils;
import netlib.util.SendMessage;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 16:49
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.vSendCode)
    TextView vSendCode;

    private SendMessage sendMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.act_register;
    }

    @Override
    public void initActions() {
        sendMessage = SendMessage.newInstant(activity)
                .setClickView(vSendCode)
                .setInputView(etPhone)
                .setTipText("获取验证码")
                .setTime(60);

        setTouchHideSoftKeyboard(etPhone, etPassword, etCode);
    }

    private void register() {
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(getValue(etCode))) {
            showToast("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(getValue(etPassword))) {
            showToast("请输入密码");
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", getValue(etCode));
        map.put("password", DESUtils.encryptDES(getValue(etPassword), new TimeOut(this).getGCXkey()));
        map.put("regId", PreferenceUtils.getString(activity, Constants.REGID));
        RxManager.http(RetrofitUtils.getApi().registerApp(map), new ResponseCall() {


            @Override
            public void success(NetModel model) {
                if (model.success()) {
                    showToast("注册成功");
                    PreferenceUtils.setString(activity, Constants.USER, gson.toJson(model.data));
                    EventBus.getDefault().post(new LoginChangeEvent());
                    finish();
                } else {
                    showToast(model.msg);
                    sendMessage.reset();
                }
            }

            @Override
            public void error() {
                sendMessage.reset();
                ToastUtils.showHttpError();
            }
        });
    }

    @OnClick({R.id.vLogin, R.id.vRegister, R.id.ivLeft})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vLogin:
                finish();
                break;
            case R.id.vRegister:
                register();
                break;
            case R.id.ivLeft:
                finish();
                break;
        }
    }

    @OnClick(R.id.vSendCode)
    public void sendCode() {
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showToast("请输入手机号");
            return;
        }
        if (!RegexUtils.checkMobile(phone)) {
            showToast("请输入正确的手机号码");
            return;
        }
        sendMessage.start();

        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("type", 1);
        RxManager.http(RetrofitUtils.getApi().sendMesCode(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                if (model.success()) {

                } else {
                    showToast(model.msg);
                    sendMessage.reset();
                }
            }

            @Override
            public void error() {
                sendMessage.reset();
                ToastUtils.showHttpError();
            }
        });
    }
}
