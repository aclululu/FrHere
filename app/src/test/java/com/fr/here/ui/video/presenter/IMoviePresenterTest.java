package com.fr.here.ui.video.presenter;

import com.fr.here.RxUnitTestTools;
import com.fr.here.base.BaseResponse;
import com.fr.here.ui.video.model.IMovieModel;
import com.fr.here.ui.video.view.IMovieView;
import com.fr.here.ui.video.vo.Movie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 为Presenter层编写单元测试
 * Created by shli on 2016-08-19.
 */
public class IMoviePresenterTest {

    IMovieView movieView;
    IMoviePresenter moviePresenter;
    IMovieModel movieModel;

    @Before
    public void setUp() throws Exception {
        RxUnitTestTools.openRxTools();
        movieModel =  mock(IMovieModel.class);
        movieView =  mock(IMovieView.class);

        moviePresenter = new MoviePresenterImpl(movieModel,movieView);
    }

    @Test
    public void testRefleshList() throws Exception {
        Movie movie = new Movie(1,new Date(),"aclululu","aclululululu","ab","ac","shli");
        BaseResponse<Movie> baseResponse = new BaseResponse<Movie>();
        List<Movie> list = new ArrayList<>();
        list.add(movie);
        baseResponse.list =list;
        when(movieModel.getMovieList(anyInt())).thenReturn(Observable.just(baseResponse));
        moviePresenter.refleshList();
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);

        verify(movieModel).getMovieList(1);
        verify(movieView).setMovieListRefresh(captor.capture());

        List<Movie> result = captor.getValue(); // 捕获的User

        Assert.assertEquals(result.get(0).id, 1);
    }
}