package com.fr.here.base;

import android.webkit.JavascriptInterface;

/**
 * 监听webview上的图片
 * Created by Administrator on 2016-08-12.
 */
public interface OnWebViewImageListener {

    /**
     * 点击webview上的图片，传入该缩略图的大图Url
     * @param bigImageUrl
     */
    @JavascriptInterface
    void onImageClick(String bigImageUrl);

}

