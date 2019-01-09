package com.jusfoun.jusfouninquire.ui.util;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * @author lee
 * @version create time:2015/10/2010:08
 * @Email email
 * @Description ${TODO}
 */

public class AppInfo implements Serializable{

    private String appName = "";
    private String packageName = "";
    private String versionName = "";
    private int versionCode = 0;
    private Drawable appIcon = null;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                ", appIcon=" + appIcon +
                '}';
    }
}
