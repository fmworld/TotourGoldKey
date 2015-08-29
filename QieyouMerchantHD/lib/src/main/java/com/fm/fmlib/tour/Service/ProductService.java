package com.fm.fmlib.tour.Service;

import com.fm.fmlib.tour.entity.CodeInfoEntity;
import com.fm.fmlib.tour.entity.ProCommentsEntity;
import com.fm.fmlib.tour.entity.ProductBreEntity;
import com.fm.fmlib.tour.entity.ProductDetailEntity;
import com.fm.fmlib.tour.entity.ProductInfoEntity;
import com.fm.fmlib.tour.entity.ProductsEntity;
import com.fm.fmlib.tour.entity.StateEntity;
import com.fm.fmlib.tour.entity.StoreCardEntity;
import com.fm.fmlib.tour.entity.StoreShareEntity;
import com.fm.fmlib.tour.entity.TransferEntity;
import com.fm.fmlib.tour.params.ProductParams;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by zhou feng'an on 2015/7/30.
 */

public interface ProductService {
    @FormUrlEncoded
    @POST("/item/getItems")
    ProductBreEntity fetchProducts(@Query("token") String token, @Field("item") String items);

    /**
     * 获取产品列表
     *
     * @param token
     * @param page
     * @param perpage
     * @param city
     * @param cid
     * @param ccid
     * @param sort
     * @param dest
     * @return
     */
    @POST("/item/qieyou")
    ProductsEntity fetchProducts(@Query("token") String token,
                                 @Query("page") String page,
                                 @Query("perpage") String perpage,
                                 @Query("city") String city,
                                 @Query("cid") String cid,
                                 @Query("ccid") String ccid,
                                 @Query("sort") String sort,
                                 @Query("dest") String dest);

    /**
     * 获取商品详情
     *
     * @param token
     * @param item
     * @return
     */
    @FormUrlEncoded
    @POST("/item/detail")
    ProductDetailEntity fetchProductDetail(@Query("token") String token, @Field("item") String item);

    /**
     * 验证电子券
     *
     * @param token
     * @param device_type
     * @return
     */
    @FormUrlEncoded
    @POST("/inn/submitCoupon")
    CodeInfoEntity fetchCodeVeriState(@Query("token") String token, @Field("refer ") String device_type);


    @FormUrlEncoded
    @POST("/item/commentlist")
    ProCommentsEntity fetchProComments(@Query("token") String token,
                                    @Field("item") String productId,
                                    @Field("type") String type,
                                    @Field("page") String page,
                                    @Field("perpage") String perpage);

    @FormUrlEncoded
    @POST("/item/changeShelves")
    StateEntity changeShelfState(
                                       @Field("tag_id") String tag_id,
                                       @Field("product_id") String product_id,
                                       @Field("action") String action
                                       );

}
