package com.fm.fmlib.models.component;

import com.fm.fmlib.models.CategoryDaoProvider;
import com.fm.fmlib.models.InnDaoProvider;
import com.fm.fmlib.models.LaunchProfileProvider;
import com.fm.fmlib.models.LocalDaoProvider;
import com.fm.fmlib.models.ProductTagDaoProvider;
import com.fm.fmlib.models.PropertyDaoProvider;
import com.fm.fmlib.models.UserDaoProvider;
import com.fm.fmlib.models.provider.DataCacheProvider;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zhoufeng'an on 2015/9/14.
 */
@Component(modules = DataCacheProvider.class)
@Singleton
public interface DataCacheComponent {
    UserDaoProvider provideUserDaoProvider();

    InnDaoProvider provideInnDaoProvider();

    CategoryDaoProvider provideCategoryDaoProvider();

    LaunchProfileProvider provideLaunchProfileProvider();

    LocalDaoProvider provideLocalDaoProvider();

    ProductTagDaoProvider provideProductTagDaoProvider();

    PropertyDaoProvider providePropertyDaoProvider();
}
