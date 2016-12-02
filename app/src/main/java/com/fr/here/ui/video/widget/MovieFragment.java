package com.fr.here.ui.video.widget;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import com.fr.here.R;
import com.fr.here.base.BaseMainFragment;
import com.fr.here.ui.video.adapter.MovieAdapter;
import com.fr.here.ui.video.presenter.IMoviePresenter;
import com.fr.here.ui.video.presenter.MoviePresenterImpl;
import com.fr.here.ui.video.view.IMovieView;
import com.fr.here.ui.video.vo.Movie;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.List;
import butterknife.Bind;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 视频
 * <br/>
 * <font color="green">实现建议：
 * <br/>在列表界面添加一个专门准备播放小视频的窗口，在需要播放的时候new出播放器放进去并设置可见。在需要隐藏的时候隐藏并且执行相关操作
 * </font><br/>
 * Created by shli on 2016-08-18.
 */
public class MovieFragment extends BaseMainFragment implements IMovieView ,SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerView)
    EasyRecyclerView recyclerView;

    //其他视图
    @Bind(R.id.error_view)
    RelativeLayout errorView;
    @Bind(R.id.empty_view)
    RelativeLayout emptyView;

    private RecyclerArrayAdapter<Movie> adapter;
    private IMoviePresenter presenter;

    public static MovieFragment newInstance(){return new MovieFragment();}
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_single_recyclerview;
    }

    @Override
    public void initView() {
        mToolbar.setTitle(getResources().getString(R.string.video));
        initToolbarNav(mToolbar, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerView.setAdapterWithProgress(adapter = new MovieAdapter(_mActivity));
        //设置加载更多  以及回调
        adapter.setMore(R.layout.custom_loading_view, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadNextList();
            }
        });
        //设置没有更多  以及点击事件
        adapter.setNoMore(R.layout.custom_nomore_item_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadNextList();
            }
        });
        //设置加载错误   以及点击事件
        adapter.setError(R.layout.custom_error_item_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadNextList();
            }
        });

        //item的点击事件
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //News news = adapter.getItem(position);
                //start(NewsDetail.newInstance(news.title, G.NEWS_URL + news.id));
            }
        });

        //给两个错误页面添加点击事件
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.showProgress();
                presenter.refleshList();
            }
        });
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.showProgress();
                presenter.refleshList();
            }
        });
        recyclerView.setRefreshListener(this);

        /**
         * 添加播放视频划出屏幕时的监听事件
         */
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                JCVideoPlayerStandard videoplayer = (JCVideoPlayerStandard) view.getTag();
                //PLog.e("item划进屏幕");
                //视频正在播放 这时候需要小窗口
                if(videoplayer!=null&&(videoplayer.currentScreen == JCVideoPlayerStandard.SCREEN_WINDOW_TINY)){
                   // videoplayer.backPress();
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
               // PLog.e("item划出屏幕");
                JCVideoPlayerStandard videoplayer = (JCVideoPlayerStandard) view.getTag();
                //视频正在播放 这时候需要小窗口
                if(videoplayer!=null&&(videoplayer.currentState == JCVideoPlayerStandard.CURRENT_STATE_PLAYING)){
                    videoplayer.startWindowTiny();
                }
            }
        });


        presenter.refleshList();
    }

    @Override
    public void initPresenter() {
        presenter = new MoviePresenterImpl(this);
    }

    @Override
    public void setMovieListRefresh(List<Movie> movieList) {
        adapter.clear();
        if(movieList==null){
            recyclerView.showError();
        }else if(movieList.size()==0){
            recyclerView.showEmpty();
        }else{
            adapter.addAll(movieList);
        }
    }

    @Override
    public void setMovieListNext(List<Movie> movieList) {
        if(movieList==null){
            adapter.pauseMore();
        }else{
            adapter.addAll(movieList);
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (JCVideoPlayer.backPress()) {
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }


    @Override
    public void onRefresh() {
        recyclerView.setRefreshing(true);
        presenter.refleshList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.releaseSubscriber();
    }
}
