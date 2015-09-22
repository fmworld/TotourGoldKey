package com.fm.fmlib.models;

import android.content.Context;

import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.MyProperty;
import com.fm.fmlib.dao.MyPropertyDao;
import com.fm.fmlib.models.component.DaggerDaoSessionComponent;
import com.fm.fmlib.models.component.DaoSessionComponent;
import com.fm.fmlib.models.provider.DaoSessionProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
@Singleton
public class PropertyDaoProvider {
    @Inject
    DaoSession mDaoSession;
    private MyPropertyDao mPropertyDao;

    public PropertyDaoProvider(Context context) {
        DaoSessionComponent sessionComponent = DaggerDaoSessionComponent.builder()
                .daoSessionProvider(new DaoSessionProvider(context)).build();
        mDaoSession = sessionComponent.provideSession();

        mPropertyDao = mDaoSession.getMyPropertyDao();
    }

    public String getValue(String key) {
        if (null == key) {
            return null;
        }

        QueryBuilder<MyProperty> queryBuilder = mPropertyDao.queryBuilder();
        queryBuilder.where(MyPropertyDao.Properties.Key.eq(key));
        if (0 < queryBuilder.list().size()) {
            return queryBuilder.list().get(0).getValue();
        }
        return null;
    }

    public String getValue(String key, String defaultValue) {
        if (null == key) {
            return defaultValue;
        }

        QueryBuilder<MyProperty> queryBuilder = mPropertyDao.queryBuilder();
        queryBuilder.where(MyPropertyDao.Properties.Key.eq(key));
        if (0 < queryBuilder.list().size()) {
            return queryBuilder.list().get(0).getValue();
        }
        return defaultValue;
    }

    public boolean saveProperty(String key, String value){
        QueryBuilder<MyProperty> queryBuilder = mPropertyDao.queryBuilder();
        queryBuilder.where(MyPropertyDao.Properties.Key.eq(key));
        MyProperty p;
        if (0 < queryBuilder.list().size()) {
            p = queryBuilder.list().get(0);
        }else{
            p = new MyProperty();
            p.setKey(key);
        }
        p.setValue(value);
        return mPropertyDao.insertOrReplace(p)>0;
    }
}
