package com.fr.here.app;

import android.annotation.SuppressLint;

import com.fr.here.util.SdCardUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 全局变量存储类
 *
 * @author shli
 */
public class C {

    /**
     * 应用程序名
     */
    public static final String APPNAME = "frhere";
    public static final String APP_NAME = "你好，旧时光";
    /**
     * 分页容量
     */
    public static final int PAGE_SIZE = 10;

    /**
     * SharedPreferences文件名称
     */
    public static final String SPF = "spf";

    /**
     * 字段为空时
     */
    public static final String NO_DATA_VIEW = "无";


    /**
     * 文件根目录
     */
    public static final String STORAGEPATH = SdCardUtil.getNormalSDCardPath() + "/" + APPNAME + "/";

    /**
     * 自动更新文件下载路径
     */
    public static final String UPDATE_APP_SAVE_PATH = STORAGEPATH + APPNAME + ".apk";
    /**
     * 系统图片
     */
    public static final String APPIMAGE = STORAGEPATH + "img/";
    /**
     * 录音文件保存路径
     */
    public static final String APPRECORD = STORAGEPATH + "record/";

    /**
     * 调用拍照request code
     */
    public static final int ACTION_CAMERA = 0x01;
    /**
     * 调用相册request code
     */
    public static final int ACTION_ALBUM = 0x02;
    /**
     * 打开扫码request code
     */
    public static final int ACTION_BARCODE = 0x03;

    /**
     * 打开录音request code
     */
    public static final int ACTION_RECORDER = 0x04;

    /**
     * 打开通讯录request code
     */
    public static final int ACTION_ADDRESSLIST = 0x05;


    @SuppressLint("SimpleDateFormat")
    public static String getPhoneCurrentTime() {
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
        return date.format(Calendar.getInstance().getTime());
    }

    /**
     * BlankActivity里面承载的业务标示
     */
    //参数
    public static final String ARGS1 = "args1";
    //业务
    public static final String ARGS_BLANK = "args_blank";
    //业务分类

    /**
     * 全局提示
     */
    public static final String IS_EXIT_TITLE = "退出";
    public static final String IS_EXIT_INFO = "确定要退出应用吗？";



}
