package com.fm.fmlib.models;

import android.content.Context;

import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.LaunchProfile;
import com.fm.fmlib.dao.LaunchProfileDao;
import com.fm.fmlib.models.component.DaggerDaoSessionComponent;
import com.fm.fmlib.models.component.DaoSessionComponent;
import com.fm.fmlib.models.provider.DaoSessionProvider;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
@Singleton
public class LaunchProfileProvider {
    @Inject
    DaoSession mDaoSession;

    private LaunchProfileDao launchProfileDao;
    public LaunchProfileProvider(Context context) {
        DaoSessionComponent sessionComponent = DaggerDaoSessionComponent.builder()
                .daoSessionProvider(new DaoSessionProvider(context)).build();
        mDaoSession = sessionComponent.provideSession();

        launchProfileDao = mDaoSession.getLaunchProfileDao();
    }



    public List<LaunchProfile> getLaunProfiles(String type){
        QueryBuilder<LaunchProfile> queryBuilder = launchProfileDao.queryBuilder();
        queryBuilder.where(LaunchProfileDao.Properties.Type.eq(type));
        return queryBuilder.list();
    }

    public void saveLaunProfiles(final List<LaunchProfile> profiles){
        launchProfileDao.deleteAll();
        launchProfileDao.insertInTx(new Iterable<LaunchProfile>() {
            @Override
            public Iterator<LaunchProfile> iterator() {
                return profiles.iterator();
            }
        });
    }
}
