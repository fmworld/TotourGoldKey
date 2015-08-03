package com.fm.fmlib;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.fm.fmlib.tour.Totour0888;
import com.fm.fmlib.tour.bean.UserInfo;
import com.fm.fmlib.utils.BackgroundExecutor;
import com.fm.fmlib.utils.provider.BackgroundExecutorProvider;
import com.fm.fmlib.utils.provider.Networkprovider;
import com.squareup.otto.Bus;

/**
 * Created by zhou feng'an on 2015/7/29.
 */
public class FmApplication extends Application {
    private BackgroundExecutor mExecutor;
    private Totour0888 mTotour;
    private static FmApplication instance;
    private UserInfo tempUserInfo;
    private Bus mBus;
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
        initTestData();
        initBus();
    }

    private void initBus(){
        mBus = new Bus();
    }
    private void initTestData() {
        tempUserInfo = new UserInfo();
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

    public UserInfo getTempUserInfo() {
        return tempUserInfo;
    }

    public String getToken() {
        return tempUserInfo.token;
    }

    public void setToken(String token) {
        tempUserInfo.token = token;
    }

    public Bus getmBus() {
        return mBus;
    }
}
