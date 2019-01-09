package com.jusfoun.jusfouninquire.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author lee
 * @version create time:2015/11/1916:01
 * @Email email
 * @Description $广告图片和地址
 */

public class AdvertiseSharePreference {
    private static final String SAVE_ADVERTISE = "save_advertise";
    private static final String IMAGE_URL = "imageUrl";
    private static final String ADVERTISE_URL = "advertiseUrl";
    private static final String DOWN_STATE = "down_state";


    private static final String NEW_IMAGEURL = "new_imageUrl";
    private static final String NEW_ADBERTISE_URL = "new_advertiseUrl";
    private static final String NEW_DOWN_STATE = "new_down_state";

    // 存储 即将展示的广告
    public static void saveAdvertise(Context context, String imageUrl, String advertiseUrl, boolean downState) {
        SharedPreferences adverSharePre = context.getSharedPreferences(SAVE_ADVERTISE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = adverSharePre.edit();
        editor.putString(IMAGE_URL, imageUrl);
        editor.putString(ADVERTISE_URL, advertiseUrl);
        editor.putBoolean(DOWN_STATE, downState);
        editor.commit();
    }

    public static String getImageUrl(Context context) {
        SharedPreferences adverSharePre = context.getSharedPreferences(SAVE_ADVERTISE, Context.MODE_PRIVATE);
        String imageUrl = adverSharePre.getString(IMAGE_URL, "");
        return imageUrl;
    }

    public static String getAdvertiseUrl(Context context) {
        SharedPreferences adverSharePre = context.getSharedPreferences(SAVE_ADVERTISE, Context.MODE_PRIVATE);
        String advertiseUrl = adverSharePre.getString(ADVERTISE_URL, "");
        return advertiseUrl;
    }

    public static boolean getAdvertiseDownState(Context context){
        SharedPreferences adverSharePre = context.getSharedPreferences(SAVE_ADVERTISE, Context.MODE_PRIVATE);
        return adverSharePre.getBoolean(DOWN_STATE, false);
    }


    // 存储 新获取到的广告
    public static void saveNewAdavertise(Context context, String imageUrl, String advertiseUrl, boolean downState) {
        SharedPreferences adverSharePre = context.getSharedPreferences(SAVE_ADVERTISE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = adverSharePre.edit();
        editor.putString(NEW_IMAGEURL, imageUrl);
        editor.putString(NEW_ADBERTISE_URL, advertiseUrl);
        editor.putBoolean(NEW_DOWN_STATE, downState);
        editor.commit();

    }




    public static String getNewImageUrl(Context context) {
        SharedPreferences adverSharePre = context.getSharedPreferences(SAVE_ADVERTISE, Context.MODE_PRIVATE);
        String imageUrl = adverSharePre.getString(NEW_IMAGEURL, "");
        return imageUrl;
    }

    public static String getNewAdvertiseUrl(Context context) {
        SharedPreferences adverSharePre = context.getSharedPreferences(SAVE_ADVERTISE, Context.MODE_PRIVATE);
        String advertiseUrl = adverSharePre.getString(NEW_ADBERTISE_URL, "");
        return advertiseUrl;
    }


    public static void saveNewToOld(Context context) {
        SharedPreferences adverSharePre = context.getSharedPreferences(SAVE_ADVERTISE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = adverSharePre.edit();
        editor.putString(IMAGE_URL, adverSharePre.getString(NEW_IMAGEURL, ""));
        editor.putString(ADVERTISE_URL, adverSharePre.getString(NEW_ADBERTISE_URL, ""));
        editor.putBoolean(DOWN_STATE, adverSharePre.getBoolean(NEW_DOWN_STATE, false));


//        editor.putString(NEW_IMAGEURL, "");
//        editor.putString(NEW_ADBERTISE_URL, "");
        editor.commit();
    }

    // 存储新广告 图片下载完成状态
    public static void setNewDownState(Context context,String  localImgUrl){
        SharedPreferences adverSharePre = context.getSharedPreferences(SAVE_ADVERTISE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = adverSharePre.edit();
        editor.putBoolean(NEW_DOWN_STATE, true);
        editor.putString(NEW_IMAGEURL, localImgUrl);
        editor.commit();
    }

}
