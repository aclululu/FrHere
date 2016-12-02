package com.fr.here.ui.video.model;

import com.fr.here.app.C;
import com.fr.here.base.BaseResponse;
import com.fr.here.net.RetrofitSingleton;
import com.fr.here.ui.video.vo.Movie;

import rx.Observable;

/**
 * 视频
 * Created by shli on 2016-08-18.
 */
public class MovieModelImpl implements IMovieModel{

    @Override
    public Observable<BaseResponse<Movie>> getMovieList(int pageNum) {
        return  RetrofitSingleton.apiService.getMovieList(pageNum, C.PAGE_SIZE);
    }
}
