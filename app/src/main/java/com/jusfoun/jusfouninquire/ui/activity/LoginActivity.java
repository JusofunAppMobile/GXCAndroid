package com.jusfoun.jusfouninquire.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.LoginConstant;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.LoginModel;
import com.jusfoun.jusfouninquire.net.route.LoginRegisterHelper;
import com.jusfoun.jusfouninquire.service.event.CompleteLoginEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.service.event.RefreshHomeEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LastLoginSharePreference;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.util.KeyboardStatusDetector;
import com.jusfoun.jusfouninquire.ui.util.LogUtil;
import com.jusfoun.jusfouninquire.ui.util.Md5Util;
import com.jusfoun.jusfouninquire.ui.util.ThirdLoginUtil;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.widget.BindPhoneDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

/**
 * @author lee
 * @version create time:2015/11/2015:10
 * @Email email
 * @Description $不常用的登录页面
 */

public class LoginActivity extends BaseInquireActivity {

    /**
     * 常量
     */
    public static final int LOGIN_BTN = 1;
    public static final int FORGET_BTN = 2;
    public static final int REGISTER_BTN = 3;
    public static final int WECHAT_LOGIN_BTN = 4;
    public static final int WEIBO_LOGIN_BTN = 5;

    public static final String FROM_MY_FOCUS = "from_my_focus";
    public static final String USER_INFO = "user_info";
    private boolean mNeedBack;


    /**
     * 组件
     */
    private EditText accountEdit, passwordEdit;

    private Button loginBtn;

    private TextView registerBtn, forgetPwdBtn;
    private View vRoot, vShare;

    /**
     * 对象
     */
    private LayoutInflater inflater;
    /**
     * 变量
     */

    private String account, password;

    private TitleView titleView;

    private ScrollView scrollView;
    private View vEmpty;


    @Override
    protected void initData() {
        super.initData();
        if (getIntent() != null) {
            mNeedBack = getIntent().getBooleanExtra(FROM_MY_FOCUS, false);
        }
    }

