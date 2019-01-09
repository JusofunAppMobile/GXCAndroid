package com.jusfoun.jusfouninquire.net.util;

import android.content.Context;
import android.util.Log;

import com.jusfoun.jusfouninquire.net.update.HttpClientUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:15/12/1下午3:28
 * @Email zyp@jusfoun.com
 * @Description ${图片下载工具类}
 */
public class AsyncImageLoader {
    private static final String LOG_TAG = "AsyncImageLoader";

    private Context context;
    private MyThreadPool originalThreadPool;
    private HashMap<String, LoaderImageThread> threads;
    private HashMap<String, String> urlMap;
    private HashMap<String, List<BitmapCallback>> _callbacks;
    private final Object _lock = new Object();
    private String imagePath = null;
    private static AsyncImageLoader imageLoader;

    public static interface BitmapCallback {
        void onImageLoaded(String path, String url);
    }

    private AsyncImageLoader(Context context) {
        originalThreadPool = new MyThreadPool(11);
        this.context = context;
        _callbacks = new HashMap<String, List<BitmapCallback>>();
        threads = new HashMap<String, AsyncImageLoader.LoaderImageThread>();
        imagePath = LibIOUtil.getDowdloadImagePath(context);
    }

    public synchronized static AsyncImageLoader getInstance(Context context) {
        if (imageLoader == null) {
            imageLoader = new AsyncImageLoader(context);
        }
        return imageLoader;
    }

    private String loadSync(String TAG, String url, LoaderImageThread thread) {
        String path = getImageFilePath(url);
        File f = new File(imagePath + path);
        try {
            if (!f.exists() || (urlMap.containsKey(url) && !urlMap.get(url).equals(f.length()))) {
                long fileLength = HttpClientUtil.downloadFile(url, f, context);
                // 发送广播，刷新相册，图片在相册中显示
//                Uri localUri = Uri.fromFile(f);
//                Log.e("tag", "url=============" + localUri);
//                Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
//                context.sendBroadcast(localIntent);
                urlMap.put(url, fileLength + "");
                Log.e("tag","fileLength="+fileLength);
                if(fileLength==0){
                    return null;
                }
            }
            if (thread == null || thread.isInterrupted()) {
                return null;
            }

        } catch (Exception ex) {
            LibIOUtil.deleteFile(imagePath+path);
            ex.printStackTrace();
        }
        return ""+imagePath + path;
    }

    public void loadAsync(String TAG, final String url, BitmapCallback callback) {
        urlMap = new HashMap<String, String>();
        if (url == null || url.equals("")) {
            String path = imagePath + getImageFilePath(url);
            callback.onImageLoaded(path, url);
            return;
        }
        if (threads.containsKey(TAG + url)) {
            threads.remove(TAG + url);
        }
        synchronized (_lock) {
            List<BitmapCallback> callbacks = _callbacks.get(TAG + url);
            if (callbacks != null) {
                if (callback != null) {
                    callbacks.add(callback);
                }
                return;
            }

            callbacks = new ArrayList<>();
            if (callback != null) {
                callbacks.add(callback);
            }
            _callbacks.put(TAG + url, callbacks);
        }
        LoaderImageThread loaderImageThread = new LoaderImageThread(TAG, url);
        if (!threads.containsKey(TAG + url)) {
            threads.put(TAG + url, loaderImageThread);
            originalThreadPool.submit(threads.get(TAG + url));
            Log.d(LOG_TAG, "URL:" + TAG + url);
        }
    }

    private class LoaderImageThread extends Thread {
        private boolean isInterrupted;
        private String url;
        private String TAG;

        public LoaderImageThread(String TAG, String url) {
            super();
            this.url = url;
            this.TAG = TAG;
        }

        // public void setInterrupted(boolean isInterrupted) {
        // this.isInterrupted = isInterrupted;
        // }

        public boolean isInterrupted() {
            return isInterrupted;
        }

        @Override
        public void run() {

            try {
                String d = loadSync(TAG, url, this);
                List<BitmapCallback> callbacks;

                synchronized (_lock) {
                    callbacks = _callbacks.remove(TAG + url);
                }

                for (BitmapCallback iter : callbacks) {
                    if (isInterrupted()) {
                        d = null;
                        return;
                    }
                    iter.onImageLoaded(d, url);
                }
            } catch (Exception e) {


                e.printStackTrace();
            }
        }
    }

    private String getImagePath(String imageUrl) throws MalformedURLException {
        if (!LibIOUtil.getExternalStorageState()) {
            return null;
        }
        URL url = new URL(imageUrl);
        String urlSplite = url.getPath();
        String filePathName = urlSplite.substring(urlSplite.lastIndexOf(LibIOUtil.FS) + 1);
        return filePathName;
    }

    public String getImageFilePath(String imgUrl) {
        Log.e("tag","imgUrl="+imgUrl);
        String filePathName = null;
        try {
            filePathName = getImagePath(imgUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e("tag","filePathName="+filePathName);
        LibIOUtil.makeDirs(filePathName.toString());
        return filePathName.toString();
    }
}

