package com.fr.here.ui.video.vo;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

/**
 * 视频
 * Created by shli on 2016-08-18.
 */
public class Movie implements Parcelable{
    public int id;
    public Date cdate;
    public String picurl;
    public String movieurl;
    public String title;
    public String abstract_;
    public String creator;

    public Movie() {
    }

    public Movie(int id, Date cdate, String picurl, String movieurl, String title, String abstract_, String creator) {
        this.id = id;
        this.cdate = cdate;
        this.picurl = picurl;
        this.movieurl = movieurl;
        this.title = title;
        this.abstract_ = abstract_;
        this.creator = creator;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        picurl = in.readString();
        movieurl = in.readString();
        title = in.readString();
        abstract_ = in.readString();
        creator = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", cdate=" + cdate +
                ", picurl='" + picurl + '\'' +
                ", movieurl='" + movieurl + '\'' +
                ", title='" + title + '\'' +
                ", abstract_='" + abstract_ + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(picurl);
        dest.writeString(movieurl);
        dest.writeString(title);
        dest.writeString(abstract_);
        dest.writeString(creator);
    }
}
