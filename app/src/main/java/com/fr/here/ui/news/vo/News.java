package com.fr.here.ui.news.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * 新闻
 * Created by shli on 2016-08-10.
 */
public class News implements Parcelable{
    public Integer id;
    public Integer cid;
    public String title;
    public String content;
    public String abstract_;
    public String keywords;
    public Integer picnews;
    public String picture;
    public String origin;
    public Date cdate;
    public String author;
    public String editor;
    public Integer clicks;
    public Integer status;
    public Integer priority;
    public String column;

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", cid=" + cid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", abstract_='" + abstract_ + '\'' +
                ", keywords='" + keywords + '\'' +
                ", picnews=" + picnews +
                ", picture='" + picture + '\'' +
                ", origin='" + origin + '\'' +
                ", cdate=" + cdate +
                ", author='" + author + '\'' +
                ", editor='" + editor + '\'' +
                ", clicks=" + clicks +
                ", status=" + status +
                ", priority=" + priority +
                ", column='" + column + '\'' +
                '}';
    }
    public  News(){}

    protected News(Parcel in) {
        title = in.readString();
        content = in.readString();
        abstract_ = in.readString();
        keywords = in.readString();
        picture = in.readString();
        origin = in.readString();
        author = in.readString();
        editor = in.readString();
        column = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(abstract_);
        dest.writeString(keywords);
        dest.writeString(picture);
        dest.writeString(origin);
        dest.writeString(author);
        dest.writeString(editor);
        dest.writeString(column);
    }
}
