
package com.jusfoun.jusfouninquire.ui.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;
import java.util.List;

import permissions.dispatcher.PermissionRequest;

import static com.jusfoun.bigdata.NearMapActivity.ACTION_APPLICATION_DETAILS_SETTINGS_CODE;
import static com.jusfoun.jusfouninquire.ui.util.PictureUtil.REQUEST_CODE_TAKE_PICTURE;

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
        intent.setAction(Intent.ACTION_VIEW);
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

    public static boolean isAnimatorVision() {
        return android.os.Build.VERSION.SDK_INT >= 11;
    }

    public static String getAppMetaData(Context mContext, String key) {
        String resultData = null;
        try {
            PackageManager packageManager = mContext.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * 有些小米手机检测都授权后，还是未授权，需要额外判断
     * AppOpsManager.OPSTR_READ_EXTERNAL_STORAGE
     */
    public static boolean isPermissionDenied(Context context, String op) {
//        if ("xiaomi".equalsIgnoreCase(Build.MANUFACTURER) && Build.VERSION.SDK_INT >= 23) {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                if (appOpsManager != null) {
                    int checkOp = appOpsManager.checkOp(op, Process.myUid(), getPackageName(context));
                    if (checkOp == AppOpsManager.MODE_IGNORED) {
                        // 权限被拒绝了
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 显示 存储空间 权限对话框
     *
     * @param activity
     * @return
     */
    public static AlertDialog showPermissionStorageDialog(final Activity activity, final PermissionRequest request) {
        return showPermissionDialog(activity, request, "存储空间", null);
    }

    /**
     * 显示 存储空间 权限对话框
     *
     * @param activity
     * @return
     */
    public static AlertDialog showPermissionStorageDialog(final Activity activity) {
        return showPermissionStorageDialog(activity, null);
    }

    /**
     * 显示 相机 权限对话框
     *
     * @param activity
     * @return
     */
    public static AlertDialog showPermissionCameraDialog(final Activity activity, final PermissionRequest request) {
        return showPermissionDialog(activity, request, "相机", new OnCallListener() {
            @Override
            public void call(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 显示 相机 权限对话框
     *
     * @param activity
     * @return
     */
    public static AlertDialog showPermissionCameraDialog(final Activity activity) {
        return showPermissionCameraDialog(activity, null);
    }

    private static AlertDialog showPermissionDialog(final Activity activity, final PermissionRequest request, String permissionTip, final OnCallListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle("权限申请")
                .setCancelable(false)
                .setMessage("在设置-应用-" + AppUtil.getAppName(activity) + "-权限中开启" + permissionTip + "权限，以正常使用" + AppUtil.getAppName(activity) + "功能");
        if (request != null) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (request != null)
                        request.proceed();
                    else if (listener != null)
                        listener.call(dialog);
                    else
                        activity.finish();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    request.cancel();
                }
            });
        } else {
            builder.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    goPermissionSetting(activity);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (listener != null)
                        listener.call(dialog);
                    else
                        activity.finish();
                }
            });
        }
        return builder.show();
    }

    private static void goPermissionSetting(Activity activity){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", AppUtil.getPackageName(activity), null);
        intent.setData(uri);
        activity.startActivityForResult(intent,ACTION_APPLICATION_DETAILS_SETTINGS_CODE);
    }

    public interface OnCallListener {
        void call(DialogInterface dialog);
    }


    public static void takePhoto(Context context, String path) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            Uri mImageCaptureUri = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mImageCaptureUri = Uri.fromFile(new File(path));
            } else {
                /*
                 * The solution is taken from here:
				 * http://stackoverflow.com/questions
				 * /10042695/how-to-get-camera-result-as-a-uri-in-data-folder
				 */
                // InternalStorageContentProvider.CONTENT_URI;
                mImageCaptureUri = Uri.parse("content://eu.janmuller.android.simplecropimage.example/");
            }
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {
            Log.d("tag", "cannot take picture", e);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showSoftInput(Activity activity, EditText editText) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            manager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

}
