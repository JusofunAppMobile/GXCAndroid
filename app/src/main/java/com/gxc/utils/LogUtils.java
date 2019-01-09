package com.gxc.utils;

import android.util.Log;

public class LogUtils {

	private final static String TAG = "Log";
	
	private static boolean debug = true;
	
	public static void debug(boolean isDebug){
		debug = isDebug;
	}

	public static void e(String msg) {
		if(debug){
			if (msg == null)
				msg = "null";
			Log.e(TAG, msg);
		}
	}
	
	public static void e(String tag, String msg) {
		if(debug){
			if (msg == null)
				msg = "null";
			Log.e(tag, msg);
		}
	}
}
