package com.jusfoun.jusfouninquire.ui.activity;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.mobstat.StatService;
import com.gxc.constants.Constants;
import com.gxc.ui.activity.HomeActivity;
import com.gxc.ui.activity.WelcomeActivity;
import com.jusfoun.jusfouninquire.JniGXCUtil;
import com.jusfoun.jusfouninquire.ui.util.AppUtil;
import com.umeng.analytics.MobclickAgent;

import netlib.util.PreferenceUtils;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.jusfoun.jusfouninquire.ui.util.AppUtil.isPermissionDenied;
import static com.jusfoun.jusfouninquire.ui.util.AppUtil.showPermissionStorageDialog;


/**
 * @author lee
 * @version create time:2015/11/1214:07
 * @Email email
 * @Description $启动页
 */
@RuntimePermissions
public class SplashActivity extends BaseInquireActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        StatService.setDebugOn(false);
        StatService.start(this);

    }

    private void goNext() {
        try {
            MobclickAgent.setDebugMode(false);
            if (AppUtil.isApkInDebug(mContext)) {
                MobclickAgent.setCatchUncaughtExceptions(false);
            } else {
                MobclickAgent.setCatchUncaughtExceptions(true);
            }
        } catch (Exception e) {

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PreferenceUtils.getBoolean(mContext, Constants.IS_FIRST_RUN, true)) {
                    Intent intent = new Intent(mContext, WelcomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
                finishDelay();
            }
        }, 1200);
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
    protected void initView() {
        setStatusBarEnable(Color.TRANSPARENT);
    }


    @Override
    protected void initWidgetActions() {
        SplashActivityPermissionsDispatcher.getPermissionWithPermissionCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void getPermission() {
        if (isPermissionDenied(this, AppOpsManager.OPSTR_READ_EXTERNAL_STORAGE)) {
            showPermissionStorageDialog(this);
            return;
        }
        SplashActivityPermissionsDispatcher.getPermission2WithPermissionCheck(this);
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationale(final PermissionRequest request) {
        showPermissionStorageDialog(this, request);
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDenied() {
        showPermissionStorageDialog(this);
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAsk() {
        showPermissionStorageDialog(this);
    }


    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void getPermission2() {
        goNext();
    }


    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void showRationale2(final PermissionRequest request) {
        goNext();
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void showDenied2() {
        goNext();
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void showNeverAsk2() {
        goNext();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JPushInterface.onPause(this);
    }

}
