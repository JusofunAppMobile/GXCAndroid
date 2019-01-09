package com.jusfoun.jusfouninquire.ui.util.crawl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author zhaoyapeng
 * @version create time:17/8/317:41
 * @Email zyp@jusfoun.com
 * @Description ${守护service}
 */
public class GuardService  extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
