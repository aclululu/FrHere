package com.fr.here.test;


import java.io.Serializable;

import rx.Observable;
import rx.functions.Action1;

/**
 * merge
 *
 * Created by shli on 2016-08-24.
 */
public class RxJavaMerge {
    public static void main(String[] s){
        Observable.merge(RxJavaZip.getInteger(),RxJavaZip.getString())
                .subscribe(new Action1<Serializable>() {
                    @Override
                    public void call(Serializable serializable) {
                        System.out.println(serializable);
                    }
                });
    }
}
