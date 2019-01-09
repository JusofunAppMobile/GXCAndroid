package com.jusfoun.jusfouninquire.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.net.model.VersionModel;

/**
 * Created by Albert on 2015/12/7.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:检查版本更新存储
 */
public class CheckVersionSharedPreference {
    private static String PREFERENCE_NAME = "version_sharedpreference";
    private static String VERSION_MODEL = "version_model";

    public static void saveVersionInfo(Context context,VersionModel model){
        String value = new Gson().toJson(model);
        SharedPreferences versionInfoPreferences = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = versionInfoPreferences.edit();
        editor.putString(VERSION_MODEL, value);
        editor.commit();
    }

    public static VersionModel getVersionInfo(Context context){
        SharedPreferences versionInfoPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String value = versionInfoPreferences.getString(VERSION_MODEL, "");
        try{
            VersionModel versionModel = new Gson().fromJson(value, VersionModel.class);
            return versionModel;
        } catch (Exception exception) {
            Toast.makeText(context, "服务器维护中", Toast.LENGTH_SHORT).show();
        } finally {

        }
        return null;
    }

    public static void deleteVersionInfo(Context context){
        SharedPreferences versionInfoPreferences = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = versionInfoPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
