package com.jusfoun.jusfouninquire.net.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.Date;

/**
 * @author henzil
 * @version create time:2015-4-30_上午11:08:29
 * @Description 用于存储是否在下载 状态
 */
public class UpdateServiceHelper {

	private final static String UPDATE_SHAREDPREFERENCES = "update";

	private final static String STATE_KEY = "state";

	private final static String TIME_KEY = "time_key";

	public static final String NOTIFICATION_ID = "nofiId";

	private static final int NOFI_ID = -100000000;

	public static void saveState(boolean state, Context context) {
		SharedPreferences lastTimePreferences = context.getSharedPreferences(context.getPackageName()
				+ UPDATE_SHAREDPREFERENCES, Activity.MODE_PRIVATE);
		Editor editor = lastTimePreferences.edit();
		editor.putBoolean(STATE_KEY, state);
		if (state) {
			// 做处理 记录当前设置了下载开始时间
			Date date = new Date();
			long time = date.getTime();
			editor.putLong(TIME_KEY, time);
		}
		editor.commit();
	}

	public static boolean getState(Context context) {
		SharedPreferences lastTimePreferences = context.getSharedPreferences(context.getPackageName()
				+ UPDATE_SHAREDPREFERENCES, Activity.MODE_PRIVATE);
		boolean state = lastTimePreferences.getBoolean(STATE_KEY, false);
		if (state) {
			long time = lastTimePreferences.getLong(TIME_KEY, -1);
			if (time > 0) {
				Date date = new Date();
				long nowTime = date.getTime();
				if ((nowTime - time) > (10 * 60 * 1000)) {
					// 如果相隔 30分钟 则认为是下载失败或者是持久化数据出问题了 则直接返回false。
					return false;
				}
			} else {
				// 如果无time 则是第一次 则直接返回false。
				return false;
			}
		}
		return state;
	}
	
	// 清除掉下载文件状态保存 持久化内容。
	public static void delete(Context context) {
		SharedPreferences userInfoPreferences = context.getSharedPreferences(context.getPackageName()
				+ UPDATE_SHAREDPREFERENCES, Activity.MODE_PRIVATE);
		if(!userInfoPreferences.getAll().isEmpty()){
			Editor editor = userInfoPreferences.edit();
			editor.clear();
			editor.commit();
		}
	}

	public static void startOSService(Context context, int nofiId, String url) {
		Log.e("tag","update-startOSService1");
		Intent intent = new Intent(context, UpdateOSService.class);
		try {
			context.stopService(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		intent.putExtra(UpdateOSService.NOTIFICATION_ID, nofiId == -1 ? NOFI_ID : nofiId);
		intent.putExtra(UpdateOSService.URL, url);
		context.startService(intent);
		Log.e("tag", "update-startOSService2");
	}

}
