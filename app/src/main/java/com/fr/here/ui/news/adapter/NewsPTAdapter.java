package com.fr.here.ui.news.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.fr.here.ui.news.adapter.viewholder.NewsPicViewHolder;
import com.fr.here.ui.news.adapter.viewholder.NewsTextViewHolder;
import com.fr.here.ui.news.vo.News;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.security.InvalidParameterException;

/**
 * 图文新闻
 * Created by shli on 2016-08-11.
 */
public class NewsPTAdapter extends RecyclerArrayAdapter<News> {
    public NewsPTAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewType(int position) {
        return getItem(position).picnews;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new NewsPicViewHolder(parent);
        } else if (viewType == -1) {
            return new NewsTextViewHolder(parent);
        } else {
            throw  new InvalidParameterException();
        }
    }
}
