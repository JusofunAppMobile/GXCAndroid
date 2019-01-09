package com.gxc.retrofit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RxManager {

    // 订阅管理类 持有所有订阅的引用
    private CompositeSubscription compositeSubscription;

    public RxManager() {
        compositeSubscription = new CompositeSubscription();
    }

    public void addSubToManager(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    public static  <T extends NetModel> void http(Observable<T> observable, Observer<T> observer) {
        RxManager rxManager = new RxManager();
        rxManager.compositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(observer));
    }

}
