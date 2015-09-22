package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.LaunchProfile;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.tour.entity.LaunchProfileEntity;
import com.fm.fmlib.tour.entity.TagListEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class LaunchFetchProfileRunnable extends TokenCheckedRunnable<LaunchProfileEntity> {
    private final String  tagsercont ="tagsercont";
    private final String  tagserupstame ="tagserupstame";
     public LaunchFetchProfileRunnable(){}


    @Override
    public LaunchProfileEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmUtilService().fetchLaunchProfile();
    }

    public void onSuccessInBackground(LaunchProfileEntity result){
//        if(!result.msg.count.equals(TourApplication.instance().getDaoProperty().getValue(tagsercont))
//                || !result.msg.last_update.equals(TourApplication.instance().getDaoProperty().getValue(tagserupstame))){
//            TourApplication.instance().getDaoProductTag().saveProductTags(result.msg.tags);
//            TourApplication.instance().getDaoProperty().saveProperty(tagsercont, result.msg.count);
//            TourApplication.instance().getDaoProperty().saveProperty(tagserupstame,result.msg.last_update);
//        }
        List<LaunchProfile> profiles = new ArrayList<>();
        LaunchProfile profile = new LaunchProfile();
        profile.setType(ProductState.LaunchProfileType.ad.toString());
        profile.setImg(result.msg.ad.getImg());
        profile.setLink(result.msg.ad.getLink());
        profiles.add(profile);

        profile = new LaunchProfile();
        profile.setType(ProductState.LaunchProfileType.shop.toString());
        profile.setImg(result.msg.shop.getImg());
        profile.setLink(result.msg.shop.getLink());
        profile.setTag(result.msg.shop.getTag());
        profiles.add(profile);

        profile = new LaunchProfile();
        profile.setType(ProductState.LaunchProfileType.slider.toString());
        profile.setImg(result.msg.slider.getImg());
        profile.setLink(result.msg.slider.getLink());
        profiles.add(profile);

        for(LaunchProfile item :result.msg.store ){
            profile = new LaunchProfile();
            profile.setType(ProductState.LaunchProfileType.store.toString());
            profile.setImg(item.getImg());
            profile.setLink(item.getLink());
            profiles.add(profile);
        }

        TourApplication.instance().getDaoLaunProfile().saveLaunProfiles(profiles);
    }

    @Override
    public void onSuccess(LaunchProfileEntity result) {
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
        Log.v(TAG, "result errorInfo "+result.errorInfo);
//        this.getBus().post(new ProductState.ProductFetchTagListEvent());
    }


}
