package com.qieyou.qieyoustore;

import android.content.Intent;

import com.fm.fmlib.Display;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.UserController;
import com.fm.fmlib.dao.User;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class LaunchActivity extends BaseTourActivity implements UserController.AppLaunchUi{
    private UserController.AppLaunchUiCallbacks appLauchCallbacks;
    protected int getContentViewLayoutId() {
        return R.layout.activity_launch;
    }

    protected void handleIntent(Intent intent, Display display) {

    }

    @Override
    public void setCallbacks(UserController.UserUiCallbacks callbacks) {
        appLauchCallbacks = (UserController.AppLaunchUiCallbacks)callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    public void onResume(){
        super.onResume();
        TourApplication.instance().getmMainController().getmUserController().attachUi(this);
        appLauchCallbacks.fetchLaunchProfile();
    }

    public void onPause(){
        TourApplication.instance().getmMainController().getmUserController().detachUi(this);
        super.onPause();
    }

    @Override
    public void launchActivity() {
        User user = TourApplication.instance().getDaoUser();

        if(null != user && user.getIslogin().booleanValue()){

            appLauchCallbacks.loginIn(user.getAccount(),
                    user.getPassword());
        }else{
            this.startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
