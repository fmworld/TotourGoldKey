package com.fm.fmlib.models.provider;

import android.content.Context;

import com.fm.fmlib.models.CategoryDaoProvider;
import com.fm.fmlib.models.InnDaoProvider;
import com.fm.fmlib.models.LaunchProfileProvider;
import com.fm.fmlib.models.LocalDaoProvider;
import com.fm.fmlib.models.ProductTagDaoProvider;
import com.fm.fmlib.models.PropertyDaoProvider;
import com.fm.fmlib.models.UserDaoProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhoufeng'an on 2015/9/14.
 */
@Module
public class DataCacheProvider {
    private Context context;

    public DataCacheProvider(Context context) {
        this.context = context;
    }

    @Provides
    UserDaoProvider provideUserDaoProvider() {
        return new UserDaoProvider(context);
    }

    @Provides
    InnDaoProvider provideInnDaoProvider() {
        return new InnDaoProvider(context);
    }

    @Provides
    CategoryDaoProvider provideCategoryDaoProvider() {
        return new CategoryDaoProvider(context);
    }

    @Provides
    LaunchProfileProvider provideLaunchProfileProvider() {
        return new LaunchProfileProvider(context);
    }

    @Provides
    LocalDaoProvider provideLocalDaoProvider() {
        return new LocalDaoProvider(context);
    }

    @Provides
    ProductTagDaoProvider provideProductTagDaoProvider() {
        return new ProductTagDaoProvider(context);
    }

    @Provides
    PropertyDaoProvider providePropertyDaoProvider() {
        return new PropertyDaoProvider(context);
    }

}
