package com.qieyou.qieyoustore.util;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.state.InnState;
import com.fm.fmlib.utils.StringUtils;
import com.qieyou.qieyoustore.CheckTypeActivity;

import im.yixin.sdk.util.StringUtil;

/**
 * Created by zhoufeng'an on 2015/8/11.
 */
public class TourWebViewClient extends WebViewClient {
    private  final String shareUrl = "qyobj://shop_shared_flag";
    private  final String paymentUrl = "qyobj://order_payment";
    private Activity mContext;

    public TourWebViewClient(Activity context) {
        this.mContext = context;
    }
//    @Override
//    public void onPageStarted(WebView view, String url, Bitmap favicon) {
//
//        // TODO Auto-generated method stub
//        if (url.contains(shareUrl)) {
//            Log.v("homepage", "onPageStarted  "+url);
//        }
//        else{
//            super.onPageStarted(view, url, favicon);
//        }
//
//    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (null != url&&url.contains(shareUrl)) {
            int index = url.indexOf("=");
            if(index >=0){
                String inn_id = url.substring(index+1);
                TourApplication.instance().getmBus().post(new InnState.InnToFetchShareInfoEvent(inn_id));
            }

            return true;
        }

        if(null != url && url.contains(paymentUrl)){
            mContext.startActivity(new Intent(mContext, CheckTypeActivity.class));
        }
        return false;

    }
}