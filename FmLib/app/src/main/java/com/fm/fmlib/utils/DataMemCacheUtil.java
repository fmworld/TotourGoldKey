package com.fm.fmlib.utils;

import android.content.Context;

import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.user;
import com.fm.fmlib.utils.provider.DaoProvider;
import com.fm.fmlib.utils.provider.UserDaoProvider;
import com.fm.fmlib.utils.provider.UserInterface;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class DataMemCacheUtil implements UserInterface {
    private DaoSession mSession;
    private UserDaoProvider userDao;

    public DataMemCacheUtil(Context context) {
        mSession = DaoProvider.provideDaoSession(context);
        userDao = new UserDaoProvider(mSession);
    }


    public String getToken() {
        return userDao.getToken();
    }

    @Override
    public void setToken(String token) {
        userDao.setToken(token);
    }

    public String getAccount() {
        return userDao.getAccount();
    }

    @Override
    public void setAccount(String account) {
        userDao.setAccount(account);
    }

    @Override
    public String getPassword() {
        return userDao.getPassword();
    }

    @Override
    public void setPassword(String password) {
        userDao.setPassword(password);
    }

    @Override
    public boolean isLogined() {
        return userDao.isLogined();
    }

    @Override
    public void setLogined(boolean islogined) {
        userDao.setLogined(islogined);
    }

    @Override
    public void setState(String state) {
        userDao.setState(state);
    }

    @Override
    public String getState() {
        return userDao.getState();
    }

    @Override
    public boolean saveUser() {
        return userDao.saveUser();
    }
}
