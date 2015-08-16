package com.fm.fmlib.tour.Service;

import com.fm.fmlib.tour.entity.ProductInfoEntity;
import com.fm.fmlib.tour.entity.StoreCardEntity;
import com.fm.fmlib.tour.entity.StoreShareEntity;
import com.fm.fmlib.tour.entity.TransferEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by zhou feng'an on 2015/7/30.
 *
 */

public interface InnManagerService {
    @POST("/manage/index")
    TransferEntity fetchHomePage(@Query("token") String token);



    /**
     * 获取商户名片（数据不全mobile、innname、innhead）
     * @param token
     * @return
     */
    @POST("/inn/info")
    @FormUrlEncoded
    StoreCardEntity fetchStroeCard(@Query("token") String token, @Field("inn") String inn_id);

    /**
     * 获取商户分享信息
     * @param token
     * @return
     */
    @POST("/item/share")
    @FormUrlEncoded
    StoreShareEntity fetchStroeShareInfo(@Query("token") String token, @Field("id") String inn_id, @Field("type") String type);

    /**
     * 获取支付方式选择连接（target :  orderPayment）
     * @param token
     * @param order_id
     * @param target
     * @return
     */
    @POST("/transfer")
    @FormUrlEncoded
    TransferEntity fetchOrderPaymentType(@Query("token") String token, @Field("order") String order_id, @Field("target") String target);

    @POST("/item/ownerEdit")
    @FormUrlEncoded
    ProductInfoEntity fetchProductInfo(@Query("token") String token, @Field("item") String product_id);
}
