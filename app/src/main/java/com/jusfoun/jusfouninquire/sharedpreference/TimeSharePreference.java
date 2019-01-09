package com.jusfoun.jusfouninquire.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;

/**
 * @author lee
 * @version create time:2016/1/1515:09
 * @Email email
 * @Description 记录时间点
 */

public class TimeSharePreference {
    private final static String TIME_NODE ="sp_time";
    private final static String TIME_INTERVAL = "sp_time_interval";
    private final static String PRODUCT_SHAREDPREFERENCES = "sp_time_sharepreference";


    /**
     *
     * @param timeNode 离开页面的时间点
     * @param context
     */
    public static void saveTimeNode(String timeNode, Context context) {
        Log.d("TAG", "onStop saveTimeNode   timeNode:" + timeNode);
        SharedPreferences timeInfoPreferences = context.getSharedPreferences(PRODUCT_SHAREDPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = timeInfoPreferences.edit();
        editor.putString(TIME_NODE, timeNode);
        editor.commit();
    }

    public static String  getTimeNode(Context context){
        SharedPreferences timeInfoPreferences = context.getSharedPreferences(PRODUCT_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        String value = timeInfoPreferences.getString(TIME_NODE, "");
        return value;
    }


    /**
     * @param timeNode  计时器的剩余时间
     * @param context
     */
    public static void saveIntervalTime(int timeNode, Context context) {
        SharedPreferences timeInfoPreferences = context.getSharedPreferences(PRODUCT_SHAREDPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = timeInfoPreferences.edit();
        editor.putInt(TIME_INTERVAL, timeNode);
        editor.commit();
    }

    public static int  getIntervalTime(Context context){
        SharedPreferences timeInfoPreferences = context.getSharedPreferences(PRODUCT_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        int value = timeInfoPreferences.getInt(TIME_INTERVAL,0);
        return value;
    }

    public static void deleteTimeNode(Context context){
        SharedPreferences timeInfoPreferences = context.getSharedPreferences(PRODUCT_SHAREDPREFERENCES,
                Context.MODE_PRIVATE);
        if(!timeInfoPreferences.getAll().isEmpty()){
            SharedPreferences.Editor editor = timeInfoPreferences.edit();
            editor.clear();
            editor.commit();
        }
    }

}
