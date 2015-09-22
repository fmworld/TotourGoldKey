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
public class GoodsShelved4OtherRunnable extends TokenCheckedRunnable<ShelvedEntity> {
    private String product_id;
    private String tag_id;
    public GoodsShelved4OtherRunnable(String product_id, String tag_id){
        this.product_id = product_id;
        this.tag_id = tag_id;
    }

    @Override
    public ShelvedEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmGoodsService().goodsShelved4Other(TourApplication.instance().getToken(), product_id, tag_id);
    }

    @Override
    public void onSuccess(ShelvedEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
    }


}
