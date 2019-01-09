package com.jusfoun.jusfouninquire.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Albert on 2016-3-31.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description: 存储最后一次展示过的通知id
 */
public class ShowedNoticePreference {
    private final static String SHOWED_NOTICE_ID ="showed_notice_id";
    private final static String NOTICE_PREFERENCE_NAME = "notice_preference_name";
    private final static String NOTICE_PREFERENCE_VALUE = "notice_preference_value";

    /**
     * 存储已经展示过的通知
     * @param noticeid  通知id
     * @param context
     */
    public static void saveNotice(String noticeid,Context context){
        SharedPreferences preferences = context.getSharedPreferences(NOTICE_PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NOTICE_PREFERENCE_VALUE, noticeid);
        editor.commit();
    }

    /**
     *
     * @param context
     * @return 本地存储的已经展示过的公告的ID
     */
    public static String  getNoticeId(Context context){
        SharedPreferences preferences = context.getSharedPreferences(NOTICE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String value = preferences.getString(NOTICE_PREFERENCE_VALUE, "");
        return value;
    }
}
