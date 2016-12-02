package com.fr.here.ui.news.model;

import com.fr.here.app.C;
import com.fr.here.base.BaseResponse;
import com.fr.here.net.RetrofitSingleton;
import com.fr.here.ui.news.vo.News;

import rx.Observable;

/**
 * 新闻
 * Created by shli on 2016-08-11.
 */
public class NewsModelImpl implements INewsModel {
    @Override
    public Observable<BaseResponse<News>> getNewsList(int picnews, int pageNum) {
        return RetrofitSingleton.apiService.getNewsList(picnews,pageNum, C.PAGE_SIZE);
    }

    @Override
    public Observable<BaseResponse<News>> getNewsBanner() {
        return RetrofitSingleton.apiService.getNewsBanner();
    }
}
