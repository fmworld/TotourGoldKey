package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.GoddsCommentsEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 * 商品添加
 */
public class GoodsProductInfo4EditRunnable extends TokenCheckedRunnable<GoddsCommentsEntity> {
    private String product_id;
    public GoodsProductInfo4EditRunnable(String product_id){
        this.product_id = product_id;
    }

    @Override
    public GoddsCommentsEntity doBackground() throws RetrofitError {
        return FmApplication.instance().getmTotour().getmGoodsService().productInfo(FmApplication.instance().getToken(),product_id);
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
