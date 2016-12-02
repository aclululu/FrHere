package com.fr.here.base;

/**
 * 公共View接口
 *
 * @author Ht
 */
public interface IBaseView {


    /**
     * 根据资源文件id弹出toast
     *
     * @param resId 资源文件id
     */
    void showToast(int resId);

    /**
     * 根据字符串弹出toast
     *
     * @param msg 提示内容
     */
    void showToast(String msg);

}
