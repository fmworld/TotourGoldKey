package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.InnController;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.InnState;
import com.fm.fmlib.tour.entity.ProductInfoEntity;
import com.fm.fmlib.tour.entity.StoreCardEntity;
import com.fm.fmlib.tour.entity.StoreShareEntity;
import com.fm.fmlib.tour.entity.TransferEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class InnFetchProductInfoRunnable extends TokenCheckedRunnable<ProductInfoEntity> {
    private String product_id;
    public InnFetchProductInfoRunnable(String product_id){
        this.product_id = product_id;
    }
    @Override
    public ProductInfoEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour()
                .getInnManagerService()
                .fetchProductInfo(TourApplication.instance().getToken(),product_id);
    }

    public void onSuccessInBackground(ProductInfoEntity result){
//        TourApplication.instance().updateData(MappingUtil.innInfoJson2inn(result.msg.inn));
    }
    @Override
    public void onSuccess(ProductInfoEntity result) {

        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
        this.getBus().post(new InnState.InnFetchProductInfoEvent(result.msg));
    }

    @Override
    public void onError(RetrofitError be) {

    }
}
