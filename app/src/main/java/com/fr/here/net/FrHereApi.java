package com.fr.here.net;

import com.fr.here.base.BaseResponse;
import com.fr.here.ui.news.vo.News;
import com.fr.here.ui.video.vo.Movie;
import com.fr.here.ui.welcome.vo.WelcomePic;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 所有网络请求
 * <br/>注：<font color="red">获取列表这种几个模块都会有方法，只是路径和返回值不同时，可以写在同一个方法里面，然后在model中各自填入参数可使用QueryMap </font>
 */
public interface FrHereApi {

    /**
     * http://aclululu.ngrok.cc
     * http://172.19.24.23
     * 全局
     */
    public final static String HOST = "172.19.24.23";
    public static String API_URL = "http://172.19.24.23";
    public final static String PING_URL = "http://"+HOST+"/fr-here/rest/app/";

    /**
     * 获取欢迎的第一个图片
     * @return
     */
    @GET("welcome/newpic")
    Observable<BaseResponse<WelcomePic>> getWelcomePic();

    /**
     * 获取新闻列表
     * @param picnews 是否是图片新闻  1是   -1不是    其他全部
     * @param pageNum 页码
     * @param pageSize 页容量
     * @return 新闻列表
     */
    @GET("news/list")
    Observable<BaseResponse<News>>  getNewsList(@Query("picnews") int picnews,@Query("pageNum") int pageNum,@Query("pageSize") int pageSize);

    /**
     * 获取头部显示的banner新闻图片轮放
     * @return banner新闻 4条
     */
    @GET("news/banner")
    Observable<BaseResponse<News>>  getNewsBanner();

    /**
     * 获取视频
     * @param pageNum  页面
     * @param pageSize 容量
     * @return 视频列表
     */
    @GET("movie/datalist")
    Observable<BaseResponse<Movie>> getMovieList(@Query("pageNum") int pageNum,@Query("pageSize") int pageSize);

    @Multipart
    @POST("test/upload1")
    Observable<BaseResponse<String>> upload1(@Part MultipartBody.Part file);

    @Multipart
    @POST("test/upload2")
    Observable<BaseResponse<String>> upload2(@Part("pictureName") RequestBody pictureName, @PartMap Map<String, RequestBody> params);



  /*  @GET("weather")
    Observable<WeatherAPI> mWeatherAPI(@Query("city") String city, @Query("key") String key);

    //而且在Retrofit 2.0中我们还可以在@Url里面定义完整的URL：这种情况下Base URL会被忽略。
    @GET("http://api.fir.im/apps/latest/5630e5f1f2fc425c52000006")
    Observable<VersionAPI> mVersionAPI(
            @Query("api_token") String api_token);*/



}
