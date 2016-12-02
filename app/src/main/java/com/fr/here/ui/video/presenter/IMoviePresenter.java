package com.fr.here.ui.video.presenter;

/**
 * 视频数据逻辑
 * Created by shli on 2016-08-18.
 */
public interface IMoviePresenter {
    /**
     * 加载下一页数据
     */
    void loadNextList();

    /**
     * 刷新
     */
    void refleshList();

    /**
     * 取消订阅
     */
    void releaseSubscriber();
}
