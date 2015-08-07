package com.totour.qieyoumerchanthd;

import android.content.Intent;
import android.util.Log;

import com.fm.fmlib.Display;
import com.totour.qieyoumerchanthd.fragment.LoginFindpwdFragment;

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

    public void showCodeCountDown() {
        LoginFindpwdFragment fragment = (LoginFindpwdFragment) getFragmentManager()
                .findFragmentByTag(LoginFindpwdFragment.class.getSimpleName());
        if (null != fragment) {
            fragment.showGetVericodeCountDown();
        }
    }
}
