package com.jusfoun.jusfouninquire.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;

/**
 * @author lee
 * @version create time:2015/11/2618:53
 * @Email email
 * @Description ${TODO}
 */

public class LastLoginSharePreference {
    private final static String USER_ACCOUNT ="account";
    private final static String PRODUCT_SHAREDPREFERENCES = "sp_account";


    public static void saveUserAccount(String account, Context context) {
        SharedPreferences userInfoPreferences = context.getSharedPreferences(PRODUCT_SHAREDPREFERENCES,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfoPreferences.edit();
        editor.putString(USER_ACCOUNT, account);
        editor.commit();
    }

    public static String getUserAccount(Context context){
        SharedPreferences userInfoPreferences = context.getSharedPreferences(PRODUCT_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        String value = userInfoPreferences.getString(USER_ACCOUNT, "");
        try{
            return value;
        } catch (Exception exception) {
            Toast.makeText(context, "服务器维护中", Toast.LENGTH_SHORT).show();
        } finally {

        }
        return "";
    }
}
