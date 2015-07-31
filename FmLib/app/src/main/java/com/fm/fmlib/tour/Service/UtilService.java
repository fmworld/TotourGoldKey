package com.fm.fmlib.tour.Service;

import com.fm.fmlib.tour.entity.FindPwdEntity;
import com.fm.fmlib.tour.entity.LoginEntity;
import com.fm.fmlib.tour.entity.LoginOutEntity;
import com.fm.fmlib.tour.entity.TransferEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by zhou feng'an on 2015/7/30.
 *
 */

public interface UtilService {
    enum TransferType{
        //提交订单页面
        submitOrder,
        //管理首页页面
        innManage
    }

    /**
     * 获取跳转的H5页面链接
     * @param target
     * @param item_id
     * @param token
     * @return
     */
    @POST("/transfer")
    @FormUrlEncoded
    TransferEntity transferToH5(@Field("target") String target, @Field("item") String item_id, @Query("token") String token);
}
