package com.fm.fmlib.controllers;

import android.util.Log;

import com.fm.fmlib.Display;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.state.HomeState;
import com.fm.fmlib.tasks.InnFetchStoreInfoRunnable;
import com.fm.fmlib.tasks.TrasInnManagerRunnable;
import com.fm.fmlib.tasks.UserFetchUserInfoRuunable;
import com.fm.fmlib.utils.BackgroundExecutor;
import com.fm.fmlib.utils.provider.BackgroundExecutorProvider;
import com.google.common.base.Preconditions;
import com.squareup.otto.Subscribe;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class MainController extends BaseUiController<MainController.MainUi, MainController.MainUiCallbacks> {
    public enum HomeMenu{
        STORE,
        MALL,
        CODE,
        MANAGER,
        SETTING,
        PERSON_INFO
    }
    private BackgroundExecutor mExecutor;
    private UserController mUserController;

    public MainController(){
        mUserController = new UserController();
        mExecutor = BackgroundExecutorProvider.providerBackgroundExecutor();
    }

    public interface MainUi extends BaseUiController.Ui<MainUiCallbacks>{

    }

    public interface ManagerUi extends MainUi{
        void loadUrl(String uri);
    }

    public interface NavigationUi extends MainUi{
        void fetchUserInfo();
//        void loadUrl(String uri);
    }

    public interface MainUiCallbacks{

    }

    public interface  NavigationCallbacks extends  MainUiCallbacks{
        void fetchUserInfo();
        void fetchStoreInfo();
    }
    public interface ManagerCallbacks extends MainUiCallbacks{
        void fetchManagerUrl();
    }

    @Override
    protected MainUiCallbacks createUiCallbacks(MainUi ui) {
        if(ui instanceof ManagerUi){
            return new ManagerCallbacks(){
                @Override
                public void fetchManagerUrl() {
                    mExecutor.execute(new TrasInnManagerRunnable());
                }
            };
        }

        if(ui instanceof NavigationUi){
            return   new NavigationCallbacks(){
                @Override
                public void fetchUserInfo() {
                    mExecutor.execute(new UserFetchUserInfoRuunable());
                }

                @Override
                public void fetchStoreInfo() {
                    mExecutor.execute(new InnFetchStoreInfoRunnable());
                }
            };
        }
        return null;
    }

    public void attachDisplay(Display display) {
        Preconditions.checkNotNull(display, "display is null");
        Preconditions.checkState(getDisplay() == null, "we currently have a display");
        setDisplay(display);
    }

    public void detachDisplay(Display display) {
        Preconditions.checkNotNull(display, "display is null");
        Preconditions.checkState(getDisplay() == display, "display is not attached");

        setDisplay(null);
        mUserController.setDisplay(null);
    }

    @Override
    protected void setDisplay(Display display) {
        super.setDisplay(display);
        mUserController.setDisplay(display);
    }

    @Override
    protected void onSuspended() {
        mUserController.suspend();
        TourApplication.instance().getmBus().unregister(this);
        super.onSuspended();
    }

    @Override
    protected void onInited() {
        super.onInited();
        TourApplication.instance().getmBus().register(this);
        mUserController.init();
    }


    public UserController getmUserController() {
        return mUserController;
    }

    @Subscribe
    public void fetchedManagerPage(HomeState.HomeManagerFetchEvent event){
        Log.v("homemanager", "fetchedManagerPage  " + event.managerUrl);
        this.getDisplay().showManagerPage(event.managerUrl);
    }

}
