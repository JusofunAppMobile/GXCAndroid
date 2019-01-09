package com.jusfoun.jusfouninquire.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Albert on 2016-3-31.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:保存通知图片
 */
public class NoticeImagePreference {

    private final static String NOTICE_IMAGE_PREFERENCE_NAME = "notice_image_preference_name";
    private final static String NOTICE_IMAGE_URL = "notice_image_url";
    private final static String NOTICE_BTN_URL = "notice_btn_url";



    public static String getNoticeImageUrl(Context context){
        SharedPreferences noticePreference = context.getSharedPreferences(NOTICE_IMAGE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String imageUrl = noticePreference.getString(NOTICE_IMAGE_URL, "");
        return imageUrl;
    }

    public static String getNoticeBtnUrl(Context context){
        SharedPreferences noticePreference = context.getSharedPreferences(NOTICE_IMAGE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String btnUrl = noticePreference.getString(NOTICE_BTN_URL, "");
        return btnUrl;
    }

    public static void setNoticeImageUrl(Context context,String path){
        SharedPreferences noticePreference = context.getSharedPreferences(NOTICE_IMAGE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = noticePreference.edit();
        editor.putString(NOTICE_IMAGE_URL, path);
        editor.commit();
    }

    public static void setNoticeBtnUrl(Context context,String path){
        SharedPreferences noticePreference = context.getSharedPreferences(NOTICE_IMAGE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = noticePreference.edit();
        editor.putString(NOTICE_BTN_URL, path);
        editor.commit();
    }

}
