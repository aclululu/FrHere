package com.fr.here.base;


import com.fr.here.util.MyStringUtils;

/**
 * 报错信息
 */
public class BaseResponseResult {
    public String requltType;
    public String errorMessage;//提示信息

    public boolean isOK(){
        return MyStringUtils.isEmpty(errorMessage);
    }
}