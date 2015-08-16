package com.fm.fmlib.tour;

import com.fm.fmlib.tour.Service.GoodsService;
import com.fm.fmlib.tour.Service.InnManagerService;
import com.fm.fmlib.tour.Service.UserService;
import com.fm.fmlib.tour.Service.UtilService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import retrofit.RestAdapter;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class Totour0888 {
    private TourConfig mTourConfig;
    private RestAdapter restAdapter ;

    private UserService mUserService;
    private UtilService mUtilService;
    private InnManagerService innManagerService;
    private GoodsService mGoodsService;
    public Totour0888(){
        mTourConfig = new TourConfig();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(mTourConfig.getEndpoint())
                .setConverter(new YangtaoConvert(gson))
                .build();
        mUserService = restAdapter.create(UserService.class);
        mUtilService = restAdapter.create(UtilService.class);
        innManagerService = restAdapter.create(InnManagerService.class);
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
}
