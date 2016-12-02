package com.fr.here.base;
import java.util.List;

/**
 * 服务器返回数据格式
 * @param <T>
 */
public class BaseResponse<T> {
    public BaseResponseResult result =  new BaseResponseResult();
    public List<T> list;
    public T model;
}