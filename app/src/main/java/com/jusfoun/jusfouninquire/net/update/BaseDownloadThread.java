package com.jusfoun.jusfouninquire.net.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.widget.RemoteViews;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.util.ResourceUtil;


public class BaseDownloadThread extends Thread {
	protected NotificationManager nm;
	protected Notification notification;
	protected RemoteViews views;
	protected Context context;
	protected ResourceUtil resourceUtil;

	public BaseDownloadThread(Context context) {
		super();
		this.context = context;
		this.resourceUtil = ResourceUtil.getInstance(context);
		this.nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		this.notification = new Notification(0, "", System.currentTimeMillis());

//		Notification notification = new Notification.Builder(context)
//				.setAutoCancel(true)
//				.setContentTitle(context.getResources().getString(R.string.download_title))
//				.setContentText("describe")
//				.setContentIntent(pendingIntent)
//				.setSmallIcon(R.drawable.ic_launcher)
//				.setWhen(System.currentTimeMillis())
//				.build();

		String tickerText = context.getResources().getString(R.string.download_title);
		notification.tickerText = tickerText;
		notification.icon = android.R.drawable.stat_sys_download;
		notification.flags = Notification.FLAG_ONGOING_EVENT;// 应用程序类，notification不能被cancel
		notification.when = System.currentTimeMillis();
		notification.defaults = Notification.DEFAULT_LIGHTS;
		views = new RemoteViews(context.getPackageName(), R.layout.download_warn);
		notification.contentView = views;
//		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
//		notification.setLatestEventInfo(context, "", "", contentIntent);
	}

}