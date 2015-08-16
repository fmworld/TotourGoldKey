package com.fm.fmlib.controllers;

import android.util.Log;

import com.fm.fmlib.Display;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.state.ApplicationState;
import com.fm.fmlib.state.HomeState;
import com.fm.fmlib.state.InnState;
import com.fm.fmlib.tasks.InnFetchStoreCardRunnable;
import com.fm.fmlib.tasks.InnFetchStoreShareInfoRunnable;
import com.fm.fmlib.tasks.InnFetchManagerTransferRunnable;
import com.fm.fmlib.tasks.UserFetchUserInfoRuunable;
import com.fm.fmlib.tour.bean.ProductInfo;
import com.fm.fmlib.tour.entity.StoreCardEntity;
import com.fm.fmlib.tour.entity.StoreShareEntity;
import com.fm.fmlib.tour.entity.TransferEntity;
import com.fm.fmlib.tour.entity.UserInfoEntity;
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
        PERSON_INFO,
        MGR_PRO_AE
    }

    private BackgroundExecutor mExecutor;
    private UserController mUserController;
    private InnController mInnController;
private ApplicationState mApplicationState;
    public MainController(){
        mApplicationState = new ApplicationState();
        mUserController = new UserController();
        mInnController = new InnController();
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
        void fetchManagerUrl();
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
                public void fetchManagerUrl() {
                    mExecutor.execute(new InnFetchManagerTeansferTask());
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
    }

    @Override
    protected void onSuspended() {
        mUserController.suspend();
        mInnController.suspend();
        TourApplication.instance().getmBus().unregister(this);
        super.onSuspended();
    }

    @Override
    protected void onInited() {
        super.onInited();
        TourApplication.instance().getmBus().register(this);
        mUserController.init();
        mInnController.init();
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



    private class InnFetchManagerTeansferTask extends InnFetchManagerTransferRunnable {
        @Override
        public void onSuccess(TransferEntity result) {
            for(Ui item : getInnController().getUis()){
                if(item instanceof InnController.InnManagerUi){
                    ((InnController.InnManagerUi)item).showManager();
                    break;
                }
            }
        }
    }


}
