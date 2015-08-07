package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.params.GoodsFetchHomeParams;
import com.fm.fmlib.tour.entity.GoodsHomeEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class GoodsFetchHomeRunnable extends TokenCheckedRunnable<GoodsHomeEntity> {
    private GoodsFetchHomeParams homeParams;
    public GoodsFetchHomeRunnable(GoodsFetchHomeParams homeParams){
        this.homeParams = homeParams;
    }

    @Override
    public GoodsHomeEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmGoodsService().fetchGoodsHomePage(this.homeParams);
    }

    @Override
    public void onSuccess(GoodsHomeEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg.size());
        for(GoodsHomeEntity.Product item :result.msg){
            Log.v(TAG, "result product_name "+item.product_name);
        }
    }

    @Override
    public void onError(RetrofitError be) {

    }
}
