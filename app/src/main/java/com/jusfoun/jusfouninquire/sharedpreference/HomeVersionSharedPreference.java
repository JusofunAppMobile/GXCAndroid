package com.jusfoun.jusfouninquire.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Albert on 2016/1/14.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:记录首页接口返回的版本号，若本地存储和接口返回不同，触发调用接口更新设置、首页、个人中心显示
 */
public class HomeVersionSharedPreference {
    private static String HOME_VERSION_NAME = "home_version_name";
    private static SharedPreferences mSharedPreferencef;
    private static String HOME_VERSION_VALUE = "home_version_value";

    public static void saveHomeVersion(Context context,String version){
        mSharedPreferencef = context.getSharedPreferences(HOME_VERSION_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferencef.edit();
        editor.putString(HOME_VERSION_VALUE,version);
        editor.commit();
    }

    public static String getHomeVersion(Context context){
        mSharedPreferencef = context.getSharedPreferences(HOME_VERSION_NAME,Context.MODE_PRIVATE);
        String version = mSharedPreferencef.getString(HOME_VERSION_VALUE,"");
        return version;
    }
}
