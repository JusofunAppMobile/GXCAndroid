package com.jusfoun.jusfouninquire.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.mobstat.StatService;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.AdvertiseModel;
import com.jusfoun.jusfouninquire.net.model.CheckVersionModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.model.VersionModel;
import com.jusfoun.jusfouninquire.net.route.GetHomeInfo;
import com.jusfoun.jusfouninquire.net.route.PersonCenterHelper;
import com.jusfoun.jusfouninquire.net.route.PostPushId;
import com.jusfoun.jusfouninquire.net.update.UpdateServiceHelper;
import com.jusfoun.jusfouninquire.service.LoadImageService;
import com.jusfoun.jusfouninquire.sharedpreference.AdvertiseSharePreference;
import com.jusfoun.jusfouninquire.sharedpreference.CheckVersionSharedPreference;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.jusfoun.bigdata.NearMapActivity.ACTION_APPLICATION_DETAILS_SETTINGS_CODE;
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
    /**
     * 常量
     */
    public static final int DELAYMILLIS = 2000;

    /**组件*/


    /**
     * 变量
     */
    private int DELAY = 1;
    private int TONET = 2;
    private int ADVERTISE = 3;
    private int PUSH = 4;
    private boolean netSuccese = false, handerSuc = false;
    private boolean advertiseState = false, pushState = false;


    private static final int CHANGE_UPDATE_TYPE = 1;
    private static final int MUST_UPDATE_TYPE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        clearUserInfo();


        String channel = AppUtil.getAppMetaData(this, "UMENG_CHANNEL");
        // 放弃旧的统计逻辑，统计渠道都进行统计，升级统计sdk
//        if (channel.equals("baidu") || channel.equals("91zhushou") || channel.equals("anzhuoshichang")) {
//            StatService.setAppKey(getString(R.string.baidu_statistics_key));
//            StatService.setSessionTimeOut(30);
//            // setOn也可以在AndroidManifest.xml文件中填写，BaiduMobAd_EXCEPTION_LOG，打开崩溃错误收集，默认是关闭的
//            StatService.setOn(this, StatService.EXCEPTION_LOG);
//        /*
//         * 设置启动时日志发送延时的秒数<br/> 单位为秒，大小为0s到30s之间<br/> 注：请在StatService.setSendLogStrategy之前调用，否则设置不起作用
//         *
//         * 如果设置的是发送策略是启动时发送，那么这个参数就会在发送前检查您设置的这个参数，表示延迟多少S发送。<br/> 这个参数的设置暂时只支持代码加入，
//         * 在您的首个启动的Activity中的onCreate函数中使用就可以。<br/>
//         */
//            StatService.setLogSenderDelayed(0);
//        /*
//         * 用于设置日志发送策略<br /> 嵌入位置：Activity的onCreate()函数中 <br />
//         *
//         * 调用方式：StatService.setSendLogStrategy(this,SendStrategyEnum. SET_TIME_INTERVAL, 1, false); 第二个参数可选：
//         * SendStrategyEnum.APP_START SendStrategyEnum.ONCE_A_DAY SendStrategyEnum.SET_TIME_INTERVAL 第三个参数：
//         * 这个参数在第二个参数选择SendStrategyEnum.SET_TIME_INTERVAL时生效、 取值。为1-24之间的整数,即1<=rtime_interval<=24，以小时为单位 第四个参数：
//         * 表示是否仅支持wifi下日志发送，若为true，表示仅在wifi环境下发送日志；若为false，表示可以在任何联网环境下发送日志
//         */
//            StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
//            StatService.setDebugOn(true);
//        }

        //暂时屏蔽广告
        advertiseState = true;

        StatService.setDebugOn(false);
        StatService.start(this);

