package com.jusfoun.jusfouninquire.ui.util;

import android.util.Log;

import com.jusfoun.jusfouninquire.BuildConfig;

/**
 * Author  wangchenchen
 * CreateDate 2016/8/17.
 * Email wcc@jusfoun.com
 * Description Log 工具类,debug可打印日志
 */
public class LogUtil {

    public static void e(String tag,String msg){
        if (BuildConfig.DEBUG)
            Log.e(tag,msg);
//        Log.e(tag,msg);
    }

    public static void d(String tag,String msg){
        if (BuildConfig.DEBUG)
            Log.d(tag, msg);
    }

    public static void i(String tag,String msg){
        if (BuildConfig.DEBUG)
            Log.i(tag, msg);
    }

    public static void w(String tag,String msg){
        if (BuildConfig.DEBUG)
            Log.w(tag, msg);
    }

    public static void v(String tag,String msg){
        if (BuildConfig.DEBUG)
            Log.v(tag, msg);

    }

}
