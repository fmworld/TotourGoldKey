package com.fm.fmlib;

import android.app.Application;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.dao.Inn;
import com.fm.fmlib.dao.ProductTagDao;
import com.fm.fmlib.dao.User;
import com.fm.fmlib.tour.Totour0888;
import com.fm.fmlib.tour.bean.UserInfo;
import com.fm.fmlib.utils.BackgroundExecutor;
import com.fm.fmlib.utils.DataCleanManager;
import com.fm.fmlib.utils.DataMemCacheUtil;
import com.fm.fmlib.utils.provider.BackgroundExecutorProvider;
import com.fm.fmlib.utils.provider.CategoryDaoProvider;
import com.fm.fmlib.utils.provider.LaunchProfileProvider;
import com.fm.fmlib.utils.provider.LocalDaoProvider;
import com.fm.fmlib.utils.provider.Networkprovider;
import com.fm.fmlib.utils.provider.ProductTagDaoProvider;
import com.fm.fmlib.utils.provider.PropertyDaoProvider;
import com.squareup.otto.Bus;

import java.io.File;

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
        initFrescoConfig();

        mTotour = Networkprovider.providerTotour0888();
    }

    private void initFrescoConfig(){
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder()
                .setBaseDirectoryName(DataCleanManager.FRESCO_CACHE)
                .setBaseDirectoryPathSupplier(new Supplier() {
                    public File get() {
                        return getCacheDir();
                    }
                })
                .build();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this,config);

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
        if (data instanceof User) {
            dataMemCache.getUserDao().saveWithUser((User)data);
        }else if(data instanceof Inn){
            dataMemCache.getInnDao().saveWithInn((Inn)data);
        }else if(data instanceof UserInfo){
            dataMemCache.getUserDao().saveWithUserInfo((UserInfo)data);
        }
    }

    public Bus getmBus() {
        return mBus;
    }

    public User getDaoUser(){
        return dataMemCache.getUserDao().getmCurrentUser();
    }

    public Inn getDaoInn(){
        return dataMemCache.getInnDao().getmCurrentInn();
    }

    public PropertyDaoProvider getDaoProperty(){
        return dataMemCache.getPropertyDao();
    }

    public ProductTagDaoProvider getDaoProductTag(){
        return dataMemCache.getPTagsDao();
    }

    public LocalDaoProvider getDaoLocal(){
        return dataMemCache.getLocalDao();
    }

    public CategoryDaoProvider getDaoCategory(){
        return dataMemCache.getCategoryDao();
    }

    public LaunchProfileProvider getDaoLaunProfile(){
        return dataMemCache.getLaunchProfileDao();
    }


}
