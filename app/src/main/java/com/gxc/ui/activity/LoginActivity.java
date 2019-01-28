package com.gxc.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gxc.base.BaseActivity;
import com.gxc.constants.Constants;
import com.gxc.event.LoginChangeEvent;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.utils.DESUtils;
import com.gxc.utils.ToastUtils;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.TimeOut;
import com.jusfoun.jusfouninquire.service.event.IEvent;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import netlib.util.PreferenceUtils;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 16:16
 * @Email lgd@jusfoun.com
 * @Description ${登录页面}
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.act_login;
    }

    @Override
    public void initActions() {
        setTouchHideSoftKeyboard(etPhone, etPassword);
    }

    private void login() {

        if (isEmptyAndToast(etPhone, "请输入手机号")) return;
        if (isPhoneValidAndToast(etPhone)) return;
        if (isEmptyAndToast(etPassword, "请输入密码")) return;

        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", getValue(etPhone));
        map.put("password", DESUtils.encryptDES(getValue(etPassword), new TimeOut(this).getGCXkey()));
        map.put("regId", PreferenceUtils.getString(activity, Constants.REGID));

        RxManager.http(RetrofitUtils.getApi().loginApp(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    showToast("登录成功");
                    PreferenceUtils.setString(activity, Constants.USER, gson.toJson(model.data));
                    EventBus.getDefault().post(new LoginChangeEvent());
                    finish();
                } else {
                    showToast(model.msg);
                }
            }

            @Override
            public void error() {
                hideLoadDialog();
                ToastUtils.showHttpError();
            }
        });

    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof LoginChangeEvent)
            finish();
    }

    @OnClick({R.id.ivLeft, R.id.vRegister, R.id.vLogin, R.id.tvForget, R.id.ivSina, R.id.ivWechat, R.id.ivQQ})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.vRegister:
                startActivity(RegisterActivity.class);
                break;
            case R.id.vLogin:
                login();
                break;
            case R.id.tvForget:
                startActivity(BindPhoneActivity.getIntent(this, BindPhoneActivity.TYPE_FORGET_PASSWORD));
                break;
            case R.id.ivSina:
                break;
            case R.id.ivWechat:
                startActivity(BindPhoneActivity.class);
                break;
            case R.id.ivQQ:
                startActivity(BindPhoneActivity.class);
                break;
        }
    }
}
