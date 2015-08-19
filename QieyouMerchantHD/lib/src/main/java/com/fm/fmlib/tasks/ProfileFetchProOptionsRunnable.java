package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.ProOptionsEntity;
import com.fm.fmlib.tour.entity.TagListEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class ProfileFetchProOptionsRunnable extends TokenCheckedRunnable<ProOptionsEntity> {
    private String city_id;
    public ProfileFetchProOptionsRunnable(String city_id){
        this.city_id = city_id;
    }


    @Override
    public ProOptionsEntity doBackground() throws RetrofitError {

        return TourApplication.instance().getmTotour().getmUtilService().fetchProductOptions(city_id);
    }

    public void onSuccessInBackground(ProOptionsEntity result){
        TourApplication.instance().getDaoCategory().saveCategoryTitles(result.msg.category.title);
        TourApplication.instance().getDaoCategory().saveCategoryLists(result.msg.category.list);
        TourApplication.instance().getDaoLocal().saveLocalTitles(result.msg.local.title);
        TourApplication.instance().getDaoLocal().saveLocalLists(result.msg.local.list);
    }

    @Override
    public void onSuccess(ProOptionsEntity result) {
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
        Log.v(TAG, "result errorInfo "+result.errorInfo);
    }

    @Override
    public void onError(RetrofitError be) {
        Log.v(TAG, "BaseError "+be);
    }
}
