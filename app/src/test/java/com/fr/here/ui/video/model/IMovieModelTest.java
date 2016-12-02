package com.fr.here.ui.video.model;

import com.fr.here.MockRetrofitHelper;
import com.fr.here.base.BaseResponse;
import com.fr.here.net.FrHereApi;
import com.fr.here.ui.video.vo.Movie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.observers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * 针对model做的单元测试
 * Created by shli on 2016-08-19.
 */
public class IMovieModelTest {
    MockRetrofitHelper retrofit;
    FrHereApi movieModel;

    @Before
    public void setUp() throws Exception {
        retrofit = new MockRetrofitHelper();

        movieModel = retrofit.create(FrHereApi.class);
    }

    @Test
    public void testGetMovieList() throws Exception {
        retrofit.setPath("{\"result\":{\"requltType\":null,\"errorMessage\":null},\"list\":[{\"id\":6,\"cdate\":1471337988000,\"creator\":\"shli\",\"picurl\":\"/fr-here/appmovie_image/movie_20160816165948.png\",\"movieurl\":\"/fr-here/appmovie_movie/movie_20160816165948.mp4\",\"title\":\"中国乒乓球队 - 乒乒乓乓天下无双 (第九季 ： 乒乓海洋)\",\"abstract_\":\"中国乒乓球队\\r\\n简介：中国乒乓球队成立于1952年，拼搏不息，攀登不止，经历了由弱到强、持久昌盛的发展历程。中国乒乓球队包括中国女子\"},{\"id\":5,\"cdate\":1471337870000,\"creator\":\"shli\",\"picurl\":\"/fr-here/appmovie_image/movie_20160816165750.png\",\"movieurl\":\"/fr-here/appmovie_movie/movie_20160816165750.mp4\",\"title\":\"Skrillex - Purple Lamborghini\",\"abstract_\":\"《Purple Lamborghini》由美国DJSkrillex、美国说唱歌手Rick Ross共同制作完成。该单曲收录在电影《X特遣队》原声曲目中。歌曲信息美国DJSkriilex、说唱歌手Rick Ross为电影《X特遣队》献曲。\"},{\"id\":4,\"cdate\":1471337845000,\"creator\":\"shli\",\"picurl\":\"/fr-here/appmovie_image/movie_20160816165725.png\",\"movieurl\":\"/fr-here/appmovie_movie/movie_20160816165725.mp4\",\"title\":\"Charlie Puth - We Don't Talk Anymore\",\"abstract_\":\"《We Don't Talk Anymore》由Charlie Puth等人创作，邀请到美国流行歌手Selena Gomez助阵献声，将于2016年作为本张专辑的第三首单曲发行。Charlie Puth在日本的旅行中创作了这首歌的旋律，回到LA之后完成录制工作。\"},{\"id\":3,\"cdate\":1471337919000,\"creator\":\"shli\",\"picurl\":\"/fr-here/appmovie_image/movie_20160816165839.png\",\"movieurl\":\"/fr-here/appmovie_movie/movie_20160816165839.mp4\",\"title\":\"Alan Walker - Sing Me to Sleep\",\"abstract_\":\"《Sing Me To Sleep》（伴我入眠）是由Alan Walker制作、Iselin Solheim演唱的歌曲，是继《Faded》后携带全新舞曲风格的作品，收录于2016年6月3日发行的同名专辑中。《Sing Me To Sleep》的音乐标题听似在描述爱情生活的甜蜜无比实则与其有天壤之别。\"},{\"id\":2,\"cdate\":1471337717000,\"creator\":\"shli\",\"picurl\":\"/fr-here/appmovie_image/movie_20160816165517.png\",\"movieurl\":\"/fr-here/appmovie_movie/movie_20160816165517.mp4\",\"title\":\"微微一笑很倾城\",\"abstract_\":\"根据顾漫同名小说改编，都市青春偶像剧《微微一笑很倾城》，8月22日起优酷独播，微微一笑在优酷。\"},{\"id\":1,\"cdate\":1471337111000,\"creator\":\"shli\",\"picurl\":\"/fr-here/appmovie_image/movie_20160816164511.png\",\"movieurl\":\"/fr-here/appmovie_movie/movie_20160816164511.mp4\",\"title\":\"谭维维 - 无问\",\"abstract_\":\"《无问》是电影《盗墓笔记》主题曲。作词人：尹约作曲人：Julian Emery、谭维维、刘迦宁由谭维维演唱的一首歌曲。基本信息电影《盗墓笔记》主题曲 作词：尹约 作曲：Julian Emery、谭维维、刘迦宁 演唱：谭维维 发行时间：2016-07-29\"}],\"model\":null}");
        TestSubscriber<BaseResponse<Movie>> testSubscriber = new TestSubscriber<>();

        BaseResponse<Movie> baseResponse = movieModel.getMovieList(1, 10)
                .toBlocking()
                .first();


       List<Movie> movies =  baseResponse.list;
        Assert.assertEquals(movies.get(0).id, 6);
        Assert.assertEquals(movies.get(1).id, 5);

    }
}