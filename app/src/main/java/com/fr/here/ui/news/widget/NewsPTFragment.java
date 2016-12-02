package com.fr.here.ui.news.widget;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fr.here.R;
import com.fr.here.base.BaseMainFragment;
import com.fr.here.ui.news.G;
import com.fr.here.ui.news.adapter.BannerAdapter;
import com.fr.here.ui.news.adapter.NewsPTAdapter;
import com.fr.here.ui.news.presenter.INewsPTPresenter;
import com.fr.here.ui.news.presenter.NewsPTPresenterImpl;
import com.fr.here.ui.news.view.INewsPTView;
import com.fr.here.ui.news.vo.News;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


/**
 * 图文新闻
 * shli
 * 2016/08/11
 */
public class NewsPTFragment extends BaseMainFragment implements INewsPTView {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rollPagerView)
    RollPagerView rollPagerView;
    @Bind(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    @Bind(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    //其他视图
    @Bind(R.id.error_view)
    RelativeLayout errorView;
    @Bind(R.id.empty_view)
    RelativeLayout emptyView;




    private INewsPTPresenter newsPTPresenter;
    private NewsPTAdapter adapter;

    public static NewsPTFragment newInstance() {
        return new NewsPTFragment();
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 默认不改变
//         return super.onCreateFragmentAnimation();
        // 在进入和离开时 设定无动画
        return new DefaultNoAnimator();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_news_pt;
    }

    @Override
    public void initView() {
        mToolbar.setTitle(getResources().getString(R.string.news));
        initToolbarNav(mToolbar, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerView.setAdapterWithProgress(adapter = new NewsPTAdapter(_mActivity));

        //设置加载更多  以及回调
        adapter.setMore(R.layout.custom_loading_view, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                newsPTPresenter.loadNextNewsList(0);
            }
        });
        //设置没有更多  以及点击事件
        adapter.setNoMore(R.layout.custom_nomore_item_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsPTPresenter.loadNextNewsList(0);
            }
        });
        //设置加载错误   以及点击事件
        adapter.setError(R.layout.custom_error_item_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsPTPresenter.loadNextNewsList(0);
            }
        });

        //item的点击事件
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                News news = adapter.getItem(position);
                start(NewsDetail.newInstance(news.title, G.NEWS_URL+news.id));
            }
        });

        //间接实现刷新
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setRefreshing(true);
                newsPTPresenter.refleshNewsList(0);
            }
        });

        //给两个错误页面添加点击事件
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.showProgress();
                newsPTPresenter.refleshNewsList(0);
            }
        });
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.showProgress();
                newsPTPresenter.refleshNewsList(0);
            }
        });

        rollPagerView.setHintView(new ColorPointHintView(_mActivity, getResources().getColor(R.color.colorAccent), Color.GRAY));
        //rollPagerView.setAdapter(new BannerAdapter(_mActivity,));
        rollPagerView.setPlayDelay(2000);
        //加载Banner
        newsPTPresenter.loadNewsBanner();
        //加载数据
        newsPTPresenter.refleshNewsList(0);
    }

    @Override
    public void initPresenter() {
        this.newsPTPresenter = new NewsPTPresenterImpl(this);
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
        rollPagerView.setAdapter(bannerAdapter);
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
    public void onDestroyView() {
        super.onDestroyView();
        newsPTPresenter.releaseSubscriber();
    }
}
