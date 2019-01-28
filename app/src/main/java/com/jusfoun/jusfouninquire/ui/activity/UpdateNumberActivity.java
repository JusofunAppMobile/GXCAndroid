package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.route.LoginRegisterHelper;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.util.Md5Util;
import com.jusfoun.jusfouninquire.ui.util.RegularUtils;
import com.jusfoun.jusfouninquire.ui.view.LineScaleManager;
import com.jusfoun.jusfouninquire.ui.view.LineScaleView;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.widget.CountDownUtils;
import com.jusfoun.jusfouninquire.ui.widget.GeneralDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * Author  wangchenchen
 * CreateDate 2016/8/12.
 * Email wcc@jusfoun.com
 * Description 修改手机号
 */
public class UpdateNumberActivity extends BaseInquireActivity {
    /**
     * 常量
     */
    public static final String REGISTER_TAG = "isFromRegister";

    /**
     * 组件
     */
    private Button infoBtn;
    private EditText phoneNumEdit, phoneAuthCodeEdit;
    private TextView getAuthCodeBtn;
    private TitleView titleView;
    private LineScaleView vLine1, vLine2;

    /**
     * 变量
     */
    private String phoneNum, authCode;

    /**
     * 对象
     */
    private CountDownUtils countUtils;
    private GeneralDialog dialog;

    @Override
    protected void initData() {
        super.initData();
        dialog = new GeneralDialog(mContext, R.style.my_dialog);
//        mSwipeBackLayout.setEnableGesture(true);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_update_number);
        titleView = (TitleView) findViewById(R.id.titleView);
        phoneNumEdit = (EditText) findViewById(R.id.phoneNumEdit);
        phoneAuthCodeEdit = (EditText) findViewById(R.id.auth_code_Edit);
        getAuthCodeBtn = (TextView) findViewById(R.id.getAuthCode);
        infoBtn = (Button) findViewById(R.id.btn_info);
        vLine1 = (LineScaleView) findViewById(R.id.vLine1);
        vLine2 = (LineScaleView) findViewById(R.id.vLine2);

        Map<EditText, LineScaleView> map = new HashMap<>();
        map.put(phoneNumEdit, vLine1);
        map.put(phoneAuthCodeEdit, vLine2);
        new LineScaleManager().setEditText(map);
    }

    @Override
    protected void initWidgetActions() {

        titleView.setTitle("修改手机号码");
//        titleView.setLeftText("返回");
//        titleView.setLeftTextColor(Color.parseColor("#676767"));
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifacationAuthCode();
            }
        });

        getAuthCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAuthCode();
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getAuthCode() {
        phoneNum = phoneNumEdit.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            showToast("请输入手机号");
            return;
        }

        if (!RegularUtils.checkMobile(phoneNum)) {
            showToast("请检查你的手机号");
            return;
        }

        String temp = Md5Util.getRandom(4);

        HashMap<String, String> map = new HashMap<>();
        map.put("phonenumber", phoneNum);
        map.put("ran", temp);
        map.put("encryption", Md5Util.getMD5Str(phoneNum + temp + "jiucifang"));
        showLoading();
        LoginRegisterHelper.getVerificationMD5(mContext, map, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                BaseModel model = (BaseModel) data;
                if (model.getResult() == 0) {
                    countUtils = new CountDownUtils(60 * 1000, 1000, getAuthCodeBtn);
                    countUtils.start();
                    showToast(model.getMsg() + "");
                } else if (model.getResult() == 6) {
                    showToast("该手机号已被注册");
                } else {
                    showToast(model.getMsg() + "");
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
     * 验证验证码
     */
    private void verifacationAuthCode() {
        phoneNum = phoneNumEdit.getText().toString().trim();
        authCode = phoneAuthCodeEdit.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            showToast("请输入手机号");
            return;
        }

        if (!RegularUtils.checkMobile(phoneNum)) {
            showToast("请检查你的手机号");
            return;
        }

        if (TextUtils.isEmpty(authCode)) {
            showToast("请输入验证码");
            return;
        }
        if (LoginSharePreference.getUserInfo(mContext) == null) {
            showToast("请先登录");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("newphone", phoneNum);
        map.put("code", authCode);
        map.put("userid", LoginSharePreference.getUserInfo(mContext).getUserid());
        showLoading();
        LoginRegisterHelper.updateNumber(mContext, map, getClass().getSimpleName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                BaseModel model = (BaseModel) data;
                if (model.getResult() == 0) {
                    showToast("修改成功");
                    Intent intent = new Intent();
                    intent.putExtra(EditPersonActivity.PHONE_NUM_KEY, phoneNum);
                    setResult(RESULT_OK, intent);
                    onBackPressed();

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
}
