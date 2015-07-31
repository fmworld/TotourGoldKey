package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.client.BaseError;
import com.fm.fmlib.tour.Service.UtilService;
import com.fm.fmlib.tour.entity.TransferEntity;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class TrasInnManagerRunnable extends NetworkCallRunnable<TransferEntity> {
    @Override
    public TransferEntity doBackgroundCall() throws BaseError {
        return FmApplication.instance().getmTotour().getmUtilService()
                .transferToH5(UtilService.TransferType.innManage.toString(),null, FmApplication.instance().getToken());
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
