package com.jusfoun.jusfouninquire.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author zhaoyapeng
 * @version create time:2015-7-21下午7:29:05
 * @Email zyp@jusfoun.com
 * @Description 搜索记录 数据库 单例
 */
public class DBSqlDataUtil {
	private static DBSqlDataUtil dbSqlDataUtil = null;

	private SearchHistoryHelper dbHelper;
	private SQLiteDatabase db;

	private DBSqlDataUtil(Context mContext) {
		dbHelper = new SearchHistoryHelper(mContext, DBCanstant.DB_NAME, null, DBCanstant.VERSION);
		db = dbHelper.getReadableDatabase();
		
	}

	public static DBSqlDataUtil getInstance(Context mContext) {
		if (dbSqlDataUtil == null) {
			dbSqlDataUtil = new DBSqlDataUtil(mContext);
		}
		return dbSqlDataUtil;
	}

	public SQLiteDatabase getDB() {
		return db;
	}
}
