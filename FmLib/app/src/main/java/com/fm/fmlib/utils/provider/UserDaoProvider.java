package com.fm.fmlib.utils.provider;

import android.content.Context;

import com.fm.fmlib.dao.DaoMaster;
import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.user;
import com.fm.fmlib.dao.userDao;

import de.greenrobot.dao.AbstractDao;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class UserDaoProvider {
    private user mCurrentUser;
    private userDao mUserDao;
    public UserDaoProvider(Context context){
        mUserDao = DaoProvider.createUserDao(context.getApplicationContext());
        mUserDao.queryBuilder().
    }
}
