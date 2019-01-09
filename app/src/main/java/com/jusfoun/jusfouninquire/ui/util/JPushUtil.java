package com.jusfoun.jusfouninquire.ui.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.JpushModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.service.event.CenterViewChangedEvent;
import com.jusfoun.jusfouninquire.service.event.MsgChangeEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailActivity;
import com.jusfoun.jusfouninquire.ui.activity.SplashActivity;
import com.jusfoun.jusfouninquire.ui.activity.SystemMessageActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/27.
 * Description
 */
public class JPushUtil {

    private static final String TAG = "JPushUtil";
    private static final String A = "a";
    private static final String B = "b";
    private static final String C = "c";
    private static final String D = "d";
    private static final String E = "e";

    //自定义消息处理
    public static void processCustomMessage(Context context, Bundle bundle) {
        UserInfoModel userinfo = LoginSharePreference.getUserInfo(context);
        if ((userinfo != null) && (!TextUtils.isEmpty(userinfo.getUserid()))) {
            MsgChangeEvent event = new MsgChangeEvent();
            event.setDelta(1);
            EventBus.getDefault().post(event);
            if (!TextUtils.isEmpty(userinfo.getSystemmessageunread())) {
                int msg_unread = Integer.parseInt(userinfo.getSystemmessageunread());
                userinfo.setSystemmessageunread(msg_unread + 1 + "");
                LoginSharePreference.saveUserInfo(userinfo, context);
            }
        }
        String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        JpushModel model = new Gson().fromJson(msg, JpushModel.class);
        PendingIntent pendingIntent = null;
        Intent intent = new Intent();
        if ("1".equals(model.getC())) {
            if ((userinfo != null) && (!TextUtils.isEmpty(userinfo.getUserid()))) {
                if (!TextUtils.isEmpty(userinfo.getMyfocusunread())) {
                    int focus_unread = Integer.parseInt(userinfo.getMyfocusunread());
                    userinfo.setMyfocusunread(focus_unread + 1 + "");
                    LoginSharePreference.saveUserInfo(userinfo, context);
                }
            }

            intent.setClass(context, CompanyDetailActivity.class);
            Bundle argument = new Bundle();
            argument.putString(CompanyDetailActivity.COMPANY_ID, model.getD());
            intent.putExtras(argument);
        } else if ("2".equals(model.getC())) {
            intent.setClass(context, WebActivity.class);
            Bundle argument = new Bundle();
            argument.putString(WebActivity.TITLE, model.getA());
            argument.putString(WebActivity.URL, model.getE());
            if (com.jusfoun.jusfouninquire.net.util.AppUtil.appIsRun(context)) {
                argument.putBoolean("goHome", false);
            }else{
                argument.putBoolean("goHome", true);
            }
            intent.putExtras(argument);
        } else if ("3".equals(model.getC())) {
            intent.setComponent(new ComponentName(netlib.util.AppUtil.getPackageName(context), SplashActivity.class.getName()));
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } else if ("4".equals(model.getC())) {//跳转系统消息
            UserInfoModel userInfoModel = LoginSharePreference.getUserInfo(context);
            if ((userInfoModel != null) && !TextUtils.isEmpty(userInfoModel.getUserid())) {
                intent.setClass(context, SystemMessageActivity.class);
            }


        }
        pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.icon_small)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(model.getA())
                .setContentText(model.getB())
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        notificationManager.notify(notifactionId, builder.build());

        EventBus.getDefault().post(new CenterViewChangedEvent());
    }
}
