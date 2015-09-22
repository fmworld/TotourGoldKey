package com.fm.fmlib.controllers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.User;
import com.fm.fmlib.models.component.DaggerStateComponent;
import com.fm.fmlib.models.component.StateComponent;
import com.fm.fmlib.models.provider.StateProvider;
import com.fm.fmlib.state.UserState;
import com.fm.fmlib.tasks.LaunchFetchProfileRunnable;
import com.fm.fmlib.tasks.UserCommitFeedbackRuunable;
import com.fm.fmlib.tasks.UserGetVerifyCodeRuunable;
import com.fm.fmlib.tasks.UserLoginInRuunable;
import com.fm.fmlib.tasks.UserLoginOutRuunable;
import com.fm.fmlib.tasks.UserFindPwdRuunable;
import com.fm.fmlib.tasks.UserSetNewPwdRuunable;
import com.fm.fmlib.tasks.UtilGetAppInfoRunnable;
import com.fm.fmlib.tour.entity.AppInfoEntity;
import com.fm.fmlib.tour.entity.GetVeriCodeEntity;
import com.fm.fmlib.tour.entity.LaunchProfileEntity;
import com.fm.fmlib.tour.entity.LoginEntity;
import com.fm.fmlib.tour.entity.LoginOutEntity;
import com.fm.fmlib.tour.entity.LoginResetPwdEntity;
import com.fm.fmlib.tour.entity.StateEntity;
import com.fm.fmlib.utils.BackgroundExecutor;
import com.fm.fmlib.models.BackgroundExecutorProvider;
import com.fm.fmlib.utils.PackageInfoUtl;
import com.fm.fmlib.utils.TelephoneUtil;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class UserController extends BaseUiController<UserController.UserUi, UserController.UserUiCallbacks> {
    private BackgroundExecutor mExecutor;
    @Inject
    UserState userState;

    public UserController() {
        mExecutor = BackgroundExecutorProvider.providerBackgroundExecutor();
        StateComponent stateComponent = DaggerStateComponent
                .builder().stateProvider(new StateProvider()).build();
        userState = stateComponent.provideUserState();
    }

    public interface UserUi extends BaseUiController.Ui<UserUiCallbacks> {

    }

    public interface AppLaunchUi extends UserUi {
        void launchActivity();
        void afterCheckWithNoUpdate();
        void showDownLoadUi(Uri uri);
    }

    public interface UserLoginUi extends UserUi {
        void logined();

        void loginFinished();
    }

    public interface UserFindPwdUi extends UserUi {
//        void showCuntDown();
    }

    public interface SetFeedbackUi extends UserUi {
        void submitFeedbackSuccess();
    }

    public interface SetChangePwdUi extends UserUi {
        void passwordChanged(String pwd);

        void showCuntDown();
    }

    public interface UserSettingUi extends UserUi {
        void initView();

        void loginOut();
    }

    public interface UserUiCallbacks {
    }

    public interface UserLoginUiCallbacks extends UserUiCallbacks {
        void loginIn(String account, String password);
    }

    public interface SettingUiCallbacks extends UserUiCallbacks {
        void loginOut();
    }

    public interface AppLaunchUiCallbacks extends UserUiCallbacks {
        void fetchLaunchProfile();
        void getAppInfo(Context context);
        void loginIn();
    }

    public interface UserLoginFindPwdCallbacks extends UserUiCallbacks {
        void getVeriCode(String mobile);

        void changePassword(String mobile, String code, String password);
    }

    public interface SetChangePwdCallbacks extends UserLoginFindPwdCallbacks {
    }

    public interface SetFeedbackCallbacks extends UserUiCallbacks {
        void submitFeedback(String note, String version);

    }



    @Override
    protected UserUiCallbacks createUiCallbacks(UserUi ui) {
        if (ui instanceof UserLoginUi) {
            return new UserLoginUiCallbacks() {

                @Override
                public void loginIn(String account, String password) {
                    mExecutor.execute(new UserLoginInTask(account, password));
                }
            };
        } else if (ui instanceof AppLaunchUi) {
            return new AppLaunchUiCallbacks() {
                @Override
                public void fetchLaunchProfile() {
                    mExecutor.execute(new LaunchFetchProfileTask());
                }

                @Override
                public void getAppInfo(Context context) {
                    mExecutor.execute(new UtilGetAppInfoTask(context, TelephoneUtil.getImei()));
                }

                @Override
                public void loginIn() {
                    User user = userState.getDefaultLoginUser();

                    if (null == user) {
                        getDisplay().showLoginActivity();
                    } else {
                        mExecutor.execute(
                                new UserLoginInTask(user.getAccount(), user.getPassword()));
                    }
                }
            };
        } else if (ui instanceof SetChangePwdUi) {
            return new SetChangePwdCallbacks() {
                @Override
                public void getVeriCode(String mobile) {
                    mExecutor.execute(new SetChangePwdGetVeryCodetask(mobile));
                }

                @Override
                public void changePassword(String mobile, String code, String password) {
                    mExecutor.execute(new SetChangePwdTask(code, password));
                }
            };
        } else if (ui instanceof SetFeedbackUi) {
            return new SetFeedbackCallbacks() {
                @Override
                public void submitFeedback(String note, String version) {
                    mExecutor.execute(new UserCommitFeedbackTask(note, version));
                }
            };
        } else if (ui instanceof UserSettingUi) {
            return new SettingUiCallbacks() {
                @Override
                public void loginOut() {
                    mExecutor.execute(new UserLoginOutTask());
                }
            };
        } else {
            return new UserLoginFindPwdCallbacks() {
                @Override
                public void getVeriCode(String mobile) {
                    mExecutor.execute(new UserGetVerifyCodeRuunable(mobile));
//                    mExecutor.execute(new UserLoginInRuunable("18612540330","qieyou"));
                }

                @Override
                public void changePassword(String mobile, String code, String password) {
                    mExecutor.execute(new UserFindPwdRuunable(mobile, code, password));
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


//    @Subscribe
//    public void uiLoginSuccessed(UserState.UserLoginExecutedEvent event) {
//        Log.v("totour0888", "loginExecuted  " + event.callingId + " name " + event.name + "  pwd  " + event.pwd);
////        this.getDisplay().showHomePage();
//        for(Ui item : getUis()){
//            if(item instanceof UserLoginUi){
//                ((UserLoginUi)item).logined();
//                break;
//            }
//        }
//    }


    @Subscribe
    public void accessVerifyCode(UserState.UserLoginAccessCodeEvent event) {
        Log.v("totour0888", "UserLoginAccessCodeEvent  ");
        this.getDisplay().showVeriCodeCountdown();
    }

    @Subscribe
    public void resetPasswordSuccessed(UserState.UserResetPasswordEvent event) {
        this.getDisplay().showLogin();
    }

    private class UserLoginInTask extends UserLoginInRuunable {
        public UserLoginInTask(String name, String pwd) {
            super(name, pwd);
        }

        public void onFinished() {
            for (Ui item : getUis()) {
                if (item instanceof UserLoginUi) {
                    ((UserLoginUi) item).loginFinished();
                    break;
                }
            }
        }

        @Override
        public void onSuccess(LoginEntity result) {
            getDisplay().showHomePage();
        }
    }

    private class LaunchFetchProfileTask extends LaunchFetchProfileRunnable {
        @Override
        public void onSuccess(LaunchProfileEntity result) {
            for (Ui item : getUis()) {
                if (item instanceof AppLaunchUi) {
                    ((AppLaunchUi) item).launchActivity();
                    break;
                }
            }
        }

        public void onSuccessBadCode(int code, String errorInfo) {
            Toast.makeText(TourApplication.instance().getApplicationContext(), errorInfo, Toast.LENGTH_SHORT).show();
        }

        public void onError(RetrofitError be) {

        }
    }

    private class SetChangePwdTask extends UserSetNewPwdRuunable {
        public SetChangePwdTask(String code, String pwd) {
            super(code, pwd);
        }

        @Override
        public void onSuccess(LoginResetPwdEntity result) {
            for (Ui item : getUis()) {
                if (item instanceof SetChangePwdUi) {
                    ((SetChangePwdUi) item).passwordChanged(this.pwd);
                    break;
                }
            }
        }
    }

    private class SetChangePwdGetVeryCodetask extends UserGetVerifyCodeRuunable {
        public SetChangePwdGetVeryCodetask(String mobile) {
            super(mobile);
        }

        public void onSuccess(GetVeriCodeEntity result) {
            for (Ui item : getUis()) {
                if (item instanceof SetChangePwdUi) {
                    ((SetChangePwdUi) item).showCuntDown();
                    break;
                }
            }
        }
    }

    private class UserCommitFeedbackTask extends UserCommitFeedbackRuunable {
        public UserCommitFeedbackTask(String note, String version) {
            super(note, version);
        }

        public void onSuccess(StateEntity result) {
            for (Ui item : getUis()) {
                if (item instanceof SetFeedbackUi) {
                    ((SetFeedbackUi) item).submitFeedbackSuccess();
                    break;
                }
            }
        }
    }

    private class UserLoginOutTask extends UserLoginOutRuunable {
        public UserLoginOutTask() {
        }

        public void onSuccessInBackground(LoginOutEntity result) {
            userState.loginOut();
        }

        public void onSuccess(LoginOutEntity result) {
            for (Ui item : getUis()) {
                if (item instanceof UserSettingUi) {
                    ((UserSettingUi) item).loginOut();
                    break;
                }
            }
        }

    }

    private class UtilGetAppInfoTask extends UtilGetAppInfoRunnable {
        public UtilGetAppInfoTask(Context context, String imei) {
            super(context, imei);
        }

        public void onSuccess(AppInfoEntity result) {
            int vercode = result.msg.app.vercode;
            if(vercode > PackageInfoUtl.getVersionCode(context)){
                for (Ui item : getUis()) {
                    if (item instanceof AppLaunchUi) {
                        ((AppLaunchUi) item).showDownLoadUi(Uri.parse(result.msg.app.link));
                        break;
                    }
                }
            }else{
                for (Ui item : getUis()) {
                    if (item instanceof AppLaunchUi) {
                        ((AppLaunchUi) item).afterCheckWithNoUpdate();
                        break;
                    }
                }
            }
        }
    }


}
