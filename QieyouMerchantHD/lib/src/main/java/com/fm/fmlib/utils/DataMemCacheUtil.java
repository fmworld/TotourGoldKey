package com.fm.fmlib.utils;

import android.content.Context;

import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.models.CategoryDaoProvider;
import com.fm.fmlib.models.DaoProvider;
import com.fm.fmlib.models.InnDaoProvider;
import com.fm.fmlib.models.LaunchProfileProvider;
import com.fm.fmlib.models.LocalDaoProvider;
import com.fm.fmlib.models.ProductTagDaoProvider;
import com.fm.fmlib.models.PropertyDaoProvider;
import com.fm.fmlib.models.UserDaoProvider;
import com.fm.fmlib.models.component.DaggerDataCacheComponent;
import com.fm.fmlib.models.component.DataCacheComponent;
import com.fm.fmlib.models.provider.DataCacheProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
@Singleton
public class DataMemCacheUtil {
    DaoSession mSession;
    @Inject
    UserDaoProvider userDao;
    @Inject
    InnDaoProvider innDao;

    PropertyDaoProvider propertyDao;

    ProductTagDaoProvider tagsDao;

    CategoryDaoProvider categoryDao;

    LocalDaoProvider localDao;

    LaunchProfileProvider launchProfileDao;

    public DataMemCacheUtil(Context context) {
        DataCacheComponent cacheComponent = DaggerDataCacheComponent.builder()
                .dataCacheProvider(new DataCacheProvider(context)).build();
        mSession = DaoProvider.provideDaoSession(context);
        userDao = cacheComponent.provideUserDaoProvider();
        innDao = cacheComponent.provideInnDaoProvider();
        innDao.initInnData(userDao.getAccount());

        propertyDao = cacheComponent.providePropertyDaoProvider();
        tagsDao = cacheComponent.provideProductTagDaoProvider();
        categoryDao = cacheComponent.provideCategoryDaoProvider();
        localDao = cacheComponent.provideLocalDaoProvider();
        launchProfileDao = cacheComponent.provideLaunchProfileProvider();
    }

    public UserDaoProvider getUserDao() {
        return userDao;
    }

    public InnDaoProvider getInnDao() {
        return innDao;
    }

    public PropertyDaoProvider getPropertyDao() {
        return propertyDao;
    }

    public ProductTagDaoProvider getPTagsDao() {
        return tagsDao;
    }

    public LocalDaoProvider getLocalDao() {
        return localDao;
    }

    public CategoryDaoProvider getCategoryDao() {
        return categoryDao;
    }

    public LaunchProfileProvider getLaunchProfileDao() {
        return launchProfileDao;
    }
}
