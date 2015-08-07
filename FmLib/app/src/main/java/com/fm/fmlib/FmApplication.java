package com.fm.fmlib;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.fm.fmlib.dao.user;
import com.fm.fmlib.tour.Totour0888;
import com.fm.fmlib.tour.bean.UserInfo;
import com.fm.fmlib.utils.BackgroundExecutor;
import com.fm.fmlib.utils.DataMemCacheUtil;
import com.fm.fmlib.utils.provider.BackgroundExecutorProvider;
import com.fm.fmlib.utils.provider.Networkprovider;
import com.squareup.otto.Bus;

/**
 * Created by zhou feng'an on 2015/7/29.
 */
public class FmApplication extends Application {
    private BackgroundExecutor mExecutor;
    private Totour0888 mTotour;
    private DataMemCacheUtil dataMemCache;

    private Bus mBus;

    private static FmApplication instance;
    public static FmApplication instance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initBackroundExecutor();
        initTotour();
        intiFresco();
        initBus();
        initDataCache();
    }

    private void initDataCache(){
        dataMemCache = new DataMemCacheUtil(this);
    }

    private void initBus(){
        mBus = new Bus();
    }

    private void intiFresco() {
        Fresco.initialize(this);
    }

    private void initBackroundExecutor() {
        mExecutor = BackgroundExecutorProvider.providerBackgroundExecutor();
    }

    private void initTotour() {
        mTotour = Networkprovider.providerTotour0888();
    }

    public BackgroundExecutor getmExecutor() {
        return mExecutor;
    }

    public Totour0888 getmTotour() {
        return mTotour;
    }


    public String getToken() {
        return dataMemCache.getToken();
    }

    public void setToken(String token) {
        dataMemCache.setToken(token);
    }

    public String getAccount(){
        return dataMemCache.getAccount();
    }

    public String getPassword(){
        return dataMemCache.getPassword();
    }

    public void updateUser(user newTemp){
        dataMemCache.setAccount(newTemp.getAccount());
        dataMemCache.setToken(newTemp.getToken());
        dataMemCache.setLogined(newTemp.getIslogin());
        dataMemCache.setPassword(newTemp.getPassword());
        dataMemCache.setState(newTemp.getState());
        dataMemCache.saveUser();
    }

    public Bus getmBus() {
        return mBus;
    }
}
