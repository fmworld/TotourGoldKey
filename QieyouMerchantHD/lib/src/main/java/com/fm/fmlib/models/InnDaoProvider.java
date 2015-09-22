package com.fm.fmlib.models;

import android.content.Context;

import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.Inn;
import com.fm.fmlib.dao.InnDao;
import com.fm.fmlib.models.component.DaggerDaoSessionComponent;
import com.fm.fmlib.utils.MappingUtil;
import com.fm.fmlib.models.component.DaoSessionComponent;
import com.fm.fmlib.models.provider.DaoSessionProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
@Singleton
public class InnDaoProvider {
    @Inject DaoSession mDaoSession;
    private Inn mCurrentInn;
    private InnDao mInnDao;
    public InnDaoProvider(Context context){
        DaoSessionComponent sessionComponent = DaggerDaoSessionComponent.builder()
                .daoSessionProvider(new DaoSessionProvider(context)).build();
        mDaoSession = sessionComponent.provideSession();
        mInnDao = mDaoSession.getInnDao();
    }

    private Inn getUserInn(String account){
        if(null !=account){
            QueryBuilder<Inn> queryBuilder = mInnDao.queryBuilder();
            queryBuilder.where(InnDao.Properties.InnerMoblie.eq(account));
            if(0 < queryBuilder.list().size()){
                return queryBuilder.list().get(0);
            }
        }

        return new Inn();
    }

    public void initInnData(String account){
        mCurrentInn = getUserInn(account);
    }

    public String getInnerContact() {
        return mCurrentInn.getInnerContact();
    }

    public void setInnerContact(String innerContact) {
        mCurrentInn.setInnerContact(innerContact);
    }

    public String getInnerHead() {
        return mCurrentInn.getInnerHead();
    }

    public void setInnerHead(String innerHead) {
        mCurrentInn.setInnerHead(innerHead);
    }

    public String getInnerIdentify() {
        return mCurrentInn.getInnerIdentify();
    }

    public void setInnerIdentify(String innerIdentify) {
        mCurrentInn.setInnerIdentify(innerIdentify);
    }

    public String getInnerMoblie() {
        return mCurrentInn.getInnerMoblie();
    }

    public void setInnerMoblie(String innerMoblie) {
        mCurrentInn.setInnerMoblie(innerMoblie);
    }

    public String getInnerScore() {
        return mCurrentInn.getInnerScore();
    }

    public void setInnerScore(String innerScore) {
        mCurrentInn.setInnerScore(innerScore);
    }

    public String getInnerServe() {
        return mCurrentInn.getInnerServe();
    }

    public void setInnerServe(String innerServe) {
        mCurrentInn.setInnerServe(innerServe);
    }

    public String getInnerTodayServe() {
        return mCurrentInn.getInnerTodayServe();
    }

    public void setInnerTodayServe(String innerTodayServe) {
        mCurrentInn.setInnerTodayServe(innerTodayServe);
    }

    public String getInnHead() {
        return mCurrentInn.getInnHead();
    }

    public void setInnHead(String innHead) {
        mCurrentInn.setInnHead(innHead);
    }

    public String getInnProducts() {
        return mCurrentInn.getInnProducts();
    }

    public void setInnProducts(String innProducts) {
        mCurrentInn.setInnProducts(innProducts);
    }

    public String getInnName() {
        return mCurrentInn.getInnName();
    }

    public void setInnName(String innName) {
        mCurrentInn.setInnName(innName);
    }

    public boolean save(){
       return mInnDao.insertOrReplace(mCurrentInn)>0;
    }

    public boolean saveWithInn(Inn temp){
        if(null == temp){
            return false;
        }
        MappingUtil.inn2inn(temp, mCurrentInn);
        return save();
    }

    public Inn getmCurrentInn(){
        return  mCurrentInn;
    }

}
