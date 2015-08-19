package com.fm.fmlib.tour.Service;

import com.fm.fmlib.tour.entity.ProductBreEntity;
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
 *
 */

public interface ProductService {
    @FormUrlEncoded
    @POST("/item/getItems")
    ProductBreEntity fetchProduct(@Query("token") String token, @Field("item") String items);




}
