package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.GoddsCommentsEntity;
import com.fm.fmlib.tour.params.GoodsProductParams;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 * 商品添加
 */
public class GoodsAddProductRunnable extends TokenCheckedRunnable<GoddsCommentsEntity> {
    private GoodsProductParams params;
    public GoodsAddProductRunnable(GoodsProductParams params){
        this.params = params;
    }

    @Override
    public GoddsCommentsEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmGoodsService().addProduct(params);
    }

    @Override
    public void onSuccess(GoddsCommentsEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
    }
}