    @Override
    protected void initView() {
        setStatusBarEnable(Color.TRANSPARENT);
        setContentView(R.layout.acitvity_login);
        accountEdit = (EditText) findViewById(R.id.accountEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        loginBtn = (Button) findViewById(R.id.login_btn);
        registerBtn = (TextView) findViewById(R.id.register_text_btn);
        forgetPwdBtn = (TextView) findViewById(R.id.forget_pwd_text_btn);
        titleView = (TitleView) findViewById(R.id.titleView);


        vEmpty = findViewById(R.id.vEmpty);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        vRoot = findViewById(R.id.layout_root);
        vShare = findViewById(R.id.vShare);
//        vRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int heightDiff = vRoot.getRootView().getHeight() - vRoot.getHeight();
//                if (heightDiff > dpToPx(200)) {
//                    // 显示软键盘
//                    vShare.setVisibility(View.GONE);
//                    LogUtil.e("Login", "显示");
//                } else {
//                    //隐藏软键盘
//                    vShare.setVisibility(View.VISIBLE);
//                    LogUtil.e("Login", "隐藏");
//                }
//            }
//        });

        titleView.setVisibility(View.VISIBLE);
        titleView.setLeftImage(R.drawable.img_back_white);
        titleView.setBackgroundTranlate();

        new KeyboardStatusDetector()
                .registerActivity(this)
                .setListener(new KeyboardStatusDetector.OnKeyboardListener() {
                    @Override
                    public void onVisibilityChanged(boolean keyboardVisible) {
                        handleStatus(keyboardVisible);
                    }
                });

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // vEmpty 未显示时， 可以滚动
                return vEmpty.getVisibility() != View.GONE;
            }
        });
    }

    @Override
    public boolean isSetStatusBar() {
        return false;
    }

    @Override
    public boolean isBarDark() {
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            vShare.setVisibility(View.GONE);
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            vShare.setVisibility(View.VISIBLE);
        }
    }

    public float dpToPx(float valueInDp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    @Override
    protected void initWidgetActions() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regularLogin();
            }
        });

        forgetPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(ForgetPwdActivity.class);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RegisterPhoneActivity.class);
                intent.putExtra(RegisterPhoneActivity.REGISTER_TAG, true);
                startActivity(intent);
            }
        });

        findViewById(R.id.weibo_Text_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThirdLoginUtil.getThirdLoginUtil(mContext).thirdLogin(new ThirdLoginUtil.Callback() {
                    @Override
                    public void callback(String openid, String nickname, String headImg, String unionid) {
                        Log.e("tag", "openid-" + openid + "nickname-" + nickname + "headImg-" + headImg + " unionid=" + unionid);
                        loginFromOther(NetConstant.TYPE_SINA, openid, nickname, headImg, unionid);
                    }
                }, SHARE_MEDIA.SINA);
            }
        });

        findViewById(R.id.wechat_Text_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThirdLoginUtil.getThirdLoginUtil(mContext).thirdLogin(new ThirdLoginUtil.Callback() {
                    @Override
                    public void callback(String openid, String nickname, String headImg, String unionid) {
                        Log.e("tag", "uid-" + openid + "nickname-" + nickname + "headImg-" + headImg);
                        loginFromOther(NetConstant.TYPE_WECHAT, openid, nickname, headImg, unionid);
                    }
                }, SHARE_MEDIA.WEIXIN);

            }
        });

        findViewById(R.id.vEye).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                passwordEdit.setTransformationMethod(v.isSelected() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                passwordEdit.setSelection(passwordEdit.getText().length());
            }
        });
    }

    /**
     * 第三方登录
     *
     * @param type
     * @param nikeName
     * @param photo
     */
    private void loginFromOther(String type, String openid, String nikeName, String photo, String unionid) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("thirdToken", openid);
        map.put("logintype", type);
        map.put("pushid", JPushInterface.getRegistrationID(mContext));
        map.put("nickname", nikeName);
        map.put("photo", photo);
        map.put("unionid", unionid);

        showLoading();
        LoginRegisterHelper.doNetPostToLogin(mContext, map, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                LoginModel model = (LoginModel) data;
                if (model.getResult() == 0) {

                    if (TextUtils.isEmpty(model.getUserinfo().getMobile())) {
                        new BindPhoneDialog(LoginActivity.this, model.getUserinfo().getUserid(), new BindPhoneDialog.OnSuccessListener() {
                            @Override
                            public void suc(LoginModel model) {
                                loginSucByThird(model);
                            }
                        }).show();
                    } else {
                        loginSucByThird(model);
                    }

                   /* if (mNeedBack){
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra(USER_INFO,new Gson().toJson(model.getUserinfo()));
                        setResult(RESULT_OK, intent);
                        finish();
                    }*/
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

    private void loginSucByThird(LoginModel model) {
        LoginSharePreference.saveUserInfo(model.getUserinfo(), mContext);
        InquireApplication.setUserInfo(model.getUserinfo());
        EventBus.getDefault().post(new CompleteLoginEvent(LoginConstant.REGISTER));
        EventBus.getDefault().post(new RefreshHomeEvent());
        finish();
    }


    /**
     * 正常登录
     */
    private void regularLogin() {
        account = accountEdit.getText().toString().trim();
        password = passwordEdit.getText().toString().trim();

        if (TextUtils.isEmpty(account)) {
            showToast("请输入帐号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast("请输入密码");
            return;
        }

        showLoading();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("phonenumber", account);
        map.put("password", Md5Util.getMD5Str(password));
        map.put("logintype", NetConstant.TYPE_PHONE);
        map.put("pushid", JPushInterface.getRegistrationID(mContext));
        Log.e("tag", "map=" + map);
        LoginRegisterHelper.doNetPostToLogin(mContext, map, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                LoginModel model = (LoginModel) data;
                if (model.getResult() == 0) {
                    LastLoginSharePreference.saveUserAccount(account, mContext);

                    LoginSharePreference.saveUserInfo(model.getUserinfo(), mContext);
                    InquireApplication.setUserInfo(model.getUserinfo());
                    //登录成功之后，设置个人中心
                    EventBus.getDefault().post(new CompleteLoginEvent(LoginConstant.REGISTER));
                    EventBus.getDefault().post(new RefreshHomeEvent());
                    if (mNeedBack) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra(USER_INFO, new Gson().toJson(model.getUserinfo()));
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        finish();
                    }
                } else {
                    showToast(model.getMsg() + "");
                }
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof CompleteLoginEvent) {
            //登录状态：本页面+其他页面登录成功或注册成功||||退出状态：本页面退出成功
            if (((CompleteLoginEvent) event).getIsLogin() == LoginConstant.REGISTER) {
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ThirdLoginUtil.getThirdLoginUtil(mContext).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isShouldHideKeyboard(accountEdit, ev) && isShouldHideKeyboard(passwordEdit, ev)) {
                AppUtil.hideSoftInput((Activity) mContext);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * @param show 当前软键盘是否显示
     */
    private void handleStatus(boolean show) {
        if (show) {
            LogUtil.e("abc", "-------软键盘显示---------");
            vEmpty.setVisibility(View.VISIBLE);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    scrollView.smoothScrollTo(0, vEmpty.getTop());
                }
            });
        } else {
            LogUtil.e("abc", "-------软键盘隐藏---------");
            vEmpty.setVisibility(View.GONE);
        }
    }
}
