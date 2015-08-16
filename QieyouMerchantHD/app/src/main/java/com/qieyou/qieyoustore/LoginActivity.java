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

    public void showCodeCountDown() {
        LoginFindpwdFragment fragment = (LoginFindpwdFragment) getFragmentManager()
                .findFragmentByTag(LoginFindpwdFragment.class.getSimpleName());
        if (null != fragment) {
            fragment.showGetVericodeCountDown();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK) { //
            if (requestCode == TourPicConfig.REQUEST_CODE_PIC_CAMERA) { // 发送照片
                if (data != null) {
                    Log.v("take pic", "" + data);
                    Uri selectedImage = data.getData();

                }

            } else if (requestCode == TourPicConfig.REQUEST_CODE_PIC_LOCAL) { // 发送本地图片
                if (data != null) {
                    Log.v("take pic", "" + data.getData());
                    Uri selectedImage = data.getData();

                }
            }
        }
    }
}
