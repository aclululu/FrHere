package com.fr.here.ui.video.adapter;

import android.content.Context;
import android.view.ViewGroup;
import com.fr.here.ui.video.adapter.viewholder.MovieViewHolder;
import com.fr.here.ui.video.vo.Movie;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * 视频
 * Created by shli on 2016-08-18.
 */
public class MovieAdapter extends RecyclerArrayAdapter<Movie> {
    public MovieAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(parent);
    }
}
