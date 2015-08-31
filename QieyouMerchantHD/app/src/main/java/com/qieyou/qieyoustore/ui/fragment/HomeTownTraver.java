package com.qieyou.qieyoustore.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.controllers.ProductController;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.HomeAcitvity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.qieyou.qieyoustore.util.TourWebViewClient;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeTownTraver extends AnimListenFragment implements View.OnClickListener{
    private WebView webView;

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
                    webView.loadUrl(link);
            }
        });

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        ((HomeAcitvity)getActivity()).selectNavigationItem(MainController.HomeMenu.WEB);
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    ProductController getController() {
        return MerchanthdApplication.instance().getmMainController().getProductController();
    }

    @Override
    public void onClick(View v) {
        if(R.id.web_back == v.getId()){
            ((BaseTourActivity)getActivity()).getDisplay().hideHomeSecondContent();
        }
    }
}
