package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.GoddsCommentsEntity;
import com.fm.fmlib.tour.params.GoodsProductParams;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 * 商品添加
 */
public class GoodsEditProductRunnable extends TokenCheckedRunnable<GoddsCommentsEntity> {
    private GoodsProductParams params;
    public GoodsEditProductRunnable(GoodsProductParams params){
        this.params = params;
    }

    @Override
    public GoddsCommentsEntity doBackground() throws RetrofitError {
        return FmApplication.instance().getmTotour().getmGoodsService().editProduct(params);
    }

    @Override
    public void onSuccess(GoddsCommentsEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
    }

    @Override
    public void onError(RetrofitError be) {

    }
}
