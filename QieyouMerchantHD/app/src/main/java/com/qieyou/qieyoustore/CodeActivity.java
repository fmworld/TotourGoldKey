package com.qieyou.qieyoustore;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.fm.fmlib.Display;
import com.fm.fmlib.controllers.MainController;
import com.qieyou.qieyoustore.util.TourStringUtil;
import com.qieyou.qieyoustore.util.TourWebViewClient;

/**
 * Created by zhoufeng'an on 2015/8/12.
 */
public class CodeActivity extends BaseTourActivity {
    protected int getContentViewLayoutId() {
        return R.layout.activity_code;
    }

    protected void handleIntent(Intent intent, Display display) {
        this.getDisplay().showVerifyCode(MainController.HomeMenu.CODE,null);
    }
}
