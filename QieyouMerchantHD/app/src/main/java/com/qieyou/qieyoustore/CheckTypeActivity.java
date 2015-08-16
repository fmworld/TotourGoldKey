package com.qieyou.qieyoustore;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.fm.fmlib.Display;
import com.qieyou.qieyoustore.util.TourStringUtil;
import com.qieyou.qieyoustore.util.TourWebViewClient;

/**
 * Created by zhoufeng'an on 2015/8/12.
 */
public class CheckTypeActivity extends BaseTourActivity {
    protected int getContentViewLayoutId() {
        return R.layout.activity_checktype;
    }

    protected void handleIntent(Intent intent, Display display) {
        String url = intent.getStringExtra("url");
        Log.v("check", "url  "+url);
        initView(url);
    }

    private void initView(String url){
        findViewById(R.id.manager_check_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckTypeActivity.this.finish();
            }
        });

        WebView webView = ((WebView)findViewById(R.id.home_mana_check_webv));
        webView.setBackgroundColor(this.getResources().getColor(R.color.home_manager_check_bg));
        webView.setWebViewClient(new TourWebViewClient(this));
        webView.getSettings().setJavaScriptEnabled(true);
        if(!TourStringUtil.isNULLorEmpty(url)){
            webView.loadUrl(url);
        }
    }
}
