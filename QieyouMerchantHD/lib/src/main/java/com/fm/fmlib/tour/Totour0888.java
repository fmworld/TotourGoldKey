package com.fm.fmlib.tour;

import com.fm.fmlib.tour.Service.GoodsService;
import com.fm.fmlib.tour.Service.InnManagerService;
import com.fm.fmlib.tour.Service.ProductService;
import com.fm.fmlib.tour.Service.UserService;
import com.fm.fmlib.tour.Service.UtilService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class Totour0888 {
    private RestAdapter restAdapter ;

    private UserService mUserService;
    private UtilService mUtilService;
    private InnManagerService innManagerService;
    private GoodsService mGoodsService;
    private ProductService mProductService;
    public Totour0888(){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(TourConfig.instance().getEndpoint())
                .setConverter(new YangtaoConvert(gson))
                .setClient(new OkClient(okHttpClient))
                .build();

        mUserService = restAdapter.create(UserService.class);
        mUtilService = restAdapter.create(UtilService.class);
        innManagerService = restAdapter.create(InnManagerService.class);
        mProductService = restAdapter.create(ProductService.class);

        mGoodsService = restAdapter.create(GoodsService.class);
    }

    public UserService getmUserService() {
        return mUserService;
    }

    public UtilService getmUtilService() {
        return mUtilService;
    }

    public InnManagerService getInnManagerService() {
        return innManagerService;
    }

    public GoodsService getmGoodsService() {
        return mGoodsService;
    }

    public ProductService getProductService() {
        return mProductService;
    }
}
