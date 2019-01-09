package com.jusfoun.jusfouninquire.net.update;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdateOSService extends Service {

	public static final String NOTIFICATION_ID = "nofiId";
	public static final String URL = "url";

	private int nofiId;
	private String url;
	
	private DownloadThread downloadThread;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.d("tag", "UpdateOSService    onStartCommand() ");
		return START_NOT_STICKY;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if (intent != null) {
			nofiId = intent.getIntExtra(NOTIFICATION_ID, -100000000);
			url = intent.getStringExtra(URL);
		}
//		if(downloadThread != null){
//			downloadThread.doCancel();
//			downloadThread = null;
//		}
		Log.d("tag", "UpdateOSService    onStart()  url = " + url);
		downloadThread = new DownloadThread(this, nofiId, url);
		downloadThread.start();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopSelf();
		Log.d("tag", "UpdateOSService    onDestroy()  downloadThread.doCancel() ");
		//downloadThread.doCancel();
		downloadThread = null;
	}

}