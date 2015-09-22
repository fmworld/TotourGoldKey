package com.fm.fmlib.tour.Service;

import com.fm.fmlib.tour.bean.ProductInfo;
import com.fm.fmlib.tour.entity.ProductInfoEntity;
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

public interface InnManagerService {
    @POST("/manage/index")
    TransferEntity fetchHomePage(@Query("token") String token);


    /**
     * 获取商户名片（数据不全mobile、innname、innhead）
     *
     * @param token
     * @return
     */
    @POST("/inn/info")
    @FormUrlEncoded
    StoreCardEntity fetchStroeCard(@Query("token") String token, @Field("inn") String inn_id);

    /**
     * 获取商户分享信息
     *
     * @param token
     * @return
     */
    @POST("/item/share")
    @FormUrlEncoded
    StoreShareEntity fetchStroeShareInfo(@Query("token") String token, @Field("id") String inn_id, @Field("type") String type);

    /**
     * 获取支付方式选择连接（target :  orderPayment）
     *
     * @param token
     * @param order_id
     * @param target
     * @return
     */
    @POST("/transfer")
    @FormUrlEncoded
    TransferEntity fetchOrderPaymentType(@Query("token") String token, @Field("order") String order_id, @Field("target") String target);

    /**
     * 获取待编辑的产品
     *
     * @param token
     * @param product_id
     * @return
     */
    @POST("/item/ownerEdit")
    @FormUrlEncoded
    ProductInfoEntity fetchProductInfo(@Query("token") String token, @Field("item") String product_id);

    @POST("/item/editProduct")
    StateEntity updateProductInfo(@Query("token") String token, @Body ProductParams product);

    /**
     * 编辑商品信息
     *
     * @param token
     * @param item
     * @param product_images
     * @param editProduct
     * @param ccid
     * @param cid
     * @param tuan_end_time
     * @param note
     * @param booking_info
     * @param traveler
     * @param content
     * @param innholder
     * @param product_name
     * @param keyword
     * @param price
     * @param old_price
     * @param tag_id
     * @param quantity
     * @return
     */
    @FormUrlEncoded
    @POST("/item/editProduct")
    StateEntity updateProductInfo(@Query("token") String token,
                                  @Field("item") String item,
                                  @Field("product_images") String product_images,
                                  @Field("editProduct") String editProduct,
                                  @Field("ccid") String ccid,
                                  @Field("cid") String cid,
                                  @Field("tuan_end_time") String tuan_end_time,
                                  @Field("note") String note,
                                  @Field("booking_info") String booking_info,
                                  @Field("traveler") String traveler,
                                  @Field("content") String content,
                                  @Field("innholder") String innholder,
                                  @Field("product_name") String product_name,
                                  @Field("keyword") String keyword,
                                  @Field("price") String price,
                                  @Field("old_price") String old_price,
                                  @Field("tag_id") String tag_id,
                                  @Field("quantity") String quantity,
                                  @Field("thumb") String thumb);

    /**
     * 添加商品
     *
     * @param token
     * @param product_images
     * @param editProduct
     * @param ccid
     * @param cid
     * @param tuan_end_time
     * @param note
     * @param booking_info
     * @param traveler
     * @param content
     * @param innholder
     * @param product_name
     * @param keyword
     * @param price
     * @param old_price
     * @param tag_id
     * @param quantity
     * @return
     */
    @FormUrlEncoded
    @POST("/item/addProduct")
    StateEntity addProduct(@Query("token") String token,
                           @Field("product_images") String product_images,
                           @Field("editProduct") String editProduct,
                           @Field("ccid") String ccid,
                           @Field("cid") String cid,
                           @Field("tuan_end_time") String tuan_end_time,
                           @Field("note") String note,
                           @Field("booking_info") String booking_info,
                           @Field("traveler") String traveler,
                           @Field("content") String content,
                           @Field("innholder") String innholder,
                           @Field("product_name") String product_name,
                           @Field("keyword") String keyword,
                           @Field("price") String price,
                           @Field("old_price") String old_price,
                           @Field("tag_id") String tag_id,
                           @Field("quantity") String quantity,
                           @Field("thumb") String thumb);
}
