package com.fr.here.ui.news.model;

import com.fr.here.base.BaseResponse;
import com.fr.here.ui.news.vo.News;

import rx.Observable;

/**
 * 新闻
 * Created by shli on 2016-08-11.
 */
public interface INewsModel {
    /**
     * 获取新闻列表
     * @param picnews 是否是图片新闻  1是   -1不是    其他全部
     * @param pageNum 页码   页容量   C
     * @return 新闻列表
     */
    Observable<BaseResponse<News>> getNewsList(int picnews,int pageNum);

    /**
     * 获取头部显示的banner新闻图片轮放
     * @return banner新闻 4条
     */
    Observable<BaseResponse<News>>  getNewsBanner();
}
