package com.fm.fmlib.utils.provider;

import android.content.Context;

import com.fm.fmlib.dao.DaoMaster;
import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.user;
import com.fm.fmlib.dao.userDao;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class UserDaoProvider implements UserInterface{
    private user mCurrentUser;
    private userDao mUserDao;
    public UserDaoProvider(DaoSession session){
        mUserDao = session.getUserDao();
        mCurrentUser = getLoginedUser();
    }

    @Override
    public String getToken() {
        return mCurrentUser.getToken();
    }

    public void setToken(String token){
        mCurrentUser.setToken(token);
    }
    @Override
    public String getAccount() {
        return mCurrentUser.getAccount();
    }

    public void setAccount(String account){
        mCurrentUser.setAccount(account);
    }

    @Override
    public String getPassword() {
        return mCurrentUser.getPassword();
    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public boolean isLogined() {
        return mCurrentUser.getIslogin();
    }

    @Override
    public void setLogined(boolean islogined) {
        mCurrentUser.setIslogin(islogined);
    }

    @Override
    public void setState(String state) {
        mCurrentUser.setState(state);
    }

    @Override
    public String getState() {
        return null;
    }

    @Override
    public boolean saveUser() {
        return mUserDao.insertOrReplace(mCurrentUser)>0?true:false;
    }

    private user getLoginedUser(){
        QueryBuilder<user> queryBuilder = mUserDao.queryBuilder();
        queryBuilder.where(userDao.Properties.Islogin.eq(true));
        if(0 < queryBuilder.list().size()){
            return queryBuilder.list().get(0);
        }
        return new user();
    }
}
