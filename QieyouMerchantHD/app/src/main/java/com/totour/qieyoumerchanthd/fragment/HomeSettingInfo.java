package com.totour.qieyoumerchanthd.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.UserController;
import com.fm.fmlib.network.TourConfig;
import com.totour.qieyoumerchanthd.BaseTourActivity;
import com.totour.qieyoumerchanthd.LoginActivity;
import com.totour.qieyoumerchanthd.MerchanthdApplication;
import com.totour.qieyoumerchanthd.R;
import com.totour.qieyoumerchanthd.widget.AnimListenFragment;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeSettingInfo extends AnimListenFragment implements UserController.UserLoginUi, View.OnClickListener {
    private UserController.UserLoginUiCallbacks mUserLoginUiCallbacks;
    private EditText login_account;
    private EditText login_pwd;
    private Button login_loginIn;

    public static HomeSettingInfo create() {
        HomeSettingInfo fragment = new HomeSettingInfo();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_setting, null);
        initView(view);
        return view;
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
    public void logined() {
        ((LoginActivity) this.getActivity()).getDisplay().showHomePage();
    }

    @Override
    public void loginFinished() {

    }

    @Override
    public void setCallbacks(UserController.UserUiCallbacks callbacks) {
        mUserLoginUiCallbacks = (UserController.UserLoginUiCallbacks) callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (R.id.login_find_pwd == v.getId()) {
            ((BaseTourActivity) this.getActivity()).getDisplay().showFindPassword();
        } else if (R.id.login_loginIn == v.getId()) {
            mUserLoginUiCallbacks.loginIn(login_account.getText().toString(), login_pwd.getText().toString());
        }

    }

    private void initView(View view) {
        ((TextView) view.findViewById(R.id.home_setting_phone)).setText(TourApplication.instance().getDaoUser().getUserMobile());
        ((TextView) view.findViewById(R.id.home_setting_user_name)).setText(TourApplication.instance().getDaoUser().getInnerName());
        ((SimpleDraweeView) view.findViewById(R.id.home_setting_user_icon))
                .setImageURI(Uri.parse(TourConfig.instance().getImageRoot() + "/" + TourApplication.instance()
                        .getDaoUser().getInnerHead()));

    }

}
