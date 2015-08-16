package com.fm.fmlib.controllers;

import android.os.Bundle;
import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.state.InnState;
import com.fm.fmlib.tasks.InnFetchOrderPmentTransferRunnable;
import com.fm.fmlib.tasks.InnFetchProductInfoRunnable;
import com.fm.fmlib.tasks.InnFetchStoreShareInfoRunnable;
import com.fm.fmlib.tour.bean.ProductInfo;
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
    }

    public interface ProductAeUi extends InnUi{
        void initEditView(ProductInfo info);
        void initAddView();
    }

    public interface InnUiCallbacks{

    }

    public interface InnStoreUiCallbacks extends InnUiCallbacks{
        void fetchInnStore();
    }

    public interface InnProductUICallbacks extends InnUiCallbacks{
        void fetchProductInfo(String product_id);
        void updateProductInfo();
    }

    public interface InnManagerUICallbacks extends InnUiCallbacks{

        void shareStoreInfo(String inn_id);
        void showCheckType(String order_id);

        void showProdcutEdit(String product_id);
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

        if(ui instanceof InnManagerUi){
            return new InnManagerUICallbacks(){


                @Override
                public void shareStoreInfo(String inn_id) {
                    mExecutor.execute(new InnFetchStoreShareInfoRunnable(inn_id));
                }

                @Override
                public void showCheckType(String order_id) {
                    mExecutor.execute(new InnFetchOrderPmentTransferRunnable(order_id));
                }

                @Override
                public void showProdcutEdit(String product_id) {
                    Bundle bundle = new Bundle();
                    bundle.putString("order", product_id);
                    InnController.this.getDisplay().showHomeSecondContent(MainController.HomeMenu.MGR_PRO_AE, bundle);
                }
            };
        }

        if(ui instanceof ProductAeUi){
            return new InnProductUICallbacks(){
                @Override
                public void fetchProductInfo(String product_id) {
                    mExecutor.execute(new InnFetchProductInfoRunnable(product_id));
                }

                @Override
                public void updateProductInfo() {

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
    public void fetchedShareInfo(InnState.InnFetchShareInfoEvent event){
        this.getDisplay().showShareUI(event.thumb, event.name,event.url);
    }

    @Subscribe
    public void showedOrderPaymentType(InnState.InnFetchPaymentTypeEvent event){
        this.getDisplay().showPaymentType(event.paymentUrl);
    }

    @Subscribe
    public void showProductInfo(InnState.InnFetchProductInfoEvent event){
        for(Ui item : getUis()){
            if(item instanceof InnController.ProductAeUi){
                ((InnController.ProductAeUi)item).initEditView(event.info);
                break;
            }
        }
    }
}
