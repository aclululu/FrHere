package com.fr.here.ui.news.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.fr.here.R;
import com.fr.here.base.BaseMainFragment;
import com.fr.here.ui.news.G;
import com.fr.here.ui.news.adapter.BannerAdapter;
import com.fr.here.ui.news.adapter.NewsPictureAdapter;
import com.fr.here.ui.news.adapter.NewsTextAdapter;
import com.fr.here.ui.news.presenter.INewsPTPresenter;
import com.fr.here.ui.news.presenter.NewsPicPresenterImpl;
import com.fr.here.ui.news.view.INewsPTView;
import com.fr.here.ui.news.vo.News;
import com.github.clans.fab.FloatingActionButton;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 图片新闻
 * Created by shli on 2016-08-12.
 */
public class NewsPicFragment extends BaseMainFragment implements INewsPTView,SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @Bind(R.id.top)
    FloatingActionButton top;

    //其他视图
    @Bind(R.id.error_view)
    RelativeLayout errorView;
    @Bind(R.id.empty_view)
    RelativeLayout emptyView;

    private NewsPicPresenterImpl newsPicPresenter;
    private int flag = 1;
    private RecyclerArrayAdapter<News> adapter;

    public static NewsPicFragment newInstance(){return new NewsPicFragment();}

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_single_recyclerview;
    }

    @Override
    public void initView() {
        mToolbar.setTitle(getResources().getString(R.string.news_pic));
        initToolbarNav(mToolbar, true);
        mToolbar.inflateMenu(R.menu.menu_pic_news);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_pic_news:
                        if (flag == 1) {
                            onRefresh();
                        } else {
                            flag = 1;
                            initPicOrTextAdapter();
                            onRefresh();
                        }
                        break;
                    case R.id.action_text_news:
                        if (flag == -1) {
                            onRefresh();
                        } else {
                            flag = -1;
                            initPicOrTextAdapter();
                            onRefresh();
                        }
                        break;
                }
                return true;
            }
        });
        initPicOrTextAdapter();
        dealWithAdapter();
        recyclerView.setRefreshListener(this);
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 5) {
                    top.hide(true);
                } else if (dy < -5) {
                    top.show(true);
                }
            }
        });
        newsPicPresenter.refleshNewsList(flag);
    }

    private void initPicOrTextAdapter(){
        if(flag==1){
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapterWithProgress(adapter = new NewsPictureAdapter(_mActivity));
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
            recyclerView.setAdapterWithProgress(adapter = new NewsTextAdapter(_mActivity));
        }
        dealWithAdapter();
    }

    private void dealWithAdapter(){
        //设置加载更多  以及回调
        adapter.setMore(R.layout.custom_loading_view, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                newsPicPresenter.loadNextNewsList(flag);
            }
        });
        //设置没有更多  以及点击事件
        adapter.setNoMore(R.layout.custom_nomore_item_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsPicPresenter.loadNextNewsList(flag);
            }
        });
        //设置加载错误   以及点击事件
        adapter.setError(R.layout.custom_error_item_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsPicPresenter.loadNextNewsList(flag);
            }
        });

        //item的点击事件
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                News news = adapter.getItem(position);
                start(NewsDetail.newInstance(news.title, G.NEWS_URL + news.id));
            }
        });

        //给两个错误页面添加点击事件
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.showProgress();
                newsPicPresenter.refleshNewsList(flag);
            }
        });
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.showProgress();
                newsPicPresenter.refleshNewsList(flag);
            }
        });
    }

    @Override
    public void initPresenter() {
        newsPicPresenter = new NewsPicPresenterImpl(this);
    }

    @Override
    public void setNewsPTBanner(List<News> newsList) {
        if(newsList==null) newsList = new ArrayList<News>();
        BannerAdapter bannerAdapter = new BannerAdapter(_mActivity,newsList);
        bannerAdapter.setOnItemClickListener(new BannerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                News news = (News) view.getTag();
                start(NewsDetail.newInstance(news.title, G.NEWS_URL + news.id));
            }
        });
        //rollPagerView.setAdapter(bannerAdapter);
    }

    @Override
    public void setNewsPTListRefresh(List<News> newsList) {
        adapter.clear();
        if(newsList==null){
            recyclerView.showError();
        }else if(newsList.size()==0){
            recyclerView.showEmpty();
        }else{
            adapter.addAll(newsList);
        }
    }

    @Override
    public void setNewsPTListNext(List<News> newsList) {
        if(newsList==null){
            adapter.pauseMore();
        }else{
            adapter.addAll(newsList);
        }
    }

    @Override
    public void onRefresh() {
        recyclerView.setRefreshing(true);
        newsPicPresenter.refleshNewsList(flag);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        newsPicPresenter.releaseSubscriber();
    }
}
