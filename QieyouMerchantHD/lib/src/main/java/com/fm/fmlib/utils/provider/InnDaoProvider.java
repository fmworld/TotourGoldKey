package com.fm.fmlib.utils.provider;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.inn;
import com.fm.fmlib.dao.innDao;
import com.fm.fmlib.dao.user;
import com.fm.fmlib.dao.userDao;
import com.fm.fmlib.tour.bean.UserInfo;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class InnDaoProvider {
    private inn mCurrentInn;
    private innDao mInnDao;
    public InnDaoProvider(DaoSession session, String account){
        mInnDao = session.getInnDao();
        mCurrentInn = getUserInn(account);
    }

    private inn getUserInn(String account){
        if(null !=account){
            QueryBuilder<inn> queryBuilder = mInnDao.queryBuilder();
            queryBuilder.where(innDao.Properties.InnerMoblie.eq(account));
            if(0 < queryBuilder.list().size()){
                return queryBuilder.list().get(0);
            }
        }

        return new inn();
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

    public boolean saveWithInn(inn temp){
        if(null == temp){
            return false;
        }
        MappingUtil.inn2inn(temp, mCurrentInn);
        return save();
    }

    public inn getmCurrentInn(){
        return  mCurrentInn;
    }

}
