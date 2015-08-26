package com.qieyou.qieyoustore.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.controllers.ProductController;
import com.fm.fmlib.dao.LaunchProfile;
import com.fm.fmlib.dao.Product;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.utils.DisplayUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liangfeizc.RubberIndicator;
import com.qieyou.qieyoustore.Adapter.MallAddAdapter;
import com.qieyou.qieyoustore.Adapter.MallProductAdapter;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.qieyou.qieyoustore.ui.widget.MallCITabBar;
import com.qieyou.qieyoustore.ui.widget.MallFilterPopWindow;
import com.qieyou.qieyoustore.ui.widget.MallTabBar;
import com.qieyou.qieyoustore.ui.widget.TourWaveRefeshLayout;
import com.qieyou.qieyoustore.util.TourWebViewClient;

import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeTownTraver extends AnimListenFragment implements ProductController.MallUi, View.OnClickListener{
    private WebView webView;
    ProductController.ProductMallCallbacks mallCallbacks;

    public HomeTownTraver() {
    }

    public static HomeTownTraver create() {
        HomeTownTraver fragment = new HomeTownTraver();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_home_town_traver, null);
//        ((TextView)view.findViewById(R.id.navi_web_title)).setText();
       ((TextView)view.findViewById(R.id.web_back)).setOnClickListener(this);
        final String link = (String)getArguments().get("link");
        webView = ((WebView)view.findViewById(R.id.navi_web_view));
        webView.setWebViewClient(new TourWebViewClient(this.getActivity()));
        webView.setBackgroundColor(this.getResources().getColor(R.color.home_manager_check_bg));
        webView.getSettings().setJavaScriptEnabled(true);
        this.setAnimationListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (null != mallCallbacks) {
                    webView.loadUrl(link);
                }
            }
        });

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

    ProductController getController() {
        return MerchanthdApplication.instance().getmMainController().getProductController();
    }

    @Override
    public void setCallbacks(ProductController.ProductUiCallbacks callbacks) {
        mallCallbacks = (ProductController.ProductMallCallbacks) callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void refreshProductList(List<Product> products) {

    }

    @Override
    public void updateTabBar() {

    }

    @Override
    public void onClick(View v) {
        if(R.id.web_back == v.getId()){
            ((BaseTourActivity)getActivity()).getDisplay().hideHomeSecondContent();
        }
    }
}
