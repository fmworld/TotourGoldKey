package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.client.BaseError;
import com.fm.fmlib.tour.Service.UtilService;
import com.fm.fmlib.tour.entity.LoginOutEntity;
import com.fm.fmlib.tour.entity.TransferEntity;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class TrasSbmOrderRunnable extends NetworkCallRunnable<TransferEntity> {
    private String item_id;
    public TrasSbmOrderRunnable(String item_id){
        this.item_id =item_id;
    }
    @Override
    public TransferEntity doBackgroundCall() throws BaseError {
        return FmApplication.instance().getmTotour().getmUtilService()
                .transferToH5(UtilService.TransferType.submitOrder.toString(),this.item_id, FmApplication.instance().getToken());
    }

    @Override
    public void onSuccess(TransferEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
    }

    @Override
    public void onError(BaseError be) {

    }
}
