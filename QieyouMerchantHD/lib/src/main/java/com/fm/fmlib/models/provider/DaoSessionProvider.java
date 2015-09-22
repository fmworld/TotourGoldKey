package com.fm.fmlib.models.provider;

import android.content.Context;

import com.fm.fmlib.dao.DaoMaster;
import com.fm.fmlib.dao.DaoSession;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhoufeng'an on 2015/9/11.
 */
@Module
public class DaoSessionProvider {
    private static final String DB_NAME ="totour-db";

    private Context mContext;
    public DaoSessionProvider(Context mContext){
        this.mContext = mContext;
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext.getApplicationContext(), DB_NAME, null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        return daoMaster.newSession();
    }
}
