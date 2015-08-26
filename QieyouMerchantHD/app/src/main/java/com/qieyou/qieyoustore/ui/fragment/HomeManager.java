package com.qieyou.qieyoustore.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.InnController;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.qieyou.qieyoustore.util.TourWebViewClient;
import com.qieyou.qieyoustore.ui.widget.TourFragment;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeManager extends AnimListenFragment implements InnController.InnManagerUi, View.OnClickListener {
    private InnController.InnManagerUICallbacks mManagerCallbacks;
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
        mWebView.setBackgroundColor(this.getResources().getColor(R.color.home_manager_bg));
        this.setAnimationListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (null != mManagerCallbacks) {
                    mWebView.setWebViewClient(new TourWebViewClient(HomeManager.this.getActivity(), mManagerCallbacks));
                    showManager();
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        getController().attachUi(this);
        super.onResume();
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
        mManagerCallbacks = (InnController.InnManagerUICallbacks)callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
    }



    @Override
    public void showManager() {
        String url = TourApplication.instance().getDaoProperty()
                .getValue(InnController.InnTransfer.managerHome.toString());
        if(null != url){
            mWebView.loadUrl(url);
        }
    }

}
