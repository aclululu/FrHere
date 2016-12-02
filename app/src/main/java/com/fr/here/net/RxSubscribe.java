package com.fr.here.net;

/**
 * 针对网络请求封装的 观察者
 * Created by shli on 2016-08-12.
 */

import android.accounts.NetworkErrorException;

import com.fr.here.app.BaseApplication;
import com.fr.here.util.NetUtils;

import rx.Subscriber;

/**
 * 针对网络请求封装的 观察者
 * @param <T>
 */
public  abstract class RxSubscribe<T> extends Subscriber<T> {
    @Override
    public void onNext(T t) {
        _onNext(t);
    }
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (!NetUtils.isNetworkConnected(BaseApplication.getInstance())) {
            _onError("网络不可用");
        } else if (e instanceof NetworkErrorException) {
            _onError(e.getMessage());
        } else {
            _onError("请求失败，请稍后再试...");
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}