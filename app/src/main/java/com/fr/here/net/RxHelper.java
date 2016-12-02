package com.fr.here.net;

import android.accounts.NetworkErrorException;

import com.fr.here.app.BaseApplication;
import com.fr.here.base.BaseResponse;
import com.fr.here.util.NetUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * RxJava帮助类
 * Created by shli on 2016-08-12.
 * 使用规范：
 * Api.getDefault().login(phone,password)
         .compost(RxHelper.handleResult())
        .subscribe(new Action1<LoginData>>(){
        @Override
        public void call(LoginData data){

        }
        });


 */
public class RxHelper {
    /**
     * 对结果进行预处理  对返回值为List的结果进行处理
     * @param  <T>
     * @return
     */
    public static  <T> Observable.Transformer<BaseResponse<T>, List<T>> handleResultList() {
        return new Observable.Transformer<BaseResponse<T>, List<T>>() {
            @Override
            public Observable<List<T>> call(Observable<BaseResponse<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseResponse<T>, Observable<List<T>>>() {
                    @Override
                    public Observable<List<T>> call(BaseResponse<T> result) {
                        if (result.result.isOK()) {
                            return createData(result.list);
                        } else {
                            return Observable.error(new NetworkErrorException(result.result.errorMessage));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 对结果进行预处理   对返回值问model的结果进行处理
     * @param  <T>
     * @return
     */
    public static  <T> Observable.Transformer<BaseResponse<T>, T> handleResultModel() {
        return new Observable.Transformer<BaseResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseResponse<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseResponse<T> result) {
                        if (result.result.isOK()) {
                           return createData(result.model);
                        } else {
                            return Observable.error(new NetworkErrorException(result.result.errorMessage));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }



}
