package com.fm.fmlib.utils;

import com.fm.fmlib.dao.user;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class DataMemCacheUtil {
    private user currentUser;
    private
    private DataMemCacheUtil(){
        currentUser = new user();
    }


    public String getToken(){
        return currentUser.getToken()
    }

    public String getAccount(){
        return currentUser.getAccount();
    }
}
