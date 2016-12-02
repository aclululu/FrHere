package com.fr.here.ui.news.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fr.here.R;
import com.fr.here.ui.news.vo.News;
import com.fr.here.util.CommonUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
/**
 * 纯文本新闻的holder
 * Created by shli on 2016-08-11.
 */
public class NewsTextViewHolder extends BaseViewHolder<News> {

    private TextView title;
    private TextView abstract_;
    private TextView column;
    private TextView author;
    private TextView cdate;


    public NewsTextViewHolder(ViewGroup parent) {
        super(parent, R.layout.fragment_news_pt_text_item);
        title = $(R.id.title);
        abstract_ = $(R.id.abstract_);
        column = $(R.id.column);
        author = $(R.id.author);
        cdate = $(R.id.cdate);
    }

    @Override
    public void setData(News data) {
        title.setText(data.title);
        abstract_.setText(data.abstract_);
        column.setText(data.column);
        author.setText(data.author);
        cdate.setText(CommonUtils.formatDate(data.cdate));
    }
}
