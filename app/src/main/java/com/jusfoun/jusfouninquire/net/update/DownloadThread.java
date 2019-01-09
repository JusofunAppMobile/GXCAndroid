package com.jusfoun.jusfouninquire.net.update;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.jusfoun.jusfouninquire.ui.util.StringUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import netlib.util.AppUtil;
import netlib.util.LibIOUtil;

public class DownloadThread extends BaseDownloadThread {

	private int nofiId;
	private String url;// 下载路径
	private String path;// 存放路径

	private boolean cancelUpdate = false;
	private int downloadPercent = 0;

	private int downloadSize;
	private int downloadSpeed;
	private long startTime;
	private long currentTime;
	private int usedTime;

	public static final int START_DOWNLOAD = 0;
	public static final int DOWNLOAD_ING = 1;
	public static final int DOWNLOAD_SUCC = 2;
	public static final int DOWNLOAD_FAIL = 3;

	private Handler mHandler = new Handler();
	private MyHandler myHandler;

	public DownloadThread(Context context, int nofiId, String url) {
		super(context);
		this.nofiId = nofiId;
		this.url = url;
		this.myHandler = new MyHandler(Looper.myLooper());
		this.path = LibIOUtil.getDownloadPath(context) + StringUtil.getMD5Str(url);
		Log.e("下载文件本地地址", path);
	}

	@Override
	public void run() {
		super.run();
		Log.e("线程开启", "文件下载");
		checkDownloade();
	}

	private synchronized void checkDownloade(){
		Log.e("进入下载方法", "synchronized解锁");
		HttpURLConnection conn = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			updateBegin();// 开始更新
			startTime = System.currentTimeMillis();
			conn = HttpClientUtil.getNewHttpURLConnection(new URL(url), context);
			Log.e("地址", url);
			conn.setDoInput(true);
			conn.setConnectTimeout(20 * 1000);
			conn.setReadTimeout(20 * 1000);
			conn.connect();
			long length = conn.getContentLength();
			is = conn.getInputStream();
			if (is != null) {
				downloadPercent = 0;
				downloadSpeed = 0;
				Message message = myHandler.obtainMessage(DOWNLOAD_ING, null);
				myHandler.sendMessage(message);
				File tempFile = new File(path);
				// 2262780
				// 2260866
				if (LibIOUtil.isFileExist(path)) {
					if(length==tempFile.length()){
						// 发送广播
						message = myHandler.obtainMessage(DOWNLOAD_SUCC, null);
						myHandler.sendMessage(message);
						mHandler.postDelayed(new Runnable() {

							@Override
							public void run() {
								AppUtil.installApk(context, path);
							}
						}, 300);
						conn.disconnect();
						return;
					} else {
						tempFile.delete();
					}
				}
				tempFile.createNewFile();
				bis = new BufferedInputStream(is);
				fos = new FileOutputStream(tempFile);
				bos = new BufferedOutputStream(fos);
				int read;
				long count = 0;
				int precent = 0;
				byte[] buffer = new byte[1024];
				while ((read = bis.read(buffer)) != -1 && !cancelUpdate) {
					bos.write(buffer, 0, read);

					count += read;
					downloadSize += read;

					precent = (int) (((double) count / length) * 100);
					if (precent - downloadPercent >= 3) {

						downloadPercent = precent;
						currentTime = System.currentTimeMillis();
						usedTime = (int) ((currentTime - startTime) / 1000);
						if (usedTime == 0) {
							usedTime = 1;
						}
						downloadSpeed = (downloadSize / usedTime) / 1024;
						Log.i("DownloadThread", "downloadPercent=" + downloadPercent);
						// 发送广播
						message = myHandler.obtainMessage(DOWNLOAD_ING, null);
						myHandler.sendMessage(message);
					}
				}
			}
			bos.flush();
			fos.flush();
			if (!cancelUpdate) {
				cancelUpdate = true;
				downloadPercent = 0;

				// 发送广播
				Message message = myHandler.obtainMessage(DOWNLOAD_SUCC, null);
				myHandler.sendMessage(message);
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						AppUtil.installApk(context, path);
					}
				}, 300);

			}
		} catch (Exception e) {
			e.printStackTrace();
			cancelUpdate = true;

			// 发送广播
			Message message = myHandler.obtainMessage(DOWNLOAD_FAIL, null);
			myHandler.sendMessage(message);
		} finally {
			updateEnd();
			try {
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				if (fos != null) {
					fos.flush();
					fos.close();
				}
				if (is != null) {
					is.close();
				}
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	private final class MyHandler extends Handler {

		public MyHandler(Looper myLooper) {
			super(myLooper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg == null) {
				return;
			}
			switch (msg.what) {
			case START_DOWNLOAD:
				break;
			case DOWNLOAD_SUCC:
				nm.cancel(nofiId);
				break;
			case DOWNLOAD_ING:
				String title = context.getResources().getString(resourceUtil.getStringId("download_title"));
				views.setTextViewText(resourceUtil.getViewId("download_title"), title);

				String progressTxt = StringBundleUtil.resolveString(resourceUtil.getStringId("download_progress"),
						new String[] { downloadPercent + "", downloadSpeed + "" }, context);
				views.setTextViewText(resourceUtil.getViewId("download_progress_text"), progressTxt);
				views.setProgressBar(resourceUtil.getViewId("download_progressbar"), 100, downloadPercent, false);

				notification.contentView = views;
				nm.notify(nofiId, notification);
				break;
			case DOWNLOAD_FAIL:
				String errorText = context.getResources().getString(resourceUtil.getStringId("download_fail"));
				Log.i("DownloadThread", "errorText" + errorText + "," + views);
				views.setTextViewText(resourceUtil.getViewId("download_progress_text"), errorText);
				notification.flags = Notification.FLAG_AUTO_CANCEL;

				Intent myIntent = new Intent(context, UpdateOSService.class);
				myIntent.putExtra(UpdateOSService.NOTIFICATION_ID, nofiId);
				myIntent.putExtra(UpdateOSService.URL, url);
				PendingIntent contentIntent = PendingIntent.getService(context, 0, myIntent,
						PendingIntent.FLAG_CANCEL_CURRENT);
				views.setOnClickPendingIntent(resourceUtil.getViewId("download_box"), contentIntent);

//				notification.setLatestEventInfo(context, "", "", contentIntent);
				notification.contentView = views;
				nm.notify(nofiId, notification);
				break;
			}
		}

	}

	private void updateBegin() {
		UpdateServiceHelper.saveState(true, context);
	}

	private void updateEnd() {
		UpdateServiceHelper.saveState(false, context);
		context.stopService(new Intent(context, UpdateOSService.class));
	}

	public void doCancel() {
		cancelUpdate = true;
		try {
			stop();
		} catch (Exception exception) {
		}
		UpdateServiceHelper.saveState(false, context);
	}

}