package com.jusfoun.jusfouninquire.net.update;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;

import netlib.util.SetUtil;

/**
 * 
 * @author henzil
 * @E-mail lizhen@dns.com.cn
 * @version create time:2013-7-24_下午9:26:19
 * @Description 请求网络最基础的工具，只管网络请求。
 */

@SuppressLint("DefaultLocale")
public class HttpClientUtil {

	private final static String TAG = "HttpClientUtil";

	private static final int SET_CONNECTION_TIMEOUT = 10000;
	private static final int SET_SOCKET_TIMEOUT = 10000;

	public static HttpURLConnection getNewHttpURLConnection(URL url, Context context) {
		HttpURLConnection connection = null;
		Cursor mCursor = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			Log.e("tag", "wifiManager.isWifiEnabled() = " + wifiManager.isWifiEnabled());
			if (!wifiManager.isWifiEnabled()) {
				// 获取当前正在使用的APN接入点
				Uri uri = Uri.parse("content://telephony/carriers/preferapn");
				mCursor = context.getContentResolver().query(uri, null, null, null, null);
				if (mCursor != null && mCursor.moveToFirst()) {
					// 游标移至第一条记录，当然也只有一条
					String proxyStr = mCursor.getString(mCursor.getColumnIndex("proxy"));
					if (proxyStr != null && proxyStr.trim().length() > 0) {
						Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(proxyStr, 80));
						connection = (HttpURLConnection) url.openConnection(proxy);
					}
				}
			}
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				return (HttpURLConnection) url.openConnection();
			} catch (IOException e1) {
				e1.printStackTrace();
				return null;
			}
		} finally {
			if (mCursor != null) {
				mCursor.close();
			}
		}

	}

	private static HashMap<String, String> urlMap = new HashMap<String, String>();
	// 下载文件
	public static long downloadFile(String url, File f, Context context) {
		if (!urlMap.containsKey(url)) {
			urlMap.put(url, url);
		} else {
			while (urlMap.containsKey(url)) {

			}
			return f.length();
		}

		long fileLength = 0;
		URL myFileUrl = null;
		try {
			Log.i(TAG, "Image url is " + url);
			myFileUrl = new URL(url);
		} catch (Exception e) {
			return fileLength;
		}
		HttpURLConnection connection = null;
		InputStream is = null;
		FileOutputStream fo = null;
		try {
			connection = getDownHttpURLConnection(myFileUrl, context);
			connection.setDoInput(true);
			connection.connect();

			is = connection.getInputStream();
			fileLength = connection.getContentLength();
			// if(f.exists()){
			// return;
			// }
			if (f.createNewFile()) {
				Log.i(TAG, "f.getAbsolutePath() = " + f.getAbsolutePath());
				fo = new FileOutputStream(f);
				byte[] buffer = new byte[256];
				int size;
				while ((size = is.read(buffer)) > 0) {
					fo.write(buffer, 0, size);
				}
			}
		} catch (MalformedURLException e) {
			Log.i(TAG, "URL is format error");
		} catch (IOException e) {
			e.printStackTrace();
			Log.i(TAG, "IO error when download file");
			Log.i(TAG, "The URL is " + url + ";the file name " + f.getName());
		} finally {
			try {
				if (fo != null) {
					fo.flush();
					fo.close();
				}
				if (is != null) {
					is.close();
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			urlMap.remove(url);
		}
		return fileLength;
	}

	public static HttpURLConnection getDownHttpURLConnection(URL url, Context context) {
		HttpURLConnection connection = null;
		Cursor mCursor = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			Log.e("tag", "wifiManager.isWifiEnabled() = " + wifiManager.isWifiEnabled());
			if (!wifiManager.isWifiEnabled()) {
				if (SetUtil.getWifiImage(context)) {
					// 设置非wifi下不下载图片之后，返回null。
					return null;
				} else {
					// 获取当前正在使用的APN接入点
					Uri uri = Uri.parse("content://telephony/carriers/preferapn");
					mCursor = context.getContentResolver().query(uri, null, null, null, null);
					if (mCursor != null && mCursor.moveToFirst()) {
						// 游标移至第一条记录，当然也只有一条
						String proxyStr = mCursor.getString(mCursor.getColumnIndex("proxy"));
						if (proxyStr != null && proxyStr.trim().length() > 0) {
							Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(proxyStr, 80));
							connection = (HttpURLConnection) url.openConnection(proxy);
						}
					}
				}
			}
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				return (HttpURLConnection) url.openConnection();
			} catch (IOException e1) {
				e1.printStackTrace();
				return null;
			}
		} finally {
			if (mCursor != null) {
				mCursor.close();
			}
		}

	}
}
