package com.fm.fmlib.models;

import android.content.Context;

import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.LocalList;
import com.fm.fmlib.dao.LocalListDao;
import com.fm.fmlib.dao.LocalTitle;
import com.fm.fmlib.dao.LocalTitleDao;
import com.fm.fmlib.models.component.DaggerDaoSessionComponent;
import com.fm.fmlib.models.component.DaoSessionComponent;
import com.fm.fmlib.utils.MappingUtil;
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
public class LocalDaoProvider {
    @Inject
    DaoSession mDaoSession;
    private LocalTitleDao mLocalTitleDao;
    private LocalListDao mLocalListDao;

    public LocalDaoProvider(Context context) {
        DaoSessionComponent sessionComponent = DaggerDaoSessionComponent.builder()
                .daoSessionProvider(new DaoSessionProvider(context)).build();
        mDaoSession = sessionComponent.provideSession();

        mLocalTitleDao = mDaoSession.getLocalTitleDao();
        mLocalListDao = mDaoSession.getLocalListDao();
    }

    public boolean saveLocalTitle(LocalTitle title){
        QueryBuilder<LocalTitle> queryBuilder = mLocalTitleDao.queryBuilder();
        queryBuilder.where(LocalTitleDao.Properties.Dest_id.eq(title.getDest_id()));
        LocalTitle ct = title;
        if (0 < queryBuilder.list().size()) {
            ct = queryBuilder.list().get(0);
            MappingUtil.lTitle2lTitle(title, ct);
        }
        return mLocalTitleDao.insertOrReplace(ct)>0;
    }

    public void saveLocalTitles(final List<LocalTitle> tags){
        mLocalTitleDao.deleteAll();
        mLocalTitleDao.insertInTx(new Iterable<LocalTitle>() {
            @Override
            public Iterator<LocalTitle> iterator() {
                return tags.iterator();
            }
        });
    }

    public List<LocalTitle> getLocalTitles(){
        QueryBuilder<LocalTitle> queryBuilder = mLocalTitleDao.queryBuilder();
        return queryBuilder.list();
    }

    public boolean saveLocalList(LocalList cList){
        QueryBuilder<LocalList> queryBuilder = mLocalListDao.queryBuilder();
        queryBuilder.where(LocalListDao.Properties.Local_id.eq(cList.getLocal_id()));
        LocalList ct = cList;
        if (0 < queryBuilder.list().size()) {
            ct = queryBuilder.list().get(0);
            MappingUtil.lList2lList(cList, ct);
        }
        return mLocalListDao.insertOrReplace(ct)>0;
    }

    public void saveLocalLists(final List<LocalList> tags){
        mLocalListDao.deleteAll();
        mLocalListDao.insertInTx(new Iterable<LocalList>() {
            @Override
            public Iterator<LocalList> iterator() {
                return tags.iterator();
            }
        });
    }

    public List<LocalList> getLocalLists(String dest_id){
        QueryBuilder<LocalList> queryBuilder = mLocalListDao.queryBuilder();
        queryBuilder.where(LocalListDao.Properties.Dest_id.eq(dest_id));
        return queryBuilder.list();
    }
}
