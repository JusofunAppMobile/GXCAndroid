package com.jusfoun.bigdata;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.util.ResourceUtil;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.greenrobot.event.EventBus;


/**
 * Activity基类
 * <p/>
 * 方法执行顺序
 * {@link #initData()} —> {@link #initView()} —> {@link #setListener()} —> {@link #doBusiness()}
 * <p/>
 */

public abstract class BaseActivity extends FragmentActivity implements IBaseActivity {

    protected final String TAG = getClass().getSimpleName();


    public Context context;
    /**
     * 当前Activity的弱引用，防止内存泄露
     */
    private WeakReference<Activity> mActivity;

    /**
     * 加载框
     */
    protected OverProgressDialog loadingDialog;

    /**
     * 换肤获取Resource
     */
    protected ResourceUtil resourceUtil;

    private InquireApplication mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        initBase();
        initDialog();

        initData();

        initView();

        setListener();
        doBusiness();
    }

    @Override
    public void setListener() {

    }

    private void initBase() {
        mActivity = new WeakReference<Activity>(this);
        if (InquireApplication.application != null) {
            mApplication = (InquireApplication) InquireApplication.application;
            mApplication.pushTask(mActivity);
        }
        resourceUtil = ResourceUtil.getInstance(getApplicationContext());
        EventBus.getDefault().register(this);
        if (isSetStatusBar())
            setStatusBarEnable();
        if (isBarDark())
            setStatusBarFontDark(true);
    }

    // Event bus会反射回这个方法。
    public void onEvent(IEvent event) {
//        // 如果有需要事件传递，则重写此方法。
//        if (event instanceof FinishAllActivityEvent) {
//            finish();
//        } else if (event instanceof DataColumnEvent) { // 数据栏目 结束页面
//            finish();
//        }
    }

    // 初始化加载对话框
    protected void initDialog() {
        if (loadingDialog == null) {
            loadingDialog = new OverProgressDialog(this, R.style.my_dialog);
            loadingDialog.setCancelable(true);
            loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }
                    return true;
                }
            });
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    // 显示加载对话框
    protected void showLoadDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    // 隐藏加载对话框
    protected void hideLoadDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        hideLoadDialog();
        if (mApplication != null)
            mApplication.removeTask(mActivity);

        try {
            VolleyUtil.getQueue(this).cancelAll(getLocalClassName());
        } catch (Exception e) {

        }
    }

    /**
     * 获取当前Activity
     *
     * @return
     */
    protected Activity getActivity() {
        if (null != mActivity)
            return mActivity.get();
        else
            return null;
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
        setStatusBarEnable(Color.parseColor("#FFFFFF"));
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
        return Color.BLACK;
    }

    public boolean isSetStatusBar() {
        return true;
    }

    public boolean isBarDark() {
        return true;
    }

    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体和图标是否为深色
     */
    protected void setStatusBarFontDark(boolean dark) {
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
}
