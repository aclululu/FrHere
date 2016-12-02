package com.fr.here.test;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * buffer
 * Created by shli on 2016-08-24.
 */
public class RxJavaBuffer {
    public static  void main (String[] s){
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        Observable.from(list)
                //.buffer(2)//buffer(count):每接收到count个数据包裹，将这count个包裹打包，发送给订阅者
                /**
                 * Buffer(count , skip):每接收到count个数据后，将该count的个数据打包，并跳过第skip个数据，发送给订阅者
                    案例：输入123456 ， 两两发送，并跳过之后的一个数据，即最后订阅者接收到[1,2] [4,5] 两个数据包裹， 3和6 分别被跳过
                 */
                .buffer(2,3)
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        System.out.println(strings.get(0)+"---"+strings.get(1));
                    }
                });
    }
}
