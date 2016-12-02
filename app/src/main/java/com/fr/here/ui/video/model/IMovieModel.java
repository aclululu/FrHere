package com.fr.here.ui.video.model;


import com.fr.here.base.BaseResponse;
import com.fr.here.ui.video.vo.Movie;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 视频数据接口
 * Created by shli on 2016-08-18.
 */
public interface IMovieModel {
    /**
     * 获取视频列表
     * @param pageNum 页码   页容量   C
     * @return 视频列表
     */
    Observable<BaseResponse<Movie>> getMovieList(int pageNum);

}
