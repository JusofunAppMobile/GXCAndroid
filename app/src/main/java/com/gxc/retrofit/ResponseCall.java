package com.gxc.retrofit;


import com.gxc.event.QuitAppEvent;
import com.gxc.utils.AppUtils;
import com.gxc.utils.LogUtils;
import com.gxc.utils.ToastUtils;

import de.greenrobot.event.EventBus;
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
        if (netModel.isRejectVisite()) {
            if (AppUtils.getUser() != null) {
                ToastUtils.show(netModel.msg);
                AppUtils.logout();
            }
            EventBus.getDefault().post(new QuitAppEvent());
        }
        success(netModel);
    }

    public abstract void success(NetModel model);

    public abstract void error();
}
