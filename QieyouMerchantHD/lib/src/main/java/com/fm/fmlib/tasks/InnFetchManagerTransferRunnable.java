package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.InnController;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.HomeState;
import com.fm.fmlib.tour.Service.UtilService;
import com.fm.fmlib.tour.entity.TransferEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class InnFetchManagerTransferRunnable extends TokenCheckedRunnable<TransferEntity> {
    @Override
    public TransferEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmUtilService()
                .transferToH5(UtilService.TransferType.innManage.toString(),null, TourApplication.instance().getToken());
    }

    @Override
    public void onSuccessInBackground(TransferEntity result){
        TourApplication.instance().getPropertyDaoPri().saveProperty(InnController.InnTransfer.managerHome.toString(), result.msg);
    }

    @Override
    public void onSuccess(TransferEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
//        this.getBus().post(new HomeState.HomeManagerFetchEvent(result.msg));
    }

    @Override
    public void onError(RetrofitError be) {

    }
}
