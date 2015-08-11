package com.fm.fmlib.utils;

import android.content.Context;

import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.user;
import com.fm.fmlib.utils.provider.DaoProvider;
import com.fm.fmlib.utils.provider.InnDaoProvider;
import com.fm.fmlib.utils.provider.UserDaoProvider;
import com.fm.fmlib.utils.provider.UserInterface;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class DataMemCacheUtil{
    private DaoSession mSession;
    private UserDaoProvider userDao;
    private InnDaoProvider innDao;

    public DataMemCacheUtil(Context context) {
        mSession = DaoProvider.provideDaoSession(context);
        userDao = new UserDaoProvider(mSession);
        innDao = new InnDaoProvider(mSession, userDao.getAccount());
    }

    public UserDaoProvider getUserDao() {
        return userDao;
    }

    public InnDaoProvider getInnDao(){
        return innDao;
    }
}
