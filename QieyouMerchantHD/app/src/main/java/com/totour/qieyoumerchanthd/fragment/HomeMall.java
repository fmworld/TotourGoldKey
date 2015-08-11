package com.totour.qieyoumerchanthd.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.fm.fmlib.controllers.MainController;
import com.totour.qieyoumerchanthd.MerchanthdApplication;
import com.totour.qieyoumerchanthd.R;
import com.totour.qieyoumerchanthd.util.TourWebViewClient;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeMall extends Fragment implements MainController.ManagerUi, View.OnClickListener {
    private MainController.ManagerCallbacks mManagerCallbacks;
    private EditText login_account;
    private EditText login_pwd;
    private Button login_loginIn;
    private WebView mWebView;
    private boolean firstResume;

    public HomeMall() {
        firstResume = true;
    }

    public static HomeMall create() {
        HomeMall fragment = new HomeMall();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_mall, null);
//        SimpleDraweeView home_person_user_icon = (SimpleDraweeView) view.findViewById(R.id.home_person_user_icon);
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
