package com.jusfoun.jusfouninquire.ui.permission;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jusfoun.jusfouninquire.InquireApplication;

/**
 * SharedPreferences 本地文件存储（形式：键值对）工具类
 * Create by lzx on 2016-7-5 21:51:16
 */
public class SPUtil {

    private static final String SP_DEFAULT = "inquire_default";


    /**
     * 保存字段到本地默认文件 默认保留到XXX文件下
     *
     * @param key   保存的key值
     * @param value 保存的value值
     */
    public static void putStringForDefault(String key, String value) {
        putString(key, value, SP_DEFAULT);
    }

    /**
     * 获取保存的字段值
     *
     * @param key 保存的key值
     * @return String 保存的value值
     */
    public static String getStringForDefault(String key) {
        return getString(key, SP_DEFAULT);
    }


    /**
     * 保存字段到指定文件夹
     *
     * @param key      保存的key值
     * @param value    保存的value值
     * @param fileName 保存的文件名称
     */
    public static void putString(String key, String value, String fileName) {
        // 获取SharedPreferences对象
        SharedPreferences sp = InquireApplication.application.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        // 存入数据
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();

    }

    /**
     * 获取指定文件下的保存的字段值
     *
     * @param key      保存的key值
     * @param fileName 保存的文件名称
     * @return String 保存的value值
     */
    public static String getString(String key, String fileName) {
        // 获取SharedPreferences对象
        SharedPreferences sp = InquireApplication.application.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getString(key, null);
    }
}
