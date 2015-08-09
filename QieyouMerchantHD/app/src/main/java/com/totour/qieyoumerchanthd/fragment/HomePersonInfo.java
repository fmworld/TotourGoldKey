package com.totour.qieyoumerchanthd.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fm.fmlib.controllers.UserController;
import com.totour.qieyoumerchanthd.BaseTourActivity;
import com.totour.qieyoumerchanthd.LoginActivity;
import com.totour.qieyoumerchanthd.MerchanthdApplication;
import com.totour.qieyoumerchanthd.R;
import com.totour.qieyoumerchanthd.util.TourRegularUtil;
import com.totour.qieyoumerchanthd.widget.AnimListenFragment;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomePersonInfo extends AnimListenFragment implements UserController.UserLoginUi, View.OnClickListener {
    private UserController.UserLoginUiCallbacks mUserLoginUiCallbacks;
    private EditText login_account;
    private EditText login_pwd;
    private Button login_loginIn;

    public static HomePersonInfo create() {
        HomePersonInfo fragment = new HomePersonInfo();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_person, null);
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

}
