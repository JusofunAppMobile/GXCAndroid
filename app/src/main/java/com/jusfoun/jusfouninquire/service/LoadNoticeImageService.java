package com.jusfoun.jusfouninquire.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import com.jusfoun.jusfouninquire.net.util.AsyncImageLoader;
import com.jusfoun.jusfouninquire.net.util.NetUtil;
import com.jusfoun.jusfouninquire.service.event.NoticeImageLoadEvent;
import com.jusfoun.jusfouninquire.sharedpreference.NoticeImagePreference;

import de.greenrobot.event.EventBus;

/**
 * Created by Albert on 2016-3-31.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:下载通知图片
 */
public class LoadNoticeImageService  extends Service{
    public final static String CONTENT_IMAGE_URL = "content_image_url";
    public final static String CONTENT_BTN_URL = "content_btn_url";

    private String imageURL,btnURL,noticeID;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            imageURL = intent.getStringExtra(CONTENT_IMAGE_URL);
            btnURL = intent.getStringExtra(CONTENT_BTN_URL);
            loadImage();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            EventBus.getDefault().post(new NoticeImageLoadEvent(1));
        }
    };

    private void loadImage(){
        if (!TextUtils.isEmpty(imageURL)){
            AsyncImageLoader.getInstance(this).loadAsync(getPackageName(), imageURL,
                    new AsyncImageLoader.BitmapCallback() {

                        @Override
                        public void onImageLoaded(String path, String url) {
                            if (path == null && NetUtil.isNetworkConnected(getApplicationContext())) {

                            } else if (path != null) {
                                handler.sendEmptyMessage(0);
                                NoticeImagePreference.setNoticeImageUrl(getApplicationContext(), path);
                            }
                        }
                    });
        }


        if (!TextUtils.isEmpty(btnURL)){
            AsyncImageLoader.getInstance(this).loadAsync(getPackageName(), btnURL,
                    new AsyncImageLoader.BitmapCallback() {

                        @Override
                        public void onImageLoaded(String path, String url) {
                            if (path == null && NetUtil.isNetworkConnected(getApplicationContext())) {

                            } else if (path != null) {
                                handler.sendEmptyMessage(0);
                                NoticeImagePreference.setNoticeBtnUrl(getApplicationContext(), path);
                            }
                        }
                    });
        }

    }
}
