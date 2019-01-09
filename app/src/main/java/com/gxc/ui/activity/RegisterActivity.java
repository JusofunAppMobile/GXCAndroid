package com.gxc.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gxc.base.BaseActivity;
import com.jusfoun.jusfouninquire.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected int getLayoutId() {
        return R.layout.act_register;
    }

    @Override
    public void initActions() {

    }

    @OnClick({R.id.vLogin, R.id.vRegister,R.id.ivLeft})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vLogin:
                break;
            case R.id.vRegister:
                break;
            case R.id.ivLeft:
                finish();
                break;
        }
    }
}
