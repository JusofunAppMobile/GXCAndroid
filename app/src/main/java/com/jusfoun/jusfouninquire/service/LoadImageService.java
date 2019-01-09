package com.jusfoun.jusfouninquire.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jusfoun.jusfouninquire.net.util.AsyncImageLoader;
import com.jusfoun.jusfouninquire.net.util.NetUtil;
import com.jusfoun.jusfouninquire.sharedpreference.AdvertiseSharePreference;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;

/**
 * @author zhaoyapeng
 * @version create time:15/12/1下午4:58
 * @Email zyp@jusfoun.com
 * @Description ${加载图片 service}
 */
public class LoadImageService extends Service {

    private int CODE_LOAD = 100001;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("tag", "service-oncreate");
//        loadImage();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("tag", "service-onStartCommand");
        loadImage();
        return super.onStartCommand(intent, flags, startId);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            loadImage();
        }
    };


    protected void loadImage() {
        AsyncImageLoader.getInstance(this).loadAsync(getPackageName(), AdvertiseSharePreference.getNewImageUrl(getApplicationContext()),
                new AsyncImageLoader.BitmapCallback() {

                    @Override
                    public void onImageLoaded(String path, String url) {
                        Log.e("tag", "path=" + path);
                        if (path == null && NetUtil.isNetworkConnected(getApplicationContext())) {
                            handler.sendEmptyMessageDelayed(CODE_LOAD, 1000);
                        } else if (path != null) {
                            AdvertiseSharePreference.setNewDownState(getApplicationContext(),path);
                        }
                    }
                });
    }
}
