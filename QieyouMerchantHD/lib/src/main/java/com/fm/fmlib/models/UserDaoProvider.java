package com.fm.fmlib.models;

import android.content.Context;

import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.User;
import com.fm.fmlib.dao.UserDao;
import com.fm.fmlib.models.component.DaggerDaoSessionComponent;
import com.fm.fmlib.models.component.DaoSessionComponent;
import com.fm.fmlib.tour.bean.UserInfo;
import com.fm.fmlib.models.provider.DaoSessionProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
@Singleton
public class UserDaoProvider implements UserInterface{
    @Inject
    DaoSession mDaoSession;
    private User mCurrentUser;
    private UserDao mUserDao;

    public UserDaoProvider(Context mContext){
        DaoSessionComponent sessionComponent = DaggerDaoSessionComponent.builder()
                .daoSessionProvider(new DaoSessionProvider(mContext)).build();
        mDaoSession = sessionComponent.provideSession();
        mUserDao = mDaoSession.getUserDao();
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
        mCurrentUser.setIslogin(copy.getIslogin());
    }

    private void initUserInfo(UserInfo info) {
        mCurrentUser.setInnerHead(info.inner_head);
        mCurrentUser.setInnerName(info.inner_name);
        mCurrentUser.setUserMobile(info.user_mobile);
    }

    private User getLoginedUser(){
        QueryBuilder<User> queryBuilder = mUserDao.queryBuilder();
        queryBuilder.where(UserDao.Properties.Islogin.eq(Boolean.TRUE));
        if(0 < queryBuilder.list().size()){
            return queryBuilder.list().get(0);
        }
        User user = new User();
        user.setIslogin(Boolean.FALSE);
        return user;
    }

    public User getmCurrentUser(){
        return  mCurrentUser;
    }
}
