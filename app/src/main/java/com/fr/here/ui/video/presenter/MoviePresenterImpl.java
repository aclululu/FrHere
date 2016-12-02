package com.fr.here.ui.video.presenter;

import com.fr.here.base.BaseResponse;
import com.fr.here.net.FrHereApi;
import com.fr.here.net.RxHelper;
import com.fr.here.net.RxSubscribe;
import com.fr.here.ui.video.model.IMovieModel;
import com.fr.here.ui.video.model.MovieModelImpl;
import com.fr.here.ui.video.view.IMovieView;
import com.fr.here.ui.video.vo.Movie;
import com.fr.here.util.PLog;

import java.util.List;

import rx.Observable;

/**
 * 视频逻辑
 * Created by shli on 2016-08-18.
 */
public class MoviePresenterImpl implements IMoviePresenter{
    private IMovieModel model;
    private IMovieView view;
    private int pageNum = 1;

    public MoviePresenterImpl(IMovieView view){
        this.view = view;
        this.model = new MovieModelImpl();
    }

    public MoviePresenterImpl(IMovieModel model,IMovieView view){
        this.view = view;
        this.model = model;
    }
    Observable.Transformer<BaseResponse<Movie>, List<Movie>> ob = RxHelper.handleResultList();


    @Override
    public void loadNextList() {
        model.getMovieList(pageNum)
                .compose(ob)
                .subscribe(rxSubscribeLoad);
    }

    @Override
    public void refleshList() {
        model.getMovieList(1)
                .compose(ob)
                .subscribe(rxSubscribeReflesh);
    }

    @Override
    public void releaseSubscriber() {
        if(rxSubscribeReflesh!=null&&!rxSubscribeReflesh.isUnsubscribed()){
            rxSubscribeReflesh.unsubscribe();
        }
        if(rxSubscribeLoad!=null&&!rxSubscribeLoad.isUnsubscribed()){
            rxSubscribeLoad.unsubscribe();
        }
    }

    RxSubscribe<List<Movie>> rxSubscribeLoad =  new RxSubscribe<List<Movie>>() {
        @Override
        protected void _onNext(List<Movie> movies) {
            for (Movie movie : movies) {
                movie.picurl = FrHereApi.API_URL +movie.picurl;
                movie.movieurl = FrHereApi.API_URL+movie.movieurl;
            }
            view.setMovieListNext(movies);
            if(movies.size()!= 0) pageNum++;
        }

        @Override
        protected void _onError(String message) {
            view.showToast(message);
            view.setMovieListNext(null);
        }

        @Override
        public void onCompleted() {

        }
    };

    RxSubscribe<List<Movie>> rxSubscribeReflesh = new RxSubscribe<List<Movie>>() {
        @Override
        protected void _onNext(List<Movie> movies) {
            for (Movie movie : movies) {
                movie.picurl = FrHereApi.API_URL +movie.picurl;
                movie.movieurl = FrHereApi.API_URL+movie.movieurl;
            }
            view.setMovieListRefresh(movies);
        }

        @Override
        protected void _onError(String message) {
            view.showToast(message);
            view.setMovieListRefresh(null);
            pageNum = 1;
        }

        @Override
        public void onCompleted() {
            pageNum = 2;
        }
    };





}
