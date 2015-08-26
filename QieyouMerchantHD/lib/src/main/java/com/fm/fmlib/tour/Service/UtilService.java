package com.fm.fmlib.tour.Service;

import com.fm.fmlib.tour.entity.LaunchProfileEntity;
import com.fm.fmlib.tour.entity.ProOptionsEntity;
import com.fm.fmlib.tour.entity.StateEntity;
import com.fm.fmlib.tour.entity.TagListEntity;
import com.fm.fmlib.tour.entity.TransferEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by zhou feng'an on 2015/7/30.
 */

public interface UtilService {
    enum TransferType {
        //提交订单页面
        submitOrder,
        //管理首页页面
        innManage
    }


    enum UploadType {
        product,
        innheadimg,
        userheadimg
    }

    /**
     * 获取跳转的H5页面链接
     *
     * @param target
     * @param item_id
     * @param token
     * @return
     */
    @POST("/transfer")
    @FormUrlEncoded
    TransferEntity transferToH5(@Field("target") String target, @Field("item") String item_id, @Query("token") String token);

    /**
     * 获取标签列表
     *
     * @param token
     * @param inn_id
     * @param detail_type
     * @return
     */
    @POST("/item/tagList")
    @FormUrlEncoded
    TagListEntity fetchTagList(@Query("token") String token, @Field("inn") String inn_id, @Field("detail") String detail_type);

    /**
     * 获取商品类型
     *
     * @param city_id
     * @return
     */
    @GET("/item/option")
    ProOptionsEntity fetchProductOptions(@Query("city") String city_id);

    @Multipart
    @POST("/upload")
    StateEntity uploadData(@Query("token") String token, @Query("type") String product, @Part("imgFile") TypedFile pic);

    @GET("/login/deviceAd")
    LaunchProfileEntity fetchLaunchProfile();
}
