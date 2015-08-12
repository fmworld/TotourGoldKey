package com.qieyou.qieyoustore.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.InnController;
import com.fm.fmlib.controllers.MainController;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.TourWebViewClient;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeManager extends Fragment implements InnController.InnManagerUi, View.OnClickListener {
    private MainController.ManagerCallbacks mManagerCallbacks;
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
        mWebView.setWebViewClient(new TourWebViewClient(this.getActivity()));
        mWebView.setBackgroundColor(this.getResources().getColor(R.color.home_manager_bg));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);
        showManager();
    }

    @Override
    public void onPause() {
        getController().detachUi(this);
        super.onPause();
    }

    InnController getController() {
        return MerchanthdApplication.instance().getmMainController().getInnController();
    }


    @Override
    public void setCallbacks(InnController.InnUiCallbacks callbacks) {

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
    public void showManager() {
        String url = TourApplication.instance().getPropertyDaoPri()
                .getValue(InnController.InnTransfer.managerHome.toString());
        if(null != url){
            mWebView.loadUrl(url);
        }
    }

    @Override
    public void showShareUi(String thumb, String name, String url) {

    }
}
