package com.jusfoun.jusfouninquire.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by Albert on 2015/11/26.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:存储前一次的获取的企业数量的最大值
 */
public class CompanyCountSharedPreference {
    private final static String PREFERENCE_NAME = "preference_name";
    private final static String COMPANY_COUNT = "company_count";

    public static void saveCompanyCount(Context context,String count){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!TextUtils.isEmpty(count)){
            editor.putLong(COMPANY_COUNT,Long.parseLong(count));
            editor.commit();
        }

    }

    public static long getCompanyCount(Context context){
        long count;
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        count = preferences.getLong(COMPANY_COUNT, -1);
        return count;
    }
}
