package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.ShelvedEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 * 商品上架到别人铺子
 */
public class GoodsShelved4SelfRunnable extends TokenCheckedRunnable<ShelvedEntity> {
    private String item_id;
    public GoodsShelved4SelfRunnable(String item_id){
        this.item_id = item_id;
    }

    @Override
    public ShelvedEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmGoodsService().goodsShelved4Self(TourApplication.instance().getToken(), item_id);
    }

    @Override
    public void onSuccess(ShelvedEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
    }


}
