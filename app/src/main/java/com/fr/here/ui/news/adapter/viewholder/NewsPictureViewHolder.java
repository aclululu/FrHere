package com.fr.here.ui.news.adapter.viewholder;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Util;
import com.fr.here.R;
import com.fr.here.ui.news.vo.News;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * 纯新闻图片holder
 * Created by shli on 2016-08-11.
 */
public class NewsPictureViewHolder extends BaseViewHolder<News> {
    private ImageView pic;
    private TextView title;

    public NewsPictureViewHolder(ViewGroup parent) {
        super(parent, R.layout.fragment_news_picture_item);
        title = $(R.id.title);
        pic = $(R.id.pic);
    }

    @Override
    public void setData(News data) {
        title.setText(data.title);
        Glide.with(getContext())
                .load(data.picture)
                .crossFade(0)
                .centerCrop()
                //.override(600,600)
                //.centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String,GlideDrawable>()
                {

                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource)
                    {
                        pic.setImageResource(R.drawable.pic_default);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource)
                    {

                        pic.setImageDrawable(resource);
                        return false;
                    }
                })
                .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL);
    }
}
