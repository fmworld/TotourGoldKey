package com.qieyou.qieyoustore.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.UserController;
import com.fm.fmlib.network.TourConfig;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.widget.AnimListenFragment;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeSettingInfo extends AnimListenFragment implements UserController.UserSettingUi, View.OnClickListener {
    private View content;

    public static HomeSettingInfo create() {
        HomeSettingInfo fragment = new HomeSettingInfo();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_home_setting, null);
        initView();
        return content;
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);
    }

    @Override
    public void onPause() {
        getController().detachUi(this);
        super.onPause();
    }

    UserController getController() {
        return MerchanthdApplication.instance().getmMainController().getmUserController();
    }

    @Override
    public void setCallbacks(UserController.UserUiCallbacks callbacks) {
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void initView() {
        ((TextView) content.findViewById(R.id.home_setting_phone)).setText(TourApplication.instance().getDaoUser().getUserMobile());
        ((TextView) content.findViewById(R.id.home_setting_user_name)).setText(TourApplication.instance().getDaoUser().getInnerName());
        ((SimpleDraweeView) content.findViewById(R.id.home_setting_user_icon))
                .setImageURI(Uri.parse(TourConfig.instance().getImageRoot() + "/" + TourApplication.instance()
                        .getDaoUser().getInnerHead()));
    }
}
