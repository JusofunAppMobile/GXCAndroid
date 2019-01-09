package com.jusfoun.jusfouninquire.ui.util.crawl.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jusfoun.jusfouninquire.ui.util.LogUtil;
import com.jusfoun.jusfouninquire.ui.util.crawl.BaseReptileRequest;
import com.jusfoun.jusfouninquire.ui.util.crawl.GetTaskRequest;
import com.jusfoun.jusfouninquire.ui.util.crawl.HtmlRequest;
import com.jusfoun.jusfouninquire.ui.util.crawl.ReptileUtil;
import com.jusfoun.jusfouninquire.ui.util.crawl.SubmitTaskRequest;
import com.jusfoun.jusfouninquire.ui.util.crawl.TaskSharedpreference;

/**
 * @author zhaoyapeng
 * @version create time:17/7/2710:52
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class WebService extends Service {

    private Handler handler;
    private boolean isFIrstNetState =true; //应用启动会收到一次网络变化

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWordReceiver,intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e("tag","onStartCommand");
        handler.removeMessages(0);
        ReptileUtil.getInatance().reSet();
        BaseReptileRequest reptileRequest;
        if (TaskSharedpreference.getTask(this) == null) {
            LogUtil.e("tag","request new task");
             reptileRequest = new GetTaskRequest(this, handler);
        }else if(TaskSharedpreference.getTaskResult(this)==null){
            LogUtil.e("tag","request task data");
            reptileRequest = new HtmlRequest(this, handler,null);
        }else{
            LogUtil.e("tag","submit task data");
            reptileRequest = new SubmitTaskRequest(this,handler,null);
        }

        ReptileUtil.getInatance().add(reptileRequest);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netWordReceiver);
        handler.removeMessages(0);
    }


    /**
     * 网络状态 广播监听
     * 1. 手机无网络 停掉 所有任务 （未停止队列，不需要？？？）
     * 2. 手机有网络 重启service
     * */
    private BroadcastReceiver netWordReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(isFIrstNetState){
                isFIrstNetState = false;
                return;
            }
            ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
//
//            }else {
//
//            }

            if(mobNetInfo.isConnected()||wifiNetInfo!=null&&wifiNetInfo.isConnected()){
                LogUtil.e("tag", "手机有网络");
                TaskSharedpreference.clearTask(context);
                Intent reIntent= new Intent(context,WebService.class);
                context.startService(reIntent);
            }else{
                LogUtil.e("tag", "手机无网络");
                handler.removeMessages(0);
            }
        }
    };


}
