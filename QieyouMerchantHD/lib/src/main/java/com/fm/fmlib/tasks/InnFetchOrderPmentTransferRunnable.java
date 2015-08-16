package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.InnController;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.InnState;
import com.fm.fmlib.tour.Service.UtilService;
import com.fm.fmlib.tour.entity.StoreCardEntity;
import com.fm.fmlib.tour.entity.StoreShareEntity;
import com.fm.fmlib.tour.entity.TransferEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class InnFetchOrderPmentTransferRunnable extends TokenCheckedRunnable<TransferEntity> {
    private String order_id;

    public InnFetchOrderPmentTransferRunnable(String order_id){
        this.order_id = order_id;
    }

    @Override
    public TransferEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour()
                .getInnManagerService()
                .fetchOrderPaymentType(TourApplication.instance().getToken(), order_id, "orderPayment");
//        return TourApplication.instance().getmTotour().getmUtilService()
//                .transferToH5(UtilService.TransferType.submitOrder.toString(), order_id, TourApplication.instance().getToken());
    }
    public void onSuccessInBackground(StoreCardEntity result){
//        TourApplication.instance().updateData(MappingUtil.innInfoJson2inn(result.msg.inn));
    }
    @Override
    public void onSuccess(TransferEntity result) {

        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
        this.getBus().post(new InnState.InnFetchPaymentTypeEvent(result.msg));
    }

    @Override
    public void onError(RetrofitError be) {

    }
}
