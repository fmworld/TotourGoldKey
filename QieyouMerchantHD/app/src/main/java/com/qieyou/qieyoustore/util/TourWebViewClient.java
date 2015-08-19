package com.qieyou.qieyoustore.util;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.InnController;
import com.fm.fmlib.state.InnState;
import com.fm.fmlib.utils.StringUtils;
import com.qieyou.qieyoustore.CheckTypeActivity;

import im.yixin.sdk.util.StringUtil;

/**
 * Created by zhoufeng'an on 2015/8/11.
 */
public class TourWebViewClient extends WebViewClient {
    private final String shareUrl = "qyobj://shop_shared_flag";
    private final String paymentUrl = "qyobj://order_payment";
    private final String productEditUrl = "qyobj://product_edit_flag";
    private final String productAddUrl = "qyobj://product_add_flag";
    private Activity mContext;
    private InnController.InnManagerUICallbacks callbacks;

    public TourWebViewClient(Activity context) {
        this.mContext = context;
    }

    public TourWebViewClient(Activity context,InnController.InnManagerUICallbacks callbacks) {
        this.mContext = context;
        this.callbacks = callbacks;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (null != url && url.contains(shareUrl)) {
            int index = url.indexOf("=");
            if (index >= 0&& null != callbacks) {
                String inn_id = url.substring(index + 1);
                callbacks.shareStoreInfo(inn_id);
            }

            return true;
        }

        if (null != url && url.contains(paymentUrl)) {
            int index = url.indexOf("=");
            if (index >= 0&& null != callbacks) {
                String order_id = url.substring(index + 1);
                callbacks.showCheckType(order_id);
            }
            return true;
        }

        if (null != url && url.contains(productEditUrl)) {
            int index = url.indexOf("=");
            if (index >= 0&& null != callbacks) {
                String product_id = url.substring(index + 1);
                callbacks.showProdcutEdit(product_id);
            }
            return true;
        }

        if (null != url && url.contains(productAddUrl)) {
            callbacks.showProdcutAdd();
            return true;
        }

        return false;
    }
}