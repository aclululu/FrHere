package com.fr.here.ui.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fr.here.R;
import com.fr.here.ui.news.G;
import com.fr.here.ui.news.adapter.viewholder.NewsPicViewHolder;
import com.fr.here.ui.news.vo.News;
import com.fr.here.ui.news.widget.NewsDetail;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * banner
 * Created by shli on 2016-08-11.
 */
public class BannerAdapter extends StaticPagerAdapter {
    private List<News> list = new ArrayList<News>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    public BannerAdapter (Context context,List<News> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_news_banner_pic_item, null);
        //加载图片
        Glide.with(context)
                .load(list.get(position).picture)
                .crossFade()
                .placeholder(R.drawable.pic_default)
                .error(R.drawable.pic_default)
                .into((ImageView)view.findViewById(R.id.pic));
        ((TextView)view.findViewById(R.id.title)).setText(list.get(position).title);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                News news = list.get(position);
                v.setTag(news);
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(v);
                }
                //Toast.makeText(context,"123"+list.get(position).title,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;

    }
    public interface OnItemClickListener{
        void onItemClick(View view);
    }
    @Override
    public int getCount() {
        return list.size();
    }


}
