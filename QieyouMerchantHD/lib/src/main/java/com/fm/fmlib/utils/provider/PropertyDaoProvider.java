package com.fm.fmlib.utils.provider;

import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.Inn;
import com.fm.fmlib.dao.InnDao;
import com.fm.fmlib.dao.MyProperty;
import com.fm.fmlib.dao.MyPropertyDao;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class PropertyDaoProvider {
    private MyPropertyDao mPropertyDao;

    public PropertyDaoProvider(DaoSession session) {
        mPropertyDao = session.getMyPropertyDao();
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
