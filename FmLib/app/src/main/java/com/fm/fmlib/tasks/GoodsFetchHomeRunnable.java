package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.client.BaseError;
import com.fm.fmlib.tour.params.GoodsFetchHomeParams;
import com.fm.fmlib.tour.entity.GoodsHomeEntity;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class GoodsFetchHomeRunnable extends NetworkCallRunnable<GoodsHomeEntity>{
    private GoodsFetchHomeParams homeParams;
    public GoodsFetchHomeRunnable(GoodsFetchHomeParams homeParams){
        this.homeParams = homeParams;
    }

    @Override
    public GoodsHomeEntity doBackgroundCall() throws BaseError {
        return FmApplication.instance().getmTotour().getmGoodsService().fetchGoodsHomePage(this.homeParams);
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
    public void onError(BaseError be) {

    }
}
