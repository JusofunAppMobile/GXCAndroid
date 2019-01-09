package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.LoginConstant;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.route.LoginRegisterHelper;
import com.jusfoun.jusfouninquire.service.event.CompleteLoginEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.util.Md5Util;
import com.jusfoun.jusfouninquire.ui.util.RegularUtils;
import com.jusfoun.jusfouninquire.ui.view.LineScaleManager;
import com.jusfoun.jusfouninquire.ui.view.LineScaleView;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.widget.CountDownUtils;
import com.jusfoun.jusfouninquire.ui.widget.GeneralDialog;

import java.util.HashMap;
import java.util.Map;

import static com.jusfoun.jusfouninquire.R.id.auth_code_Edit;

/**
 * @author zhaoyapeng
 * @version create time:15/10/30下午4:07
 * @Email zyp@jusfoun.com
 * @Description ${注册页面}
 */
public class RegisterPhoneActivity extends BaseInquireActivity {
    /**
     * 常量
     */
    public static final String REGISTER_TAG = "isFromRegister";

    /**
     * 组件
     */
    private Button infoBtn;
    private EditText phoneNumEdit, phoneAuthCodeEdit;
    private TextView getAuthCodeBtn, goLoginBtn;
    private TitleView titleView;
    private LinearLayout linearLayout;
    private LineScaleView vLine1, vLine2;


    /**
     * 变量
     */
    private String phoneNum, authCode;

    private boolean isFromRegister = false;

    /**
     * 对象
     */
    private CountDownUtils countUtils;
    private GeneralDialog dialog;

    @Override
    protected void initData() {
        super.initData();
        isFromRegister = getIntent().getBooleanExtra(REGISTER_TAG, false);
        dialog = new GeneralDialog(mContext, R.style.my_dialog);
//        mSwipeBackLayout.setEnableGesture(true);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_register);
        titleView = (TitleView) findViewById(R.id.titleView);
//        titleView.setLeftImage(R.mipmap.back_image);
        titleView.setTitle("注册");
        phoneNumEdit = (EditText) findViewById(R.id.phoneNumEdit);
        phoneAuthCodeEdit = (EditText) findViewById(auth_code_Edit);
        getAuthCodeBtn = (TextView) findViewById(R.id.getAuthCode);
        goLoginBtn = (TextView) findViewById(R.id.go_login_btn);
        infoBtn = (Button) findViewById(R.id.btn_info);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        vLine1 = (LineScaleView) findViewById(R.id.vLine1);
        vLine2 = (LineScaleView) findViewById(R.id.vLine2);

        Map<EditText, LineScaleView> map = new HashMap<>();
        map.put(phoneNumEdit, vLine1);
        map.put(phoneAuthCodeEdit, vLine2);
        new LineScaleManager().setEditText(map);
    }

    @Override
    protected void initWidgetActions() {
        if (isFromRegister) {
            infoBtn.setText("补充信息 完成注册");
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            infoBtn.setText("确定");
            linearLayout.setVisibility(View.GONE);
        }

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifacationAuthCode();
//                Intent intent = new Intent(mContext,SupplementRegisterInfoActivity.class);
//                intent.putExtra(SupplementRegisterInfoActivity.REGISTER_TAG,true);
//                startActivity(intent);
            }
        });

        getAuthCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAuthCode();
            }
        });

        goLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    if (isFromRegister) {
                        dialog.setButtonText("取消", "去登录");
                        dialog.setMessageText("手机号已注册，请直接登录");
                        dialog.setTitleTextViewIsShow(false);
                        dialog.setRightListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        dialog.setLeftListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    } else {
                        showToast("该手机号已被注册");
                    }
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
        HashMap<String, String> map = new HashMap<>();
        map.put("phonenumber", phoneNum);
        map.put("Verifcode", authCode);
        showLoading();
        LoginRegisterHelper.doNetGETVerification(mContext, map, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                BaseModel model = (BaseModel) data;
                if (model.getResult() == 0) {
                    if (isFromRegister) {
                        Intent intent = new Intent(mContext, SupplementRegisterInfoActivity.class);
                        intent.putExtra(SupplementRegisterInfoActivity.PHONE_NUM_KEY, phoneNum);
                        intent.putExtra(SupplementRegisterInfoActivity.REGISTER_TAG, true);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra(SupplementRegisterInfoActivity.PHONE_NUM_KEY, phoneNum);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

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

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof CompleteLoginEvent) {
            if (((CompleteLoginEvent) event).getIsLogin() == LoginConstant.REGISTER) {
                finish();
            }
        }
    }
}
