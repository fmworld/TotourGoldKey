package com.totour.qieyoumerchanthd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.fm.fmlib.Display;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.utils.DisplayUtil;
import com.google.common.base.Preconditions;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class BaseTourActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getSimpleName();
    private MainController mMainController;
    private Display mDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Request Progress Bar in Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.v(TAG, "density "+DisplayUtil.getDensity(this));
        super.onCreate(savedInstanceState);

        setContentView(getContentViewLayoutId());

        mMainController = MerchanthdApplication.instance().getmMainController();
//        mDisplay = new AndroidDisplay(this, mDrawerLayout);
        mDisplay = new AndroidDisplay(this);
        handleIntent(getIntent(), getDisplay());
    }
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent, getDisplay());
    }

    protected void handleIntent(Intent intent, Display display) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainController.attachDisplay(mDisplay);
//        mMainController.setHostCallbacks(this);
        mMainController.init();
    }
    @Override
    protected void onPause() {
        mMainController.suspend();
//      mMainController.setHostCallbacks(null);
        mMainController.detachDisplay(mDisplay);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisplay = null;
    }


    public Display getDisplay() {
        return mDisplay;
    }

}
