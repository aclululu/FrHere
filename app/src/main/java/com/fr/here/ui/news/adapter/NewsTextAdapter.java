package com.fr.here.ui.news.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.fr.here.ui.news.adapter.viewholder.NewsPictureViewHolder;
import com.fr.here.ui.news.adapter.viewholder.NewsTextViewHolder;
import com.fr.here.ui.news.vo.News;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * 图片新闻
 * Created by shli on 2016-08-12.
 */
public class NewsTextAdapter extends RecyclerArrayAdapter<News> {
    public NewsTextAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsTextViewHolder(parent);
    }
}
