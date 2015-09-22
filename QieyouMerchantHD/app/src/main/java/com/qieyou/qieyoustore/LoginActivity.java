package com.qieyou.qieyoustore;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.fm.fmlib.Display;
import com.qieyou.qieyoustore.ui.fragment.LoginFindpwdFragment;
import com.qieyou.qieyoustore.util.TourPicConfig;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class LoginActivity extends BaseTourActivity {

    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    protected void handleIntent(Intent intent, Display display) {
        display.showLogin();
    }
}