//        Intent intent = new Intent(this, StartService.class);
//        mContext.startService(intent);


    }

    @Override
    public boolean isSetStatusBar() {
        return false;
    }

    @Override
    public boolean isBarDark() {
        return false;
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == DELAY) {
                handerSuc = true;
                if (netSuccese && advertiseState && pushState) {
                    gotoActivity();
                }

            } else if (msg.what == TONET) {
                netSuccese = true;
                if (handerSuc && advertiseState && pushState) {
                    gotoActivity();
                }

            } else if (msg.what == ADVERTISE) {
                advertiseState = true;
                if (handerSuc && netSuccese && pushState) {
                    gotoActivity();
                }
            } else if (msg.what == PUSH) {
                pushState = true;
                if (handerSuc && netSuccese && advertiseState) {
                    gotoActivity();
                }
            }
        }
    };

    @Override
    protected void initView() {
        setStatusBarEnable(Color.TRANSPARENT);
    }


    @Override
    protected void initWidgetActions() {
//        MobclickAgent.updateOnlineConfig(this);
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

    private void goNext() {
        setMessage(DELAY);
        postPushId();
        CheckVersion();
//        getAdvertiseUrl();
        try {
            MobclickAgent.setDebugMode(false);
            if (AppUtil.isApkInDebug(mContext)) {
                MobclickAgent.setCatchUncaughtExceptions(false);
            } else {
                MobclickAgent.setCatchUncaughtExceptions(true);
            }
        } catch (Exception e) {

        }
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

    /**
     * 检查更新
     */
    private void CheckVersion() {
        ApplicationInfo appInfo = null;
        String ChannelValue = null;
        try {
            appInfo = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            ChannelValue = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = AppUtil.getVersionName(mContext);
        int versionCode = AppUtil.getVersionCode(mContext);
        HashMap<String, String> map = new HashMap<>();
        map.put("versionname", versionName);
        map.put("versioncode", versionCode + "");
        map.put("from", "Android");
        map.put("channel", ChannelValue);

        PersonCenterHelper.doNetGetCheckVersion(mContext, map, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                CheckVersionModel model = (CheckVersionModel) data;
                if (model.getResult() == 0) {
                    if (model.getVersionnumber() != null) {
                        if (model.getVersionnumber().getUpdate() != 0) {
                            CheckVersionSharedPreference.saveVersionInfo(mContext, model.getVersionnumber());
                        } else {
                            CheckVersionSharedPreference.deleteVersionInfo(mContext);
                        }

                    }
                    detailUpdate(model);

                } else {
                    netSuccese = true;
                    setMessage(TONET);
                }


            }

            @Override
            public void onFail(String error) {
                netSuccese = true;
                setMessage(TONET);
            }
        });

//        Log.e("tag", "getDeviceInfo=" + getDeviceInfo(this));
    }

    private void setMessage(int what) {
        Message msg = new Message();
        if (what == DELAY) {
            Log.e("tag", "setMessage1");
            msg.what = DELAY;
            handler.sendMessageDelayed(msg, DELAYMILLIS);
        } else if (what == TONET) {
            Log.e("tag", "setMessage2");
            msg.what = TONET;
            handler.sendMessage(msg);
        } else if (what == PUSH) {
            Log.e("tag", "setMessage3");
            msg.what = PUSH;
            handler.sendMessage(msg);
        } else if (what == ADVERTISE) {
            Log.e("tag", "setMessage4");
            msg.what = ADVERTISE;
            handler.sendMessage(msg);
        }

    }

    /**
     * 发送唯一标示
     */
    private void postPushId() {
        HashMap<String, String> params = new HashMap<>();
        params.put("pushid", JPushInterface.getRegistrationID(mContext));
        if (LoginSharePreference.getUserInfo(mContext) != null
                && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            params.put("userid", LoginSharePreference.getUserInfo(mContext).getUserid());
        } else {
            params.put("userid", "");
        }
        params.put("deveiceid", PhoneUtil.getIMEI(mContext));
        Log.e("tag", "deveiceid=" + PhoneUtil.getIMEI(mContext));
        Log.e("tag", "pushid=" + JPushInterface.getRegistrationID(mContext));
        PostPushId.postPushId(mContext, params, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                pushState = true;
                setMessage(PUSH);
            }

            @Override
            public void onFail(String error) {
                pushState = true;
                setMessage(PUSH);
            }
        });
    }

    // 判断跳转至 广告页面 还是 首页
    private void gotoActivity() {
        // 暂时屏蔽广告页面
//        if (FirstStartAppSharePreference.isFirstStart(this)) {
//
//            goActivity(MainActivity.class);
//        } else {
//            if (AdvertiseSharePreference.getAdvertiseUrl(this) != null
//                    && !AdvertiseSharePreference.getAdvertiseUrl(this).equals("")
//                    && AdvertiseSharePreference.getImageUrl(this) != null
//                    && !AdvertiseSharePreference.getImageUrl(this).equals("")
//                    && AdvertiseSharePreference.getAdvertiseDownState(this)) {
//                goActivity(AdvertisementActivity.class);
////                goActivity(SearchResultActivity.class);
//            } else {
//                goActivity(MainActivity.class);
////                goActivity(SearchResultActivity.class);
//            }
//        }

        goActivity(MainActivity.class);
        finish();
    }

    private void detailUpdate(CheckVersionModel model) {
        final VersionModel localVersion = model.getVersionnumber();
        if (localVersion == null) {
            return;
        }
        if (localVersion.getUpdate() == CHANGE_UPDATE_TYPE || localVersion.getUpdate() == MUST_UPDATE_TYPE) {
            final Dialog dialog = new AlertDialog.Builder(mContext)
                    .setTitle(localVersion.getTitletext())
                    .setCancelable(false)
                    .setMessage(localVersion.getDescribe())
                    .setPositiveButton(localVersion.getUpdatetext(),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!UpdateServiceHelper.getState(mContext)) {// 在更新则不更新
                                        UpdateServiceHelper.startOSService(mContext, -1,
                                                localVersion.getUrl());
                                    }
                                    dialog.dismiss();
                                    if (localVersion.getUpdate() == MUST_UPDATE_TYPE) {
                                        ((Activity) mContext).finish();
                                        return;
                                    }
                                    netSuccese = true;
                                    setMessage(TONET);
                                }
                            })
                    .setNeutralButton(localVersion.getCacletext(),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if (localVersion.getUpdate() == MUST_UPDATE_TYPE) {
                                        ((Activity) mContext).finish();
                                        return;
                                    }
                                    netSuccese = true;
                                    setMessage(TONET);
                                }
                            }).create();
            if ((!isFinishing())) {
                dialog.show();
            }

        } else {
            netSuccese = true;
            setMessage(TONET);
        }
    }

    private void getAdvertiseUrl() {
        AdvertiseSharePreference.saveNewToOld(this);
        GetHomeInfo.getAdvertise(mContext, null, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                advertiseState = true;
                AdvertiseModel model = (AdvertiseModel) data;
                if (model.getResult() == 0) {
                    AdvertiseSharePreference.saveNewAdavertise(mContext, model.getImageurl(), model.getAdurl(), false);
                    if (model.getImageurl() != null && !model.getImageurl().equals("")) {
                        Intent intent = new Intent(SplashActivity.this, LoadImageService.class);
                        startService(intent);
                    }
                    setMessage(ADVERTISE);
                }
            }

            @Override
            public void onFail(String error) {
                advertiseState = true;
//                AdvertiseSharePreference.saveNewAdavertise(mContext, "", "", false);
//                AdvertiseSharePreference.saveNewAdavertise(mContext, "http://www.51ps.com/upfile/2007/11/200711234317140356694.jpg", "aa", false);
//                Intent intent = new Intent(SplashActivity.this, LoadImageService.class);
//                startService(intent);
                setMessage(ADVERTISE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }


    public boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
//            if (checkPermission(context, Permission.READ_PHONE_STATE)) {
//
//            }
            device_id = tm.getDeviceId();
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void clearUserInfo() {
        UserInfoModel userInfoModel = LoginSharePreference.getUserInfo(this);
        if (userInfoModel != null) {
            // 未绑定手机号，强制注销
//            if (TextUtils.isEmpty(userInfoModel.getMobile()) && TextUtils.isEmpty(userInfoModel.getNickname())) {
            if (TextUtils.isEmpty(userInfoModel.getMobile())) {
                LoginSharePreference.deleteUserInfo(mContext);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_APPLICATION_DETAILS_SETTINGS_CODE) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SplashActivityPermissionsDispatcher.getPermissionWithPermissionCheck(SplashActivity.this);
                }
            }, 300);
        }
    }
}
