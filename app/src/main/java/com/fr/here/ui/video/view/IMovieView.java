package com.fr.here.ui.video.view;

import com.fr.here.base.IBaseView;
import com.fr.here.ui.video.vo.Movie;

import java.util.List;

/**
 * 视频界面接口
 * Created by shli on 2016-08-18.
 */
public interface IMovieView extends IBaseView{
    /**
     * 刷新列表
     * @param movieList
     */
    void setMovieListRefresh(List<Movie> movieList);

    /**
     * 加载下一页
     * @param movieList
     */
    void setMovieListNext(List<Movie> movieList);
}
