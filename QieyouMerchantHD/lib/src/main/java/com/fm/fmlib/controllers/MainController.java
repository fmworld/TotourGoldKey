package com.fm.fmlib.controllers;

import com.fm.fmlib.Display;
import com.fm.fmlib.TourApplication;
import com.google.common.base.Preconditions;

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

    private UserController mUserController;

    public MainController(){
        mUserController = new UserController();

    }

    public interface MainUi extends BaseUiController.Ui<MainUiCallbacks>{

    }
    public interface MainUiCallbacks{

    }

    @Override
    protected MainUiCallbacks createUiCallbacks(MainUi ui) {
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
}
