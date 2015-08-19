package com.fm.fmlib.controllers;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.state.UserState;
import com.fm.fmlib.tasks.UserGetVerifyCodeRuunable;
import com.fm.fmlib.tasks.UserLoginInRuunable;
import com.fm.fmlib.tasks.UserSetNewPwdRuunable;
import com.fm.fmlib.utils.BackgroundExecutor;
import com.fm.fmlib.utils.provider.BackgroundExecutorProvider;
import com.squareup.otto.Subscribe;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class UserController extends BaseUiController<UserController.UserUi,UserController.UserUiCallbacks>{
    private BackgroundExecutor mExecutor;
    public interface UserUi extends BaseUiController.Ui<UserUiCallbacks>{

    }

    public interface UserLoginUi extends UserUi{
        void logined();
        void loginFinished();
    }

    public interface UserFindPwdUi extends UserUi{
    }

    public interface UserSettingUi extends UserUi{
        void initView();
    }

    public interface UserUiCallbacks{
    }

    public interface UserLoginUiCallbacks extends UserUiCallbacks{
        void loginIn(String account, String password);
    }

    public interface UserLoginFindPwdCallbacks extends UserUiCallbacks{
        void getVeriCode(String mobile);
        void changePassword(String mobile, String code, String password);
    }

    public UserController() {
        mExecutor = BackgroundExecutorProvider.providerBackgroundExecutor();
    }

    @Override
    protected UserUiCallbacks createUiCallbacks(UserUi ui) {
        if(ui instanceof UserLoginUi) return new UserLoginUiCallbacks() {

            @Override
            public void loginIn(String account, String password) {
                    mExecutor.execute(new UserLoginInTask(account,password));
            }


        };
        else{
            return new UserLoginFindPwdCallbacks(){
                @Override
                public void getVeriCode(String mobile) {
                    mExecutor.execute(new UserGetVerifyCodeRuunable(mobile));
//                    mExecutor.execute(new UserLoginInRuunable("18612540330","qieyou"));
                }

                @Override
                public void changePassword(String mobile, String code, String password) {
                    mExecutor.execute(new UserSetNewPwdRuunable(mobile, code, password));
                }
            };
        }
    }

    @Override
    protected void onInited() {
        super.onInited();
        TourApplication.instance().getmBus().register(this);
    }

    @Override
    protected void onSuspended() {
        TourApplication.instance().getmBus().unregister(this);
        super.onSuspended();
    }

    @Subscribe
    public void uiLoginSuccessed(UserState.UserLoginExecutedEvent event) {
        Log.v("totour0888", "loginExecuted  " + event.callingId + " name " + event.name + "  pwd  " + event.pwd);
//        this.getDisplay().showHomePage();
        for(Ui item : getUis()){
            if(item instanceof UserLoginUi){
                ((UserLoginUi)item).logined();
                break;
            }
        }
    }


    @Subscribe
    public void accessVerifyCode(UserState.UserLoginAccessCodeEvent event){
        Log.v("totour0888", "UserLoginAccessCodeEvent  ");
        this.getDisplay().showVeriCodeCountdown();
    }

    @Subscribe
    public void resetPasswordSuccessed(UserState.UserResetPasswordEvent event){
        this.getDisplay().showLogin();
    }

    private class UserLoginInTask extends UserLoginInRuunable{
        public UserLoginInTask(String name, String pwd){
            super(name, pwd);
        }
        public void onFinished() {
                for(Ui item : getUis()){
                    if(item instanceof UserLoginUi){
                        ((UserLoginUi)item).loginFinished();
                        break;
                    }
                }
        }
    }
}
