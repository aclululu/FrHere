package com.fr.here.base;

/**
 * 基础presenter接口
 * Created by shli on 2016-08-30.
 */
public interface IBasePresenter {
    /**
     * 加载下一页数据
     */
    void next();

    /**
     * 刷新
     */
    void reflesh();

    /**
     * 释放资源 一般这里为“解除订阅关系”
     */
    void release();
}
