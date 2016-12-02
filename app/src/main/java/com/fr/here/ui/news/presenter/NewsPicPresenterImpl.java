package com.fr.here.ui.news.presenter;

import com.fr.here.base.BaseResponse;
import com.fr.here.net.FrHereApi;
import com.fr.here.net.RxHelper;
import com.fr.here.net.RxSubscribe;
import com.fr.here.ui.news.model.INewsModel;
import com.fr.here.ui.news.model.NewsModelImpl;
import com.fr.here.ui.news.view.INewsPTView;
import com.fr.here.ui.news.vo.News;
import java.util.List;
import rx.Observable;
import rx.Subscription;

/**
 * 图片新闻
 * Created by shli on 2016-08-12.
 */
public class NewsPicPresenterImpl implements INewsPTPresenter{
    private INewsModel model;
    private INewsPTView view;
    private int pageNum = 1;
    private Subscription subscriptionreflesh;
    private Subscription subscriptionload;
    private Subscription subscriptionnext;

    Observable.Transformer<BaseResponse<News>, List<News>> ob = RxHelper.handleResultList();
    public NewsPicPresenterImpl (INewsPTView view){
        this.model = new NewsModelImpl();
        this.view = view;
    }

    @Override
    public void loadNewsBanner() {
        subscriptionload = model.getNewsBanner()
                .compose(ob)
                .subscribe(new RxSubscribe<List<News>>() {
                    @Override
                    protected void _onNext(List<News> newses) {
                        for (News news : newses) {
                            news.picture = FrHereApi.API_URL +news.picture;
                        }
                        view.setNewsPTBanner(newses);
                    }

                    @Override
                    protected void _onError(String message) {
                        view.showToast(message);
                        view.setNewsPTBanner(null);
                    }
                    @Override
                    public void onCompleted() {

                    }
                });

    }

    @Override
    public void loadNextNewsList(int pic) {
        subscriptionnext = model.getNewsList(pic,pageNum)
                .compose(ob)
                .subscribe(new RxSubscribe<List<News>>() {
                    @Override
                    protected void _onNext(List<News> newses) {
                        for (News news : newses) {
                            news.picture = FrHereApi.API_URL +news.picture;
                        }
                        view.setNewsPTListNext(newses);
                        if(newses.size()!= 0) pageNum++;
                    }

                    @Override
                    protected void _onError(String message) {
                        view.showToast(message);
                        view.setNewsPTListNext(null);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    @Override
    public void refleshNewsList(int pic) {
        subscriptionreflesh = model.getNewsList(pic,1)
                .compose(ob)
                .subscribe(new RxSubscribe<List<News>>() {
                    @Override
                    protected void _onNext(List<News> newses) {
                        for (News news : newses) {
                            news.picture = FrHereApi.API_URL +news.picture;
                        }
                        view.setNewsPTListRefresh(newses);
                    }

                    @Override
                    protected void _onError(String message) {
                        view.showToast(message);
                        view.setNewsPTListRefresh(null);
                        pageNum = 1;
                    }

                    @Override
                    public void onCompleted() {
                        pageNum = 2;
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
