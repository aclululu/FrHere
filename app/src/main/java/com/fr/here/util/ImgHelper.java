package com.fr.here.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.fr.here.app.C;
import com.fr.here.base.OnWebViewImageListener;
import com.fr.here.widget.imageload.ImageFragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ImgHelper {
    /**
     * 将byte转化为图片 并保存
     * @param imgbyte
     */
    public static void toimg(byte[] imgbyte,String path) {
        FileOutputStream fos = null;
        File newFile = new File(path);
        try {
            fos = new FileOutputStream(newFile);
            fos.write(imgbyte);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if (null != fos) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * byte[]解压
     * @param imgbytecompress
     * @return
     */
    public static byte[] decompress(byte[] imgbytecompress) {
        GZIPInputStream gunzip = null ;
        ByteArrayOutputStream baos = null ;
        ByteArrayInputStream  is = null;
        byte[] newData = null ;
        try {
            baos = new ByteArrayOutputStream() ;
            is = new ByteArrayInputStream(imgbytecompress);
            gunzip = new GZIPInputStream(is);

            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer))>= 0) {
                baos.write(buffer, 0, n);
            }
            baos.flush();
            newData = baos.toByteArray() ;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                gunzip.close();
                baos.close() ;
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newData ;
    }

    /**
     * 加载本地文件,并转换为byte数组
     * @return
     */
    public static byte[] loadFile(String path) {
        File file = new File(path);
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        byte[] data = null ;

        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream((int) file.length());

            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            data = baos.toByteArray() ;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }

                baos.close() ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return data ;
    }

    /**
     * 对byte[]进行压缩
     *要压缩的数据
     * @param
     * @return 压缩后的数据
     */
    public static byte[] compress(byte[] data) {
        GZIPOutputStream gzip = null ;
        ByteArrayOutputStream baos = null ;
        byte[] newData = null ;

        try {
            baos = new ByteArrayOutputStream() ;
            gzip = new GZIPOutputStream(baos);
            gzip.write(data);
            gzip.finish();
            gzip.flush();

            newData = baos.toByteArray() ;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                gzip.close();
                baos.close() ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newData ;
    }
    public static Uri saveImage(Context context, String url, Bitmap bitmap,ImageView imageView,String tag){
        //图片保存路径
        String imgDir = C.STORAGEPATH + "img";
        //图片名称处理
        String[] fileNameArr = url.substring(url.lastIndexOf("/") + 1).split("\\.");
        String fileName = fileNameArr[0] + ".png";
        //创建文件路径
        File fileDir = new File(imgDir);
        if (!fileDir.exists()){
            fileDir.mkdir();
        }
        //创建文件
        File imageFile = new File(fileDir,fileName);
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            boolean compress = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            if (tag.equals("save")){
                if (compress){
                    Snackbar.make(imageView, "图片已经躺在你的图库里啦.. ( ＞ω＜)", Snackbar.LENGTH_SHORT).show();
                }else {
                    Snackbar.make(imageView,"图片拒绝了你的请求.. ( ＞ω＜)",Snackbar.LENGTH_SHORT).show();
                }
            }else{
                if (!compress){
                    Snackbar.make(imageView,"图片拒绝了你的请求.. ( ＞ω＜)",Snackbar.LENGTH_SHORT).show();
                }
            }
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri uri = Uri.fromFile(imageFile);
        //发送广播，通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
        return uri;
    }

    /**
     * 为图片添加点击事件
     * @param webView 当前web容器
     * @param onWebViewImageListener 点击事件的回调<br> <font color="red">一定要在方法上添加@JavascriptInterface注解 </br></font>
     */
    @SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
    public static void addOnclickToHtml(final View multiplestatusview,final WebView webView ,OnWebViewImageListener onWebViewImageListener){
        webView.addJavascriptInterface(onWebViewImageListener, "WebViewImageListener");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                addImageClickListner(webView);
                multiplestatusview.setVisibility(View.GONE);
            }
        });
    }

    // 注入js函数监听
    private static void addImageClickListner(WebView webView) {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        //onClick=\"javascript:mWebViewImageListener.onImageClick('$2')\"")
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        WebViewImageListener.onImageClick(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }
}