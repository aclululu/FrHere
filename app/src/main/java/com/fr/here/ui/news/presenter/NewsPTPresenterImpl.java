package com.fr.here.ui.news.presenter;

import com.fr.here.base.BaseResponse;
import com.fr.here.net.FrHereApi;
import com.fr.here.ui.news.model.INewsModel;
import com.fr.here.ui.news.model.NewsModelImpl;
import com.fr.here.ui.news.view.INewsPTView;
import com.fr.here.ui.news.vo.News;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 新闻逻辑层
 * Created by shli on 2016-08-11.
 */
public class NewsPTPresenterImpl implements INewsPTPresenter{
    private INewsModel model;
    private INewsPTView view;
    private int pageNum = 1;
    private Subscription subscriptionreflesh;
    private Subscription subscriptionload;
    private Subscription subscriptionnext;

    public NewsPTPresenterImpl (INewsPTView view){
        this.model = new NewsModelImpl();
        this.view = view;
    }

    @Override
    public void loadNewsBanner() {
        subscriptionload = model.getNewsBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<News>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.setNewsPTBanner(null);
                    }

                    @Override
                    public void onNext(BaseResponse<News> newsBaseResponse) {
                        //picture='/fr-here/news_image/news_20160811085832.png'
                        for (News news : newsBaseResponse.list) {
                            news.picture = FrHereApi.API_URL +news.picture;
                        }
                        view.setNewsPTBanner(newsBaseResponse.list);
                    }
                });
    }

    @Override
    public void loadNextNewsList(int picture) {
        subscriptionnext =  model.getNewsList(picture,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<News>>() {
                    @Override
                    public void onCompleted() {
                        //pageNum++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.setNewsPTListNext(null);
                    }

                    @Override
                    public void onNext(BaseResponse<News> newsBaseResponse) {
                        for (News news : newsBaseResponse.list) {
                            if(news.picnews == 1)
                            news.picture = FrHereApi.API_URL +news.picture;
                        }
                        view.setNewsPTListNext(newsBaseResponse.list);
                        if(newsBaseResponse.list.size()!= 0) pageNum++;
                    }
                });
    }

    @Override
    public void refleshNewsList(int pictu) {
        subscriptionreflesh = model.getNewsList(pictu,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<News>>() {
                    @Override
                    public void onCompleted() {
                        pageNum = 2;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.setNewsPTListRefresh(null);
                        pageNum = 1;
                    }

                    @Override
                    public void onNext(BaseResponse<News> newsBaseResponse) {
                        for (News news : newsBaseResponse.list) {
                            if(news.picnews == 1)
                                news.picture = FrHereApi.API_URL +news.picture;
                        }
                        view.setNewsPTListRefresh(newsBaseResponse.list);
                    }
                });
    }

    @Override
    public void releaseSubscriber() {
        if(subscriptionreflesh!=null&&!subscriptionreflesh.isUnsubscribed()){
            subscriptionreflesh.unsubscribe();
        }
        if(subscriptionnext!=null&&!subscriptionnext.isUnsubscribed()){
            subscriptionnext.unsubscribe();
        }
        if(subscriptionload!=null&&!subscriptionload.isUnsubscribed()){
            subscriptionload.unsubscribe();
        }
    }
}
