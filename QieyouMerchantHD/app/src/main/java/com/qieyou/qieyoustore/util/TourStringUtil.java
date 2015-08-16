package com.qieyou.qieyoustore.util;

/**
 * Created by zhoufeng'an on 2015/8/13.
 */
public class TourStringUtil {
    public static boolean isNULLorEmpty(String str){
        if(null == str || "".equals(str)){
            return true;
        }
        return false;
    }
}
