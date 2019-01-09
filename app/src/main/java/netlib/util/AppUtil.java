package netlib.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jusfoun.bigdata.DataColumnEvent;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.ui.permission.SPUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import de.greenrobot.event.EventBus;

public class AppUtil {

    // 获取当前应用应用名
    public static String getAppName(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    // 获取当前应用版本名
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    // 获取当前应用版本号
    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 获取当前应用包名
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    // 获取当前应用图标
    public static Drawable getAppIcon(Context context) {
        return context.getApplicationInfo().loadIcon(context.getPackageManager());
    }

    // 通过进程名获取进程的进程id
    public static int getPid(Context context, String processName) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcessList) {
            if (processName.equals(appProcess.processName)) {
                return appProcess.pid;
            }
        }
        return 0;
    }

    // 安装apk
    public static void installApk(Context context, String apkPath) {
        File file = new File(apkPath);
        Log.e("", "apk size=" + LibIOUtil.getFileSize(file));
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    // 启动apk
    public static void launchApk(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    // 通过文件名获取包名
    public static String getPackageName(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            return appInfo.packageName;
        } else {
            return null;
        }
    }

    // 判断本程序 最顶部activity 是否是 此activity
    public static boolean isActivityTopStartThisProgram(Context context, String activityName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = activityManager.getRunningTasks(100);
        if (list != null && list.size() > 0) {
            for (RunningTaskInfo runningTaskInfo : list) {
                Log.e("tag",
                        "runningTaskInfo.topActivity.getClassName() = " + runningTaskInfo.topActivity.getClassName());
                Log.e("tag", "activityName = " + activityName);
                if (context.getPackageName().equals(runningTaskInfo.baseActivity.getPackageName())) {
                    if (runningTaskInfo.topActivity.getClassName().equals(activityName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断是否已经启动这个程序 且在活动在第一个界面
     *
     * @param context
     * @return
     */
    public static boolean isTopStartProgram(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = activityManager.getRunningTasks(100);
        if (list != null && list.size() > 0) {
            RunningTaskInfo runningTaskInfo = list.get(0);
            if (context.getPackageName().equals(runningTaskInfo.baseActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前应用程序处于前台还是后台
     *
     * @param context
     * @return
     */

    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean appIsRun(final Context mContext) {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = activityManager.getRunningTasks(100);
        for (RunningTaskInfo info : list) {
            if (mContext.getPackageName().equals(info.topActivity.getPackageName()) || mContext.getPackageName().equals(info.baseActivity.getPackageName()))
                return true;
        }

        return false;
    }


    public static boolean isAnimatorVision() {
        return android.os.Build.VERSION.SDK_INT >= 11;
    }

    /**
     * 将参数直接追加到链接上
     *
     * @param url
     * @param map
     * @return
     */
    public static String appendMapParam(String url, Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            StringBuffer sb = new StringBuffer(url);
            if (!sb.toString().contains("?"))
                sb.append("?");
            for (String key : map.keySet()) {
                sb.append(key + "=" + map.get(key) + "&");
            }
            if (sb.toString().endsWith("&"))
                sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
        return url;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * dp转px
     *
     * @param context
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    // 1.法人 2.股东 3.资本变更 4.公司名称 5.经营范围
    // 名称变更；法人变更；股东变更；资本变更；经营变更；
    public static String getChangeType(int type) {
        switch (type){
            case 1:
                return "法人变更";
//                return "法人";
            case 2:
                return "股东变更";
//                return "股东";
            case 3:
                return "资本变更";
//                return "注册资本";
            case 4:
                return "名称变更";
//                return "公司名称";
            case 5:
                return "经营变更";
//                return "经营范围";
        }
        return "";
    }

    public static String getTime(long time){
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd");
        String di =  myFmt.format(new Date(time));
        return di;

    }


    /**
     * 统一设置
     * @param view
     */
    public static void setDataColumn(TextView view) {
        view.setText(Html.fromHtml("数据<br>栏目"));
        view.setTextColor(Color.parseColor("#DF0202"));
        view.setBackgroundColor(Color.TRANSPARENT);
        view.setVisibility(View.VISIBLE);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DataColumnEvent());
            }
        });
    }



    // 检测MIUI
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    public final static String IS_MIUI = "is_miui";

    public static boolean isMIUI() {
        //获取缓存状态
        String miui = SPUtil.getStringForDefault(IS_MIUI);
        if (miui != null) {
            if ("1".equals(miui))
                return true;
            else if ("2".equals(miui))
                return false;
        }
        Properties prop = new Properties();
        boolean isMIUI;
        try {
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        isMIUI = prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        SPUtil.putStringForDefault(IS_MIUI, isMIUI ? "1" : "2");
        return isMIUI;
    }
    /**
     * 查看原生态的权限是否有授权
     *
     * @param context
     * @param op      如定位权限AppOpsManager.OPSTR_FINE_LOCATION
     * @return
     */
    public static boolean checkAppops(Context context, String op) {
        if (isMIUI()) { // 只有小米手机才检测
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                int checkOp = appOpsManager.checkOp(op, Binder.getCallingUid(), context.getPackageName());
                if (checkOp == AppOpsManager.MODE_IGNORED) {
                    return false;
                }
            }
        }
        return true;
    }

}
