package com.fm.fmlib.models.component;

import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.models.provider.DaoSessionProvider;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zhoufeng'an on 2015/9/14.
 */
@Component(modules = DaoSessionProvider.class)
@Singleton
public interface DaoSessionComponent {
    DaoSession provideSession();
}
