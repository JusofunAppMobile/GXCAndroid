package com.gxc.retrofit;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import com.gxc.event.FinishEvent;
import com.gxc.event.QuitAppEvent;
import com.gxc.utils.LogUtils;

import de.greenrobot.event.EventBus;
import netlib.util.AppUtil;
import rx.Observer;

public abstract class ResponseCall implements Observer<NetModel> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e(e.getMessage());
        error();
    }

    @Override
    public void onNext(NetModel netModel) {
        if(netModel.result==2019){
            QuitAppEvent event = new QuitAppEvent();
            event.msg = netModel.msg;
            EventBus.getDefault().post(event);
        }

        success(netModel);
    }

    public abstract void success(NetModel model);

    public abstract void error();
}
