package com.fr.here.ui.news.widget;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.fr.here.R;
import com.fr.here.base.BaseBackFragment;
import com.fr.here.base.OnWebViewImageListener;
import com.fr.here.ui.news.G;
import com.fr.here.util.ImgHelper;
import com.fr.here.util.ShareUtil;
import com.fr.here.widget.imageload.ImageFragment;
import com.fr.here.widget.statusview.MultipleStatusView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 新闻详情  其实是html5的载体而已
 * Created by shli on 2016-08-12.
 */
@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
public class NewsDetail extends BaseBackFragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.multiplestatusview)
    MultipleStatusView multiplestatusview;
    @Bind(R.id.web_view)
    WebView webView;

    private String url;
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null){
            url = args.getString(G.NEWS_DETAIL_URL);
            title = args.getString(G.NEWS_DETAIL_TITLE,G.NEWS_DETAIL_TITLR_DEFAULT);
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_news_detail;
    }
    @Override
    public void initView() {
        toolbar.setTitle(title);
        initToolbarNav(toolbar);
        toolbar.inflateMenu(R.menu.menu_detail);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.action_share:
                        ShareUtil.shareText(_mActivity, title + " - " + url + " ヾ (o ° ω ° O ) ノ");
                        break;
                    case R.id.action_copy_url:
                        ShareUtil.copyToClipboard(_mActivity, url, webView);
                        break;
                    case R.id.action_open_in_browser:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        break;
                    case R.id.action_refresh:
                        webView.reload();
                        break;
                }
                return true;
            }
        });

        multiplestatusview.showLoading();
        //WebView
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setAppCacheEnabled(true);


        ImgHelper.addOnclickToHtml(multiplestatusview, webView, new OnWebViewImageListener() {
            @Override
            @JavascriptInterface
            public void onImageClick(String url) {
                start(ImageFragment.newInstance(url));
            }
        });

        /*webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    multiplestatusview.setVisibility(View.GONE);
                }
            }
        });*/
        webView.loadUrl(url);
    }




    @Override
    public void initPresenter() {

    }

    public static NewsDetail newInstance(String title,String url){
        Bundle args = new Bundle();
        args.putString(G.NEWS_DETAIL_TITLE, title);
        args.putString(G.NEWS_DETAIL_URL, url);
        NewsDetail newsDetail = new NewsDetail();
        newsDetail.setArguments(args);
        return newsDetail;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null){
            webView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null){
            webView.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (webView != null){
            webView.stopLoading();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(webView !=null){
            webView.removeAllViews();
            webView.destroy();
        }
    }

}
