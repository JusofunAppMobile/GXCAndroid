package com.gxc.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gxc.base.BaseActivity;
import com.jusfoun.jusfouninquire.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 17:18
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class BindPhoneActivity extends BaseActivity {
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etCode)
    EditText etCode;

    @Override
    protected int getLayoutId() {
        return R.layout.act_bind_phone;
    }

    @Override
    public void initActions() {

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
