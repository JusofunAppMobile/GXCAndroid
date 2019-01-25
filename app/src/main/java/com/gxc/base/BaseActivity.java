package com.gxc.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gxc.event.FinishEvent;
import com.gxc.ui.dialog.LoadingDialog;
import com.gxc.utils.AppUtils;
import com.gxc.utils.LogUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.util.RegexUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public abstract class BaseActivity extends AppCompatActivity {

    protected Activity activity;

    public boolean isDestory;

    protected Gson gson = new Gson();
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        setStatusBarEnable();

        if (isBarDark())
            setStatusBarFontDark(true);

        initDialog();
        initActions();
    }

    protected abstract int getLayoutId();

    public abstract void initActions();

    public boolean isSetStatusBar() {
        return true;
    }

    /**
     * 沉侵式状态栏设置
     */
    public void setStatusBarEnable(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        if (isSetStatusBar()) {
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(color);

        }
        if (getVertualKeyboardBgColor() != 0) {
            tintManager.setNavigationBarTintEnabled(true);
            //  虚拟键盘的颜色
            tintManager.setNavigationBarTintColor(getVertualKeyboardBgColor());
        }
    }

    public void setStatusBarEnable() {
//        setStatusBarEnable(ContextCompat.getColor(this, R.color.common_deeper));
        setStatusBarEnable(Color.parseColor("#FFFFFF"));
    }


    public void setStatusBarRed() {
        setStatusBarEnable(ContextCompat.getColor(this, R.color.common_red));
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

    }

    /**
     * 获取虚拟键盘背景颜色，如果为0，不设置
     *
     * @return
     */
    protected int getVertualKeyboardBgColor() {
        return Color.TRANSPARENT;
    }

    /**
     * 接收event 重写此方法
     */
    public void onEvent(IEvent event) {
        LogUtils.e("onEvent++++>>" + event.getClass().getSimpleName() + "<<" + getClass().getSimpleName());
        if (event instanceof FinishEvent)
            finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestory = true;
        EventBus.getDefault().unregister(this);
    }


    /**
     * 显示一个Toast类型的消息
     *
     * @param msg 显示的消息
     */
    public void showToast(String msg) {
        ToastUtils.show(msg);
    }

    /**
     * 显示{@link Toast}通知
     *
     * @param strResId 字符串资源id
     */
    public void showToast(final int strResId) {
        showToast(getResources().getString(strResId));
    }


    /**
     * 获取Activity根View
     *
     * @param context
     * @return
     */
    protected View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    protected String getValue(EditText et) {
        return et.getText().toString();
    }

    protected String getValue(TextView et) {
        return et.getText().toString();
    }


    protected void setFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void busPost(Object object) {
        EventBus.getDefault().post(object);
    }

    /**
     * 从启动页、广告页进入主页设置成无动画的效果
     */
    protected void setIntentNoAnim(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }


    public View inflate(int id) {
        return View.inflate(this, id, null);
    }

    public boolean isEmpty(String str) {
        return str == null || TextUtils.isEmpty(str.trim());
    }

    public boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public boolean isEmpty(TextView textView) {
        return TextUtils.isEmpty(textView.getText().toString().trim());
    }

    private long lastBackPressTime;
    private int pressTime;

    private boolean doubleClickExit;

    /**
     * 连续点击两次返回按钮是否退出应用
     *
     * @param exit 是否退出应用
     */
    protected void setDoubleClickExitApp(boolean exit) {
        doubleClickExit = exit;
    }

    private boolean isGoExitApp = false;

    @Override
    public void onBackPressed() {
        if (doubleClickExit) {
            long time = System.currentTimeMillis();
            if (time - lastBackPressTime < 3 * 1000 && pressTime == 1) {
                isGoExitApp = true;
                busPost(new FinishEvent());
            } else {
                pressTime = 1;
                lastBackPressTime = time;
                showToast(getString(R.string.doublExitApp));
            }
        } else
            super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isGoExitApp = false;
    }

    protected void startActivity(Class<?> clazz) {
        startActivity(clazz, getIntent().getStringExtra("title"));
    }

    protected void startActivity(Class<?> clazz, String title) {
        startActivity(clazz, null, title);
    }

    protected void startActivity(Class<?> clazz, Bundle bundle) {
        startActivity(clazz, bundle, null);
    }

    protected void startActivity(Class<?> clazz, Bundle bundle, String title) {
        if (!TextUtils.isEmpty(title)) {
            if (bundle == null)
                bundle = new Bundle();
            bundle.putString("title", title);
        }
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
        if (doubleClickExit)
            overridePendingTransition(0, 0);
    }

    public boolean isBarDark() {
        return true;
    }

    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体和图标是否为深色
     */
    public void setStatusBarFontDark(boolean dark) {
        // 小米MIUI
        try {
            Window window = getWindow();
            Class clazz = getWindow().getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {       //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }

        // 魅族FlymeUI
        try {
            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
//            e.printStackTrace();
        }

//         android6.0+系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark)
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    protected void initDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this, R.style.my_dialog);
            loadingDialog.setCancelable(true);
            loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (loadingDialog != null) {
                            loadingDialog.cancel();
                            try {
//                                VolleyUtil.getQueue(BaseActivity.this).cancelAll(getLocalClassName());

                            } catch (Exception e) {

                            }
                        }
                    }
                    return true;
                }
            });
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    public void showLoading() {
        if ((!isFinishing())) {
            if (loadingDialog != null && !loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }

    }

    public boolean isEmptyAndToast(TextView editText, String value) {
        return isEmptyAndToast(getValue(editText), value);
    }

    public boolean isEmptyAndToast(String text, String value) {
        if (TextUtils.isEmpty(text)) {
            showToast(value);
            return true;
        }
        return false;
    }

    public boolean isPhoneValidAndToast(EditText editText) {
        if (!RegexUtils.checkMobile(getValue(editText))) {
            showToast("请输入正确的手机号码");
            return true;
        }
        return false;
    }

    // 隐藏加载对话框
    public void hideLoadDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 点击输入框以外的位置是否能隐藏软键盘
     */
    private boolean isTouchHideSoftkeyboard = false;
    private List<EditText> touchList;

    /**
     * 设置点击输入框以外的位置是否能隐藏软键盘,
     *
     * @param editTexts 页面中的所有 EditText，需要检测所有的 EditText 的位置，避免点击其他EditText 软键盘跳动现象
     */
    public void setTouchHideSoftKeyboard(EditText... editTexts) {
        if (editTexts != null && editTexts.length > 0) {
            touchList = new ArrayList<>();
            for (EditText et : editTexts)
                touchList.add(et);
            isTouchHideSoftkeyboard = true;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isTouchHideSoftkeyboard) return super.dispatchTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            for (EditText et : touchList) {
                int[] leftTop = {0, 0};
                //获取输入框当前的location位置
                et.getLocationInWindow(leftTop);
                int left = leftTop[0];
                int top = leftTop[1];
                int bottom = top + et.getHeight();
                int right = left + et.getWidth();
                if (event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void finishDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 400);
    }

    public void setStatusBar(int id) {
        View vStatus = findViewById(id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            vStatus.setVisibility(View.VISIBLE);
            vStatus.getLayoutParams().height = AppUtils.getStatusHeight();
        }
    }
}
