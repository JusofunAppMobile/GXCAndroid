package com.jusfoun.jusfouninquire.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Albert on 2015/12/8.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:搜索记录数据库helper
 */
public class SearchHistoryHelper extends SQLiteOpenHelper {
    public SearchHistoryHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SearchHistoryHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlStr = "CREATE TABLE IF NOT EXISTS "
                + DBCanstant.TABLE_SEARCH_HISTORY_NAME + "("
                + DBCanstant.SEARCH_KEY + " TEXT,"
                + DBCanstant.SEARCH_SCOPEID + " TEXT,"
                + DBCanstant.SEARCH_SCOPENAME + " TEXT,"
                + DBCanstant.SEARCH_TIMESTAMAP + " TEXT KEY,"

                + DBCanstant.SEARCH_TYPE + " TEXT);";

        String homeSql = "CREATE TABLE IF NOT EXISTS " + DBCanstant.TABLE_NAME_HOME + "("+DBCanstant.JSON_HOME_ID+" TEXT PRIMARY KEY," + DBCanstant.JSON_HOME + " TEXT);";
        Log.e("tag","homeSql="+homeSql);
        db.execSQL(homeSql);
        db.execSQL(sqlStr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            db.execSQL("drop table if exists " + DBCanstant.TABLE_SEARCH_HISTORY_NAME);
            db.execSQL("drop table if exists "+DBCanstant.TABLE_NAME_HOME);
        } catch (Exception e) {

        }
        onCreate(db);


    }
}
