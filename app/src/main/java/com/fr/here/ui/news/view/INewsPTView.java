package com.fr.here.ui.news.view;

import com.fr.here.base.IBaseView;
import com.fr.here.ui.news.vo.News;

import java.util.List;

/**
 * 新闻页面
 * Created by shli on 2016-08-11.
 */
public interface INewsPTView extends IBaseView {
    /**
     * 填充banner
     * @param newsList
     */
    void setNewsPTBanner(List<News> newsList);

    /**
     * 刷新列表
     * @param newsList
     */
    void setNewsPTListRefresh(List<News> newsList);

    /**
     * 加载下一页
     * @param newsList
     */
    void setNewsPTListNext(List<News> newsList);
}
