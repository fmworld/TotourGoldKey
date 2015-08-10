package com.totour.qieyoumerchanthd.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
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
        view.findViewById(R.id.home_psn_serve_phone).setOnClickListener(this);
        SimpleDraweeView home_person_user_icon = (SimpleDraweeView)view.findViewById(R.id.home_person_user_icon);
//        home_person_user_icon.setImageURI(Uri.parse("http://img1.fjtv.net/material/news/img/2015/07/4b9db2f97de68cbf05df2517be05db1a.jpg"));
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
        } else if(R.id.home_psn_serve_phone == v.getId()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            final String phone = ((TextView)v).getText().toString();
            builder.setMessage(phone).setPositiveButton(this.getString(R.string.positive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));
                    HomePersonInfo.this.startActivity(intent);
                }
            }).setNegativeButton(this.getString(R.string.navitive),null).show();

        }
    }


}
