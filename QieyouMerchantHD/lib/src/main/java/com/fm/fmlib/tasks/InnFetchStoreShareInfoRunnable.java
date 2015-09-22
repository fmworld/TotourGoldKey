package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.InnController;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.InnState;
import com.fm.fmlib.tour.entity.StoreCardEntity;
import com.fm.fmlib.tour.entity.StoreShareEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class InnFetchStoreShareInfoRunnable extends TokenCheckedRunnable<StoreShareEntity> {
    private String inn_id;
    public InnFetchStoreShareInfoRunnable(String inn_id){
        this.inn_id = inn_id;
    }
    @Override
    public StoreShareEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour()
                .getInnManagerService()
                .fetchStroeShareInfo(TourApplication.instance().getToken(), inn_id, InnController.InnType.inn.toString());
    }
    public void onSuccessInBackground(StoreCardEntity result){
//        TourApplication.instance().updateData(MappingUtil.innInfoJson2inn(result.msg.inn));
    }
    @Override
    public void onSuccess(StoreShareEntity result) {

        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
        this.getBus().post(new InnState.InnFetchShareInfoEvent(result.msg.thumb,result.msg.name,result.msg.url));
    }


}
