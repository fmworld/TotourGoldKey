package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.StoreCardEntity;
import com.fm.fmlib.utils.MappingUtil;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class InnFetchStoreCardRunnable extends TokenCheckedRunnable<StoreCardEntity> {
    @Override
    public StoreCardEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getInnManagerService().fetchStroeCard(TourApplication.instance().getToken(), null);
    }
    public void onSuccessInBackground(StoreCardEntity result){
        TourApplication.instance().updateData(MappingUtil.innInfoJson2inn(result.msg.inn));
    }
    @Override
    public void onSuccess(StoreCardEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
    }

    @Override
    public void onError(RetrofitError be) {

    }
}
