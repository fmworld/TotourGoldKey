package com.totour.qieyoumerchanthd.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.controllers.UserController;
import com.totour.qieyoumerchanthd.BaseTourActivity;
import com.totour.qieyoumerchanthd.LoginActivity;
import com.totour.qieyoumerchanthd.MerchanthdApplication;
import com.totour.qieyoumerchanthd.R;
import com.totour.qieyoumerchanthd.util.TourWebViewClient;
import com.totour.qieyoumerchanthd.widget.AnimListenFragment;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeManager extends Fragment implements MainController.ManagerUi, View.OnClickListener {
    private MainController.ManagerCallbacks mManagerCallbacks;
    private EditText login_account;
    private EditText login_pwd;
    private Button login_loginIn;
    private WebView mWebView;
    private boolean firstResume;

    public HomeManager() {
        firstResume = true;
    }

    public static HomeManager create() {
        HomeManager fragment = new HomeManager();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_manager, null);
        mWebView = (WebView) view.findViewById(R.id.home_manager_web);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new TourWebViewClient());
        mWebView.setBackgroundColor(this.getResources().getColor(R.color.home_manager_bg));
//        SimpleDraweeView home_person_user_icon = (SimpleDraweeView) view.findViewById(R.id.home_person_user_icon);
//        home_person_user_icon.setImageURI(Uri.parse("http://img1.fjtv.net/material/news/img/2015/07/4b9db2f97de68cbf05df2517be05db1a.jpg"));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);
        if(firstResume){
            mManagerCallbacks.fetchManagerUrl();
            firstResume =false;
        }
    }

    @Override
    public void onPause() {
        getController().detachUi(this);
        super.onPause();
    }

    MainController getController() {
        return MerchanthdApplication.instance().getmMainController();
    }


    @Override
    public void setCallbacks(MainController.MainUiCallbacks callbacks) {
        mManagerCallbacks = (MainController.ManagerCallbacks) callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
        mManagerCallbacks.fetchManagerUrl();
    }


    @Override
    public void loadUrl(String uri) {
        Log.v("homemanager", "loadUrl  :"+uri);
        mWebView.loadUrl(uri);
    }
}
