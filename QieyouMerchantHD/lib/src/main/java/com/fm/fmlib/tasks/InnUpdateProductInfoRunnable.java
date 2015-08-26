package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.InnController;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.InnState;
import com.fm.fmlib.tour.entity.ProductInfoEntity;
import com.fm.fmlib.tour.entity.StateEntity;
import com.fm.fmlib.tour.entity.StoreCardEntity;
import com.fm.fmlib.tour.entity.StoreShareEntity;
import com.fm.fmlib.tour.params.ProductParams;

import retrofit.RetrofitError;
import retrofit.http.Field;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class InnUpdateProductInfoRunnable extends TokenCheckedRunnable<StateEntity> {
    private ProductParams params;
    public InnUpdateProductInfoRunnable(ProductParams params){
        this.params = params;
    }
    @Override
    public StateEntity doBackground() throws RetrofitError {
//        return TourApplication.instance().getmTotour()
//                .getInnManagerService()
//                .updateProductInfo(TourApplication.instance().getToken(), params);
        return TourApplication.instance().getmTotour()
                .getInnManagerService()
                .updateProductInfo(TourApplication.instance().getToken(), params.item,params.product_images,
                        params.editProduct,params.ccid,params.cid,params.tuan_end_time,params.note,params.booking_info
                ,params.traveler,params.content,params.innholder,params.product_name,params.keyword,params.price,params.old_price,params.tag_id,params.quantity);
    }
    public void onSuccessInBackground(StateEntity result){
//        TourApplication.instance().updateData(MappingUtil.innInfoJson2inn(result.msg.inn));
    }
    @Override
    public void onSuccess(StateEntity result) {

        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
    }

    @Override
    public void onError(RetrofitError be) {

    }
}