package com.fr.here.ui.news.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fr.here.R;
import com.fr.here.ui.news.vo.News;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * 新闻图片holder
 * Created by shli on 2016-08-11.
 */
public class NewsPicViewHolder extends BaseViewHolder<News> {
    private ImageView pic;
    private TextView title;

    public NewsPicViewHolder(ViewGroup parent) {
        super(parent, R.layout.fragment_news_pt_pic_item);
        title = $(R.id.title);
        pic = $(R.id.pic);
    }

    @Override
    public void setData(News data) {
        title.setText(data.title);

        Glide.with(getContext())
                .load(data.picture)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.pic_loading)
                .error(R.drawable.pic_loading)
                .into(pic);
    }
}
