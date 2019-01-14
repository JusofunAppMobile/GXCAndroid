package com.gxc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.Group;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.util.RegexUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import netlib.util.SendMessage;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 17:18
 * @Email lgd@jusfoun.com
 * @Description ${绑定手机号}
 */
public class BindPhoneActivity extends BaseActivity {

    public static final int TYPE_FORGET_PASSWORD = 1; // 忘记密码
    public static final int TYPE_UPDATE_PHONE = 2; // 修改手机号
    public static final int TYPE_UPDATE_PASSWORD = 3; // 修改密码

    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.tvLabel)
    TextView tvLabel;
    @BindView(R.id.vSendCode)
    TextView vSendCode;
    @BindView(R.id.pwdGroup)
    Group pwdGroup;

    private SendMessage sendMessage;

    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.act_bind_phone;
    }

    @Override
    public void initActions() {
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case TYPE_FORGET_PASSWORD:
                tvLabel.setText("找回密码");
                break;
            case TYPE_UPDATE_PHONE:
                tvLabel.setText("绑定新手机号");
                pwdGroup.setVisibility(View.GONE);
                break;
            case TYPE_UPDATE_PASSWORD:
                tvLabel.setText("重置密码");
                break;
        }


        sendMessage = SendMessage.newInstant(activity)
                .setClickView(vSendCode)
                .setInputView(etPhone)
                .setTipText("获取验证码")
                .setTime(60);
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

    public static Intent getIntent(Context context, int type) {
        Intent intent = new Intent(context, BindPhoneActivity.class);
        intent.putExtra("type", type);
        return intent;
    }

    @OnClick({R.id.ivLeft, R.id.vSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.vSubmit:
                break;
        }
    }
}
