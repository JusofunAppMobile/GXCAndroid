package com.gxc.retrofit;



import com.jusfoun.jusfouninquire.service.event.IEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {

    private Subject<IEvent, IEvent> mSubject;
    private final ConcurrentHashMap<Object,Set<Subscription>> subscriptions=new ConcurrentHashMap<>();

    private static volatile RxBus rxBus = new RxBus();

    public RxBus() {
        mSubject = new SerializedSubject<>(PublishSubject.<IEvent>create());
    }

    public static RxBus getDefault() {
        return rxBus;
    }


    /**
    *
    * 注册事件
    * */
    public void register( Action1<IEvent> action, Object tag) {
        Subscription subscription = toObservable(IEvent.class).subscribe(action, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
        addSubscriptions(tag,subscription);
    }

    /**
     * 发送事件
     */
    public void post(IEvent event) {
        mSubject.onNext(event);
    }


    /**
     * 返回指定类型的Observable实例
     */
    public <T> Observable<T> toObservable(final Class<T> type) {
        return mSubject.ofType(type);
    }

    /**
     * 已订阅集合，需要进行解注册防止内存泄漏
     * 解除注册
     */
    private void unregisterEvent(Subscription subscription) {
        if (subscription!=null&&!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    private void unregisterEventList(Set<Subscription> subscriptions) {
        for (Subscription sub : subscriptions){
            unregisterEvent(sub);
        }
    }


    private  void addSubscriptions(Object tag, Subscription subscription) {
        if (tag==null)
            return;
        if(subscriptions.get(tag)==null){
            subscriptions.put(tag,new HashSet<Subscription>());
        }
        subscriptions.get(tag).add(subscription);
    }
    public  void removeSubscriptions(Object tag) {
        if (tag==null)
            return;
        if(subscriptions.get(tag)!=null){
            unregisterEventList(subscriptions.get(tag));
        }
    }
}
