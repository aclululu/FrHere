package com.fr.here.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.fr.here.app.BaseApplication;
import com.fr.here.app.C;


/**
 * 公共参数配置类 统一保存 方便使用
 *
 * @author shli
 */
public class SharedPreferencesUtil {

    private static SharedPreferences mSharedPreferences = BaseApplication.getInstance().getSharedPreferences(C.SPF, Activity.MODE_PRIVATE);
    private static Editor mEditor;

    /**
     * 加载一个String数据
     *
     * @param key 目标数据的key
     * @return 目标数据, 无则返回空字符串
     */
    public static String loadString(String key) {
        return mSharedPreferences.getString(key, "");
    }
    /**
     * 加载一个String数据
     *
     * @param key 目标数据的key
     * @return 目标数据, 无则返回空字符串
     */
    public static String loadString(String key,String defaut) {
        return mSharedPreferences.getString(key, defaut);
    }

    /**
     * 加载一个int类型的数据
     *
     * @param key 目标数据的key
     * @return 目标数据, 无则返回-1
     */
    public static int loadInt(String key) {
        return mSharedPreferences.getInt(key, -1);
    }

    /**
     * 加载一个long类型的数据
     *
     * @param key 目标数据的key
     * @return 目标数据, 无则返回-1
     */
    public static long loadLong(String key) {
        return mSharedPreferences.getLong(key, -1);
    }

    /**
     * 加载一个boolean类型的数据
     *
     * @param key 目标数据的key
     * @return 目标数据, 无则返回false
     */
    public static boolean loadBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 加载一个float类型的数据
     *
     * @param key 目标数据的key
     * @return 目标数据, 无则返回-1
     */
    public static float loadFloat(String key) {
        return mSharedPreferences.getFloat(key, -1);
    }

    /**
     * 以k-v形式保存一个String数据
     */
    public static void saveString(String key, String value) {
        if (mEditor == null)
            mEditor = mSharedPreferences.edit();

        mEditor.putString(key, value);
        mEditor.commit();
    }

    /**
     * 以k-v保存一个int类型的数据
     */
    public static void saveInt(String key, int value) {
        if (mEditor == null)
            mEditor = mSharedPreferences.edit();

        mEditor.putInt(key, value);
        mEditor.commit();
    }

    /**
     * 以k-v保存一个long类型的数据
     */
    public static void saveLong(String key, long value) {
        if (mEditor == null)
            mEditor = mSharedPreferences.edit();

        mEditor.putLong(key, value);
        mEditor.commit();
    }

    /**
     * 以k-v保存一个boolean类型的数据
     */
    public static void saveBoolean(String key, boolean value) {
        if (mEditor == null)
            mEditor = mSharedPreferences.edit();

        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    /**
     * 以k-v保存一个float类型的数据
     */
    public static void saveFloat(String key, float value) {
        if (mEditor == null)
            mEditor = mSharedPreferences.edit();

        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    /**
     * 清空数据
     */
    public static void clear() {
        if (mEditor == null)
            mEditor = mSharedPreferences.edit();

        mEditor.clear();
        mEditor.commit();
    }

}
