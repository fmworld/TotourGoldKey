package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.inn;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.StoreInfoEntity;
import com.fm.fmlib.tour.entity.TransferEntity;
import com.fm.fmlib.utils.provider.MappingUtil;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class InnFetchStoreInfoRunnable extends TokenCheckedRunnable<StoreInfoEntity> {
    @Override
    public StoreInfoEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getInnManagerService().fetchStroeInfo(TourApplication.instance().getToken(),null);
    }
    public void onSuccessInBackground(StoreInfoEntity result){
        TourApplication.instance().updateData(MappingUtil.innInfoJson2inn(result.msg.inn));
    }
    @Override
    public void onSuccess(StoreInfoEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
    }

    @Override
    public void onError(RetrofitError be) {

    }
}
