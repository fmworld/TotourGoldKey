package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.client.BaseError;
import com.fm.fmlib.tour.entity.ShelvedEntity;

/**
 * Created by zhoufeng'an on 2015/7/31.
 * 商品上架到别人铺子
 */
public class GoodsShelved4OtherRunnable extends NetworkCallRunnable<ShelvedEntity>{
    private String product_id;
    private String tag_id;
    public GoodsShelved4OtherRunnable(String product_id, String tag_id){
        this.product_id = product_id;
        this.tag_id = tag_id;
    }

    @Override
    public ShelvedEntity doBackgroundCall() throws BaseError {
        return FmApplication.instance().getmTotour().getmGoodsService().goodsShelved4Other(FmApplication.instance().getToken(), product_id, tag_id);
    }

    @Override
    public void onSuccess(ShelvedEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
    }

    @Override
    public void onError(BaseError be) {

    }
}
