package com.jusfoun.jusfouninquire.ui.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.util.TouchUtil;
import com.jusfoun.jusfouninquire.ui.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.util.KeyboardStatusDetector;
import com.jusfoun.jusfouninquire.ui.util.LogUtil;

/**
 * @author lee
 * @version create time:2015/11/169:54
 * @Email email
 * @Description $登录view
 */

public class LoginView extends LinearLayout {

    /**
     * 常量
     */
    public static final int LOGIN_BTN = 1;
    public static final int FORGET_BTN = 2;
    public static final int REGISTER_BTN = 3;
    public static final int WECHAT_LOGIN_BTN = 4;
    public static final int WEIBO_LOGIN_BTN = 5;


    /**
     * 组件
     */
    private EditText accountEdit, passwordEdit;

    private Button loginBtn;

    private TextView registerBtn, forgetPwdBtn;
    private View weiboLoginBtn, weChatLoginBtn;

    private ScrollView scrollView;


    /**
     * 对象
     */
    private Context mContext;
    private LayoutInflater inflater;
    private LoginViewListener loginViewlistener;
    /**
     * 变量
     */

    private String account, password;

    private RelativeLayout rootLayout;

    private View thirdLoginView, vEmpty;

    public LoginView(Context context) {
        super(context);
        mContext = context;
        inflater = LayoutInflater.from(context);
        initView();
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        inflater = LayoutInflater.from(context);
        initView();
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        inflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.acitvity_login, this, true);
        accountEdit = (EditText) view.findViewById(R.id.accountEdit);
        passwordEdit = (EditText) view.findViewById(R.id.passwordEdit);
        loginBtn = (Button) view.findViewById(R.id.login_btn);
        registerBtn = (TextView) view.findViewById(R.id.register_text_btn);
        forgetPwdBtn = (TextView) view.findViewById(R.id.forget_pwd_text_btn);
        weiboLoginBtn = view.findViewById(R.id.weibo_Text_btn);
        weChatLoginBtn = view.findViewById(R.id.wechat_Text_btn);
        rootLayout = (RelativeLayout) view.findViewById(R.id.layout_root);
        thirdLoginView = view.findViewById(R.id.view_third_login);
        vEmpty = view.findViewById(R.id.vEmpty);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);

        initViewAction();

        new KeyboardStatusDetector()
                .registerActivity((Activity) mContext)
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

    private void initViewAction() {
        TouchUtil.createTouchDelegate(forgetPwdBtn, 10);
        TouchUtil.createTouchDelegate(registerBtn, 10);
        TouchUtil.createTouchDelegate(weiboLoginBtn, 4);
        TouchUtil.createTouchDelegate(weChatLoginBtn, 4);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewlistener.viewOnClick(LOGIN_BTN);
            }
        });

        forgetPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewlistener.viewOnClick(FORGET_BTN);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewlistener.viewOnClick(REGISTER_BTN);
            }
        });

        weiboLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewlistener.viewOnClick(WEIBO_LOGIN_BTN);
            }
        });

        weChatLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "weChatLoginBtn");
                loginViewlistener.viewOnClick(WECHAT_LOGIN_BTN);
            }
        });

        if (!TextUtils.isEmpty(android.os.Build.BRAND) && android.os.Build.BRAND.contains("vivo")) {
            thirdLoginView.setVisibility(View.GONE);
            weiboLoginBtn.setVisibility(View.GONE);
        }

        findViewById(R.id.vEye).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                passwordEdit.setTransformationMethod(v.isSelected() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                passwordEdit.setSelection(passwordEdit.getText().length());
            }
        });
    }

    public void setLoginBtnEnble(boolean enble) {
        if (enble) {
            loginBtn.setEnabled(true);
        } else {
            loginBtn.setEnabled(false);
        }
    }

    public String getAccountEdit() {
        return accountEdit.getText().toString().trim();
    }

    public String getPasswordEdit() {
        return passwordEdit.getText().toString().trim();
    }

    public void setPasswordEdit(String string) {
        passwordEdit.setText(string);
    }

    public void setAccountEdit(String account) {
        if (!TextUtils.isEmpty(account)) {
            accountEdit.setText(account);
            accountEdit.setSelection(account.length());
        }

    }

    public void setLoginViewListener(LoginViewListener listener) {
        this.loginViewlistener = listener;
    }

    public interface LoginViewListener {
        public void viewOnClick(int viewId);

    }

    public void setPadding(int padding) {
//        rootLayout.setPadding(0, padding, 0, 0);
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
