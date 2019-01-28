package com.jusfoun.jusfouninquire.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.route.LoginRegisterHelper;
import com.jusfoun.jusfouninquire.ui.util.KeyBoardUtil;
import com.jusfoun.jusfouninquire.ui.util.Md5Util;
import com.jusfoun.jusfouninquire.ui.util.RegularUtils;
import com.jusfoun.jusfouninquire.ui.view.LineScaleManager;
import com.jusfoun.jusfouninquire.ui.view.LineScaleView;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.widget.CountDownUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * @author lee
 * @version create time:2015/11/1016:01
 * @Email email
 * @Description $忘记密码
 */

public class ForgetPwdActivity extends BaseInquireActivity {
    /**常量*/
    /**组件*/
    private EditText phoneEdit,verificationEdit,newPasswrodEdit;
    private Button submitBtn;

    private TitleView titleView;
    private TextView getAuthCode;
    /**变量*/
    private String phoneNum,VerificationCode,newPassword;

    /**对象*/
    private CountDownUtils countUtils;

    private LineScaleView vLine1, vLine2, vLine3;

    @Override
    protected void initData() {
        super.initData();
//        mSwipeBackLayout.setEnableGesture(true);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_forget_password);
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("找回密码");
        phoneEdit = (EditText) findViewById(R.id.phoneNumEdit);
        getAuthCode = (TextView) findViewById(R.id.getAuthCode);
        verificationEdit = (EditText) findViewById(R.id.auth_code_Edit);
        newPasswrodEdit = (EditText) findViewById(R.id.passwordEdit);

        submitBtn = (Button) findViewById(R.id.btn_info);

        vLine1 = (LineScaleView) findViewById(R.id.vLine1);
        vLine2 = (LineScaleView) findViewById(R.id.vLine2);
        vLine3 = (LineScaleView) findViewById(R.id.vLine3);

        Map<EditText, LineScaleView> map = new HashMap<>();
        map.put(phoneEdit, vLine1);
        map.put(verificationEdit, vLine2);
        map.put(newPasswrodEdit, vLine3);
        new LineScaleManager().setEditText(map);
    }


    @Override
    protected void initWidgetActions() {
        getAuthCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForgetAuthCode();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitNewPassword();
            }
        });
    }

    private void getForgetAuthCode(){
        phoneNum = phoneEdit.getText().toString().trim();
        if(TextUtils.isEmpty(phoneNum)){
            showToast("请输入手机号");
            return;
        }
        if(!RegularUtils.checkMobile(phoneNum)){
            showToast("手机号格式有误，请检查");
            return;
        }

       String temp = Md5Util.getRandom(4);
        HashMap<String,String> map = new HashMap<>();
        map.put("phonenumber",phoneNum);
        map.put("ran", temp + "");
        map.put("encryption", Md5Util.getMD5Str(phoneNum + temp + "jiucifang"));
        KeyBoardUtil.hideSoftKeyboard(this);
        showLoading();
        LoginRegisterHelper.getForgetPwdAuthCodeMD5(mContext, map,getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                BaseModel model = (BaseModel) data;
                if (model.getResult() == 0) {
                    countUtils = new CountDownUtils(60 * 1000, 1000, getAuthCode);
                    countUtils.start();
                    showToast(model.getMsg());
                } else {
                    showToast(model.getMsg());
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast(R.string.net_error);
            }
        });

    }

    /**
     * 提交 修改的密码
     */
    private void submitNewPassword(){
        phoneNum = phoneEdit.getText().toString().trim();

        VerificationCode = verificationEdit.getText().toString().trim();

        newPassword = newPasswrodEdit.getText().toString().trim();

        if(TextUtils.isEmpty(phoneNum)){
            showToast("请输入手机号");
            return;
        }
        if(!RegularUtils.checkMobile(phoneNum)){
            showToast("手机号格式有误，请检查");
            return;
        }
        if(TextUtils.isEmpty(VerificationCode)){
            showToast("请输入验证码");
            return;
        }
        if(TextUtils.isEmpty(newPassword)){
            showToast("请输入新密码");
            return;
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("phonenumber",phoneNum);
        map.put("verifcode",VerificationCode);
        map.put("password", Md5Util.getMD5Str(newPassword));
        showLoading();
        KeyBoardUtil.hideSoftKeyboard(this);
        submitBtn.setText("修改中...");
        LoginRegisterHelper.doNetPostForgetPwd(mContext, map,getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                BaseModel model = (BaseModel) data;
                submitBtn.setText("确定");
                if (model.getResult() == 0) {
                    showToast(model.getMsg());
                    finish();
                } else {
                    showToast(model.getMsg());
                }
            }

            @Override
            public void onFail(String error) {
                submitBtn.setText("确定");
                hideLoadDialog();
                showToast(R.string.net_error);
            }
        });

    }

}
