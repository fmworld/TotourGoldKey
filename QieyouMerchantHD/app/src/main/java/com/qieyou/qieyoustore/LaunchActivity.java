package com.qieyou.qieyoustore;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.fm.fmlib.Display;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.UserController;
import com.qieyou.qieyoustore.ui.widget.DownLoadDialog;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class LaunchActivity extends BaseTourActivity implements UserController.AppLaunchUi {
    private DownLoadDialog mDownLoadDialog;
    private UserController.AppLaunchUiCallbacks appLauchCallbacks;

    protected int getContentViewLayoutId() {
        return R.layout.activity_launch;
    }

    protected void handleIntent(Intent intent, Display display) {

    }

    @Override
    public void setCallbacks(UserController.UserUiCallbacks callbacks) {
        appLauchCallbacks = (UserController.AppLaunchUiCallbacks) callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    UserController.AppLaunchUiCallbacks getCallbacks() {
        return appLauchCallbacks;
    }

    public void onResume() {
        super.onResume();
        getController().attachUi(this);
        getCallbacks().getAppInfo(this);
    }

    public void onPause() {
        getController().detachUi(this);
        super.onPause();
    }

    UserController getController() {
        return TourApplication.instance().getmMainController().getmUserController();
    }

    @Override
    public void launchActivity() {
        getCallbacks().loginIn();
    }

    @Override
    public void afterCheckWithNoUpdate() {
        getCallbacks().fetchLaunchProfile();
    }

    @Override
    public void showDownLoadUi(Uri uri) {
        //如果有版本可以更新，则在更新提示消失后resume的情况下进入主页
        //{@link onResume()}

        if(null == mDownLoadDialog){
            mDownLoadDialog = new DownLoadDialog(this);
            mDownLoadDialog.setDismissListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getCallbacks().fetchLaunchProfile();
                }
            });
            mDownLoadDialog.showNotify(uri);
        }else{
            Log.v("updatedia", "showDownLoadUi  fetchLaunchProfile");
            getCallbacks().fetchLaunchProfile();
        }
    }
}
