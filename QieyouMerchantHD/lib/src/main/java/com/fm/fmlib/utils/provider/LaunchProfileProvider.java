package com.fm.fmlib.utils.provider;

import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.LaunchProfile;
import com.fm.fmlib.dao.LaunchProfileDao;
import com.fm.fmlib.dao.LocalList;
import com.fm.fmlib.dao.LocalListDao;
import com.fm.fmlib.dao.LocalTitle;
import com.fm.fmlib.dao.LocalTitleDao;
import com.fm.fmlib.utils.MappingUtil;

import java.util.Iterator;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class LaunchProfileProvider {
    private LaunchProfileDao launchProfileDao;
    public LaunchProfileProvider(DaoSession session) {
        launchProfileDao = session.getLaunchProfileDao();
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
                // TODO Auto-generated method stub
                return profiles.iterator();
            }
        });
    }
}
