package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.StateEntity;
import com.fm.fmlib.tour.params.ProductParams;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class InnAddProductInfoRunnable extends TokenCheckedRunnable<StateEntity> {
    private ProductParams params;
    public InnAddProductInfoRunnable(ProductParams params){
        this.params = params;
    }
    @Override
    public StateEntity doBackground() throws RetrofitError {
//        return TourApplication.instance().getmTotour()
//                .getInnManagerService()
//                .updateProductInfo(TourApplication.instance().getToken(), params);
        return TourApplication.instance().getmTotour()
                .getInnManagerService()
                .addProduct(TourApplication.instance().getToken(), params.product_images,
                        params.editProduct,params.ccid,params.cid,params.tuan_end_time,params.note,params.booking_info
                ,params.traveler,params.content,params.innholder,params.product_name,params.keyword,
                        params.price,params.old_price,params.tag_id,params.quantity, params.thumb);
    }
    public void onSuccessInBackground(StateEntity result){
//        TourApplication.instance().updateData(MappingUtil.innInfoJson2inn(result.msg.inn));
    }
    @Override
    public void onSuccess(StateEntity result) {

        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg " + result.msg);
    }

}
