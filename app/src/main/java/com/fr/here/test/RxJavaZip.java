package com.fr.here.test;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * zip
 * Created by shli on 2016-08-24.
 */
public class RxJavaZip {
    public static void main(String[] args){
        Observable
                .zip(getString(), getInteger(), new Func2<String, Integer, List<String>>() {
                    @Override
                    public List<String> call(String s, Integer integer) {
                        List<String> list = new ArrayList<>();
                        list.add(s);
                        list.add(String.valueOf(integer));
                        return list;
                    }
                })
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> list) {
                        return Observable.from(list);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });
    }

    public static Observable<String> getString(){
        List<String> strings = new ArrayList<>();
        strings.add("s");
        strings.add("ss");
        return  Observable.from(strings);
    }

    public static Observable<Integer> getInteger(){
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        //integers.add(2);
        return Observable.from(integers);
    }


}
