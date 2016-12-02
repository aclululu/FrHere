package com.fr.here.app;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.fr.here.BuildConfig;
import com.fr.here.net.RetrofitSingleton;
import com.fr.here.util.CrashHandler;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Locale;
import java.util.UUID;

/**
 * 自定义应用入口
 *
 * @author shli
 */
public class BaseApplication extends Application {
    private static BaseApplication mInstance;

    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        // 初始化 retrofit
        RetrofitSingleton.init();
        //crash本地处理
        CrashHandler.init(new CrashHandler(getApplicationContext()));
        //crash云处理   管理账号：945152947   appid、：900042063
        if (!BuildConfig.DEBUG)
        CrashReport.initCrashReport(getApplicationContext(), "900042063", false);

        //LeakCanary.install(this);
        //initImageLoader();
        initScreenSize();
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = mInstance.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mInstance.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前系统语言
     *
     * @return 当前系统语言
     */
    public static String getLanguage() {
        Locale locale = mInstance.getResources().getConfiguration().locale;
        String language = locale.getDefault().toString();
        return language;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }
    /**
     * 获取App唯一标识
     *
     * @return
     */
    public String getAppId() {
        return UUID.randomUUID().toString();
    }



    /**
     * killed self
     */
    public void exitApp() {
        // 杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);

    }

    public void showToast(int resId) {
        showToast(getString(resId));
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
