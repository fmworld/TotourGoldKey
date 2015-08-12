package com.fm.fmlib.utils.provider;

import android.content.Context;

import com.fm.fmlib.dao.DaoMaster;
import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.User;
import com.fm.fmlib.dao.UserDao;
import com.fm.fmlib.tour.bean.UserInfo;
import com.fm.fmlib.tour.entity.UserInfoEntity;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class UserDaoProvider implements UserInterface{
    private User mCurrentUser;
    private UserDao mUserDao;
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
        mCurrentUser.setPassword(password);
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
    public boolean saveUser() {
        return mUserDao.insertOrReplace(mCurrentUser)>0;
    }

    @Override
    public boolean saveWithUser(User copy) {
        initUser(copy);
        return saveUser();
    }

    @Override
    public boolean saveWithUserInfo(UserInfo info) {
        initUserInfo(info);
        return saveUser();
    }

    @Override
    public void setState(String state) {
        mCurrentUser.setState(state);
    }

    @Override
    public String getState() {
        return mCurrentUser.getState();
    }

    private void initUser(User copy) {
        mCurrentUser.setAccount(copy.getAccount());
        mCurrentUser.setPassword(copy.getPassword());
        mCurrentUser.setState(copy.getState());
        mCurrentUser.setToken(copy.getToken());
        mCurrentUser.setRole(copy.getRole());
    }

    private void initUserInfo(UserInfo info) {
        mCurrentUser.setInnerHead(info.inner_head);
        mCurrentUser.setInnerName(info.inner_name);
        mCurrentUser.setUserMobile(info.user_mobile);
    }

    private User getLoginedUser(){
        QueryBuilder<User> queryBuilder = mUserDao.queryBuilder();
        queryBuilder.where(UserDao.Properties.Islogin.eq(true));
        if(0 < queryBuilder.list().size()){
            return queryBuilder.list().get(0);
        }
        return new User();
    }

    public User getmCurrentUser(){
        return  mCurrentUser;
    }
}
