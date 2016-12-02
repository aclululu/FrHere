package com.fr.here.ui.news.presenter;

/**
 * 新闻逻辑接口
 * Created by shli on 2016-08-11.
 */
public interface INewsPTPresenter {
    /**
     * 加载banner数据
     */
    void loadNewsBanner();

    /**
     * 加载下一页数据
     */
    void loadNextNewsList(int picture);

    /**
     * 刷新
     */
    void refleshNewsList(int picture);

    /**
     * 取消订阅
     */
    void releaseSubscriber();

}
