package com.fm.fmlib;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.dao.inn;
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
public class TourApplication extends Application {
    private BackgroundExecutor mExecutor;
    private Totour0888 mTotour;
    private DataMemCacheUtil dataMemCache;
    private MainController mMainController;
    private Bus mBus;

    private static TourApplication instance;

    public TourApplication() {
    }

    public static TourApplication testGenerate() {
        if (null == instance) {
            instance = new TourApplication();
            instance.initConfig();
        }

        return instance;
    }

    public static TourApplication instance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initConfig();
//        initBackroundExecutor();
//        initTotour();
//        intiFresco();
//        initBus();
//        initDataCache();
//        initController();
    }


    private void initConfig() {
        dataMemCache = new DataMemCacheUtil(this);
        mMainController = new MainController();
        mBus = new Bus();
        Fresco.initialize(this);
        mTotour = Networkprovider.providerTotour0888();
    }

    public MainController getmMainController() {
        return mMainController;
    }

    private void initController() {
        mMainController = new MainController();

    }

    private void initDataCache() {
        dataMemCache = new DataMemCacheUtil(this);
    }

    private void initBus() {
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
        return dataMemCache.getUserDao().getToken();
    }

    public void setToken(String token) {
        dataMemCache.getUserDao().setToken(token);
    }

    public String getAccount() {
        return dataMemCache.getUserDao().getAccount();
    }

    public String getPassword() {
        return dataMemCache.getUserDao().getPassword();
    }


    public <T> void updateData(T data) {
        if (data instanceof user) {
            dataMemCache.getUserDao().saveWithUser((user)data);
        }else if(data instanceof inn){
            dataMemCache.getInnDao().saveWithInn((inn) data);
        }else if(data instanceof UserInfo){
            dataMemCache.getUserDao().saveWithUserInfo((UserInfo)data);
        }
    }

    public Bus getmBus() {
        return mBus;
    }

    public user getDaoUser(){
        return dataMemCache.getUserDao().getmCurrentUser();
    }

    public inn getDaoInn(){
        return dataMemCache.getInnDao().getmCurrentInn();
    }

}
