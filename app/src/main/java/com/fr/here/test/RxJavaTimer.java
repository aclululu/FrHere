package com.fr.here.test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * timer
 * Created by shli on 2016-08-24.
 */
public class RxJavaTimer {
    public static void main(String[] strings){
        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println("sssss");
                    }
                });
        PublishSubject<String> publishSubject = PublishSubject.create();
    }
}
