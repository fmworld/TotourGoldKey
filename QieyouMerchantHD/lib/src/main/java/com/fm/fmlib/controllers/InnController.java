package com.fm.fmlib.controllers;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.state.InnState;
import com.fm.fmlib.tasks.InnFetchStoreShareInfoRunnable;
import com.fm.fmlib.utils.BackgroundExecutor;
import com.fm.fmlib.utils.provider.BackgroundExecutorProvider;
import com.squareup.otto.Subscribe;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class InnController extends BaseUiController<InnController.InnUi,InnController.InnUiCallbacks>{
    public enum InnType{
        inn
    }

    public enum InnTransfer{
        managerHome
    }

    private BackgroundExecutor mExecutor;
    public interface InnUi extends BaseUiController.Ui<InnUiCallbacks>{

    }


    public interface InnStoreUi extends InnUi{
        void initView();
    }

    public interface InnManagerUi extends InnUi{
        void showManager();
        void showShareUi(String thumb, String name, String url);
    }

    public interface InnUiCallbacks{
    }

    public interface InnStoreUiCallbacks extends InnUiCallbacks{
        void fetchInnStore();
    }

    public InnController() {
        mExecutor = BackgroundExecutorProvider.providerBackgroundExecutor();
    }

    @Override
    protected InnUiCallbacks createUiCallbacks(InnUi ui) {
        if(ui instanceof InnStoreUi){
            return new InnStoreUiCallbacks(){
                @Override
                public void fetchInnStore() {
//                    mExecutor.execute(new InnStoreFtechTask());
                }
            };
        }
        return null;
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
    public void fetchShareInfo(InnState.InnToFetchShareInfoEvent event){
        Log.v("tourshare", "fetchShareInfo");
        mExecutor.execute(new InnFetchStoreShareInfoRunnable(event.inn_id));
    }

    @Subscribe
    public void fetchedShareInfo(InnState.InnFetchShareInfoEvent event){
        this.getDisplay();
        this.getDisplay().showShareUI(event.thumb, event.name,event.url);
    }
}
