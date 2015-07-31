package com.fm.fmlib.tour.Service;

import com.fm.fmlib.tour.entity.GoddsDetailEntity;
import com.fm.fmlib.tour.entity.ShelvedEntity;
import com.fm.fmlib.tour.params.GoodsFetchHomeParams;
import com.fm.fmlib.tour.entity.GoodsHomeEntity;
import com.fm.fmlib.tour.entity.ShopTagsEntity;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by zhou feng'an on 2015/7/30.
 *
 */

public interface GoodsService {
    /**
     * 获取铺子标签
     * @param token
     * @param page
     * @param perpage
     * @param lastId
     * @return
     */
    @POST("/item/tagList")
    @FormUrlEncoded
    ShopTagsEntity fetchShopTagList(@Query("token") String token, @Field("page") String page,
                                    @Field("perpage") String perpage, @Field("lastId") String lastId);

    /**
     * 商品上架到他人店铺
     * @param token
     * @param product_id
     * @param tag_id
     * @return
     */
    @POST("/item/upShelves")
    @FormUrlEncoded
    ShelvedEntity goodsShelved4Other(@Query("token") String token, @Field("product_id") String product_id, @Field("tag_id") String tag_id);

    /**
     * 商品上架到自己店铺
     * @param token
     * @param item_id
     * @return
     */
    @POST("/item/onshelves")
    @FormUrlEncoded
    ShelvedEntity goodsShelved4Self(@Query("token") String token, @Field("item") String item_id);

    /**
     * 获取铺子首页商品
     * @param homeParams
     * @return
     */
    @POST("/item/qieyou")
    GoodsHomeEntity fetchGoodsHomePage(@Body GoodsFetchHomeParams homeParams);

    /**
     * 获取商品详情
     * @param item
     * @return
     */
    @POST("/item/detail")
    @FormUrlEncoded
    GoddsDetailEntity fetchGoodsDetail(@Field("item") String item);


    /**
     * 获取评论详情
     * @param comment_id
     * @return
     */
    @POST("/item/comment")
    @FormUrlEncoded
    ShelvedEntity fetchGoodsCommentDetail(@Field("comment_id") String comment_id);

    /**
     * 获取评论列表
     * @param comment_id
     * @param type
     * @param page
     * @param perpage
     * @return
     */
    @POST("/item/commentlist")
    @FormUrlEncoded
    ShelvedEntity fetchGoodsComments(@Field("item_id") String comment_id,@Field("type") String type,@Field("page") String page,@Field("perpage") String perpage);
}
