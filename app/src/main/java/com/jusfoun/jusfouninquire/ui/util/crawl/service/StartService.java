package com.jusfoun.jusfouninquire.ui.util.crawl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author zhaoyapeng
 * @version create time:17/8/216:40
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class StartService  extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent startIntent = new Intent(this, WebService.class);
        startService(startIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
