package com.fm.fmlib.controllers;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.state.UserState;
import com.fm.fmlib.tasks.UserFindPwdRuunable;
import com.fm.fmlib.tasks.UserLoginInRuunable;
import com.fm.fmlib.tasks.UserLoginOutRuunable;
import com.fm.fmlib.utils.BackgroundExecutor;
import com.fm.fmlib.utils.provider.BackgroundExecutorProvider;
import com.fm.fmlib.utils.provider.Networkprovider;
import com.squareup.otto.Subscribe;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class UserController extends BaseUiController<UserController.UserUi,UserController.UserUiCallbacks>{
    private BackgroundExecutor mExecutor;
    public interface UserUi extends BaseUiController.Ui<UserUiCallbacks>{

    }
    public interface UserUiCallbacks{
        void loginIn(String account, String password);
        void loginOut();
        void findPwd(String mobile);
    }

    public UserController() {
        mExecutor = BackgroundExecutorProvider.providerBackgroundExecutor();
    }

    @Override
    protected UserUiCallbacks createUiCallbacks(UserUi ui) {
        return new UserUiCallbacks(){

            @Override
            public void loginIn(String account, String password) {
                mExecutor.execute(new UserLoginInRuunable(account,password));
            }

            @Override
            public void loginOut() {
                mExecutor.execute(new UserLoginOutRuunable());
            }

            @Override
            public void findPwd(String mobile) {
                mExecutor.execute(new UserFindPwdRuunable(mobile));
            }
        };
    }

    public void atachUI() {
        FmApplication.instance().getmBus().register(this);
    }

    public void unatachUI() {
        FmApplication.instance().getmBus().unregister(this);
    }

    @Subscribe
    public void uiLoginSuccessed(UserState.UserLoginExecutedEvent event) {
        Log.v("totour0888", "loginExecuted  " + event.callingId + " name " + event.name + "  pwd  " + event.pwd);
    }


}
