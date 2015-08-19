package com.fm.fmlib.controllers;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.tasks.ProductFetchListByTagRunnable;
import com.fm.fmlib.utils.BackgroundExecutor;
import com.fm.fmlib.utils.provider.BackgroundExecutorProvider;
import com.squareup.otto.Subscribe;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class ProductController extends BaseUiController<ProductController.ProductUi, ProductController.ProductUiCallbacks> {
    private BackgroundExecutor mExecutor;

    public ProductController(){
        mExecutor = BackgroundExecutorProvider.providerBackgroundExecutor();
    }
    public interface ProductUi extends BaseUiController.Ui<ProductUiCallbacks> {
        void updateTabBar();
    }

    public interface StoreUi extends ProductUi {
    }


    public interface ProductUiCallbacks {
    }

    public interface ProductStoreCallbacks extends ProductUiCallbacks {
        void fetchProBre(String items);

    }

    @Override
    protected ProductUiCallbacks createUiCallbacks(ProductUi ui) {
        if (ui instanceof StoreUi) {
            return new ProductStoreCallbacks() {

                @Override
                public void fetchProBre(String items) {
                    mExecutor.execute(new ProductFetchListByTagRunnable(items));
                }
            };
        }
        else {
            return null;
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
    public void fetchProductBre(ProductState.InnFetchProductBreEvent event){
        Log.v("tour0888", "fetchProductBre"+ event.infos.size());
        this.getDisplay().showProductBre(event.infos);
    }

    @Subscribe
    public void fetTaglist(ProductState.ProductFetchTagListEvent event){
        for(Ui item : getUis()){
            if(item instanceof StoreUi){
                ((StoreUi)item).updateTabBar();
                break;
            }
        }
    }
}
