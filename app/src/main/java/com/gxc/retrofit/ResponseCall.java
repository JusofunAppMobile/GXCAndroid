package com.gxc.retrofit;



import com.gxc.utils.LogUtils;

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
        success(netModel);
    }

    public abstract void success(NetModel model);

    public abstract void error();
}
