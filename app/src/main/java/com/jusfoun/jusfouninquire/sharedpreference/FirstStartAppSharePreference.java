package com.jusfoun.jusfouninquire.sharedpreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author zhaoyapeng
 * @version create time:15/11/27下午2:29
 * @Email zyp@jusfoun.com
 * @Description ${第一次启动 应用 sharepreference}
 */
public class FirstStartAppSharePreference {

    public static final String FIRST_START = "first_login";

    public static final String ISFIRST = "is_first";

    public static final String HOME_FIRST_START="home_first_login";

    public static final String HOME_IS_FIRST="home_is_first";


    public static final String QUESTION_NAIRE ="questionnaire";

    public static final String QUESTION_IS_FIRST = "question_is_first";

    public static boolean isFirstStart(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FIRST_START, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(ISFIRST, true);
    }

    public static void saveFirstStart(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FIRST_START, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ISFIRST, false);
        editor.commit();
    }

    public static boolean isHomeFirstStart(Context mContext){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(HOME_FIRST_START,Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(HOME_IS_FIRST,true);
    }

    public static void saveHomeFirstStart(Context mContext){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(HOME_FIRST_START,Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(HOME_IS_FIRST,false);
        editor.commit();
    }


    public static boolean isFirstQuestionnaire(Context mContext){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(QUESTION_NAIRE,Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(QUESTION_IS_FIRST,true);
    }

    public static void saveFirstQuestionnaire(Context mContext){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(QUESTION_NAIRE,Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(QUESTION_IS_FIRST,false);
        editor.commit();
    }
}
