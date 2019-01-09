package com.jusfoun.jusfouninquire.ui.util.crawl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jusfoun.jusfouninquire.ui.util.crawl.service.WebService;

/**
 * @author zhaoyapeng
 * @version create time:17/8/216:50
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class TaskBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("tag","TaskBroadcastReceiver");
        Intent start = new Intent(context, WebService.class);
        context.startService(start);
    }
}
