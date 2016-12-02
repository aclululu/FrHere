package com.fr.here.ui.video.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fr.here.R;
import com.fr.here.ui.video.vo.Movie;
import com.fr.here.util.CommonUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 视频holder
 * Created by shli on 2016-08-18.
 */
public class MovieViewHolder extends BaseViewHolder<Movie> {
    private JCVideoPlayerStandard videoplayer;
    private TextView title;
    private TextView abstract_;
    private TextView author;
    private TextView cdate;

    public MovieViewHolder(ViewGroup parent) {
        super(parent, R.layout.fragment_movie_item);
        videoplayer = $(R.id.videoplayer);
        title = $(R.id.title);
        abstract_ = $(R.id.abstract_);
        author = $(R.id.author);
        cdate = $(R.id.cdate);
        //videoplayer.startWindowTiny();
        itemView.setTag(videoplayer);
    }

    @Override
    public void setData(Movie data) {
        videoplayer.setUp(
                data.movieurl, JCVideoPlayer.SCREEN_LAYOUT_LIST,
                data.title);
        title.setText(data.title);
        abstract_.setText(data.abstract_);
        author.setText(data.creator);
        cdate.setText(CommonUtils.formatDate(data.cdate));

        Glide.with(getContext())
                .load(data.picurl)
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.pic_loading)
                .into(videoplayer.thumbImageView);
    }
}
