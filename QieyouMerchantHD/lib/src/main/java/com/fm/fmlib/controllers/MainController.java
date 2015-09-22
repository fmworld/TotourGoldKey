package com.fm.fmlib.controllers;

import android.util.Log;

import com.fm.fmlib.Display;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.state.ApplicationState;
import com.fm.fmlib.state.HomeState;
import com.fm.fmlib.state.InnState;
import com.fm.fmlib.tasks.InnFetchStoreCardRunnable;
import com.fm.fmlib.tasks.InnFetchManagerTransferRunnable;
import com.fm.fmlib.tasks.LaunchFetchProfileRunnable;
import com.fm.fmlib.tasks.ProfileFetchProOptionsRunnable;
import com.fm.fmlib.tasks.ProfileFetchTagListRunnable;
import com.fm.fmlib.tasks.UserFetchUserInfoRuunable;
import com.fm.fmlib.tour.entity.StoreCardEntity;
import com.fm.fmlib.tour.entity.UserInfoEntity;
import com.fm.fmlib.utils.BackgroundExecutor;
import com.fm.fmlib.models.BackgroundExecutorProvider;
import com.google.common.base.Preconditions;
import com.squareup.otto.Subscribe;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class MainController extends BaseUiController<MainController.MainUi, MainController.MainUiCallbacks> {
    public enum HomeMenu{
        STORE_GALLERY,
        MALL,
        CODE,
        MANAGER,
        SETTING,
        WEB,
        PERSON_INFO,
        MGR_PRO_AE,
        STORE_SUDOKU,
        PRO_DETAIL,
        COMMENTS,
        VERIFY_SUCCESS
    }

    private BackgroundExecutor mExecutor;
    private UserController mUserController;
    private InnController mInnController;
    private ProductController mProductController;
    private ApplicationState mApplicationState;
    public MainController(){
        mApplicationState = new ApplicationState();
        mUserController = new UserController();
        mInnController = new InnController();
        mProductController =new ProductController();
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
        void fetchTagList();
        void fetchProOptions();
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
                    mExecutor.execute(new InnFetchManagerTransferRunnable());
                }
            };
        }

        if(ui instanceof NavigationUi){
            return   new NavigationCallbacks(){
                @Override
                public void fetchUserInfo() {
                    mExecutor.execute(new UserFetchUserInfoTask());
                }

                @Override
                public void fetchStoreInfo() {
                    mExecutor.execute(new InnFetchStoreCardTask());
                }

                @Override
                public void fetchTagList() {
                    mExecutor.execute(new ProfileFetchTagListRunnable());
                }

                @Override
                public void fetchProOptions() {
                    //丽江
                    mExecutor.execute(new ProfileFetchProOptionsRunnable("530700"));
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
        mInnController.setDisplay(display);
        mProductController.setDisplay(display);
    }

    @Override
    protected void onSuspended() {
        mUserController.suspend();
        mInnController.suspend();
        mProductController.suspend();
        TourApplication.instance().getmBus().unregister(this);
        super.onSuspended();
    }

    @Override
    protected void onInited() {
        super.onInited();
        TourApplication.instance().getmBus().register(this);
        mUserController.init();
        mInnController.init();
        mProductController.init();
    }


    public UserController getmUserController() {
        return mUserController;
    }

    public InnController getInnController() {
        return mInnController;
    }

    public ApplicationState getApplicationState() {
        return mApplicationState;
    }

    public ProductController getProductController() {
        return mProductController;
    }

//    public LaunchController getLaunchController() {
//        return mLaunchController;
//    }

    public InnState getInnState() {
        return (InnState)mApplicationState;
    }


    @Subscribe
    public void fetchedManagerPage(HomeState.HomeManagerFetchEvent event){
        Log.v("homemanager", "fetchedManagerPage  " + event.managerUrl);
        this.getDisplay().showManagerPage(event.managerUrl);
    }

    private class InnFetchStoreCardTask extends InnFetchStoreCardRunnable {
        @Override
        public void onSuccess(StoreCardEntity result) {
            for(Ui item : getInnController().getUis()){
                if(item instanceof InnController.InnStoreUi){
                    ((InnController.InnStoreUi)item).initView();
                    break;
                }
            }
        }
    }

    private class UserFetchUserInfoTask extends UserFetchUserInfoRuunable {
        @Override
        public void onSuccess(UserInfoEntity result) {
            for(Ui item : getmUserController().getUis()){
                if(item instanceof UserController.UserSettingUi){
                    ((UserController.UserSettingUi)item).initView();
                    break;
                }
            }
        }
    }

    private class LaunchFetchProfileTask extends LaunchFetchProfileRunnable {

    }


}
