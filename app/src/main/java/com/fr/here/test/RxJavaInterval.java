package com.fr.here.test;


import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * interval
 * Created by shli on 2016-08-24.
 */
public class RxJavaInterval {
    public static void main(String[] strings){
        System.out.println("sss");
        Observable.interval(2000,2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long number) {
                        System.out.println ("hello world");
                    }
                });
    }
}
