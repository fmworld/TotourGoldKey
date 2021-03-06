package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.Service.UtilService;
import com.fm.fmlib.tour.entity.TransferEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class TrasSbmOrderRunnable extends TokenCheckedRunnable<TransferEntity> {
    private String item_id;
    public TrasSbmOrderRunnable(String item_id){
        this.item_id =item_id;
    }
    @Override
    public TransferEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmUtilService()
                .transferToH5(UtilService.TransferType.submitOrder.toString(),this.item_id, TourApplication.instance().getToken());
    }

    @Override
    public void onSuccess(TransferEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
    }


}
