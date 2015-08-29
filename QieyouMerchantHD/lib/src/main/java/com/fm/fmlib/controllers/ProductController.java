package com.fm.fmlib.controllers;

import android.util.Log;
import android.widget.Toast;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.Product;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.tasks.ProFetchShareInfoRunnable;
import com.fm.fmlib.tasks.ProductChangeShelfStateRunnable;
import com.fm.fmlib.tasks.ProductFetchCodeVeriStateRunnable;
import com.fm.fmlib.tasks.ProductFetchCommentsRunnable;
import com.fm.fmlib.tasks.ProductFetchMallProductRunnable;
import com.fm.fmlib.tasks.ProductFetchProDetailRunnable;
import com.fm.fmlib.tasks.ProductFetchStoreProductRunnable;
import com.fm.fmlib.tasks.ProductFetchSubmitTransferRunnable;
import com.fm.fmlib.tour.bean.CodeInfo;
import com.fm.fmlib.tour.bean.ProComment;
import com.fm.fmlib.tour.bean.ProductDetail;
import com.fm.fmlib.tour.entity.CodeInfoEntity;
import com.fm.fmlib.tour.entity.ProCommentsEntity;
import com.fm.fmlib.tour.entity.ProductDetailEntity;
import com.fm.fmlib.tour.entity.ProductsEntity;
import com.fm.fmlib.tour.entity.StateEntity;
import com.fm.fmlib.tour.entity.StoreShareEntity;
import com.fm.fmlib.tour.entity.TransferEntity;
import com.fm.fmlib.utils.BackgroundExecutor;
import com.fm.fmlib.utils.provider.BackgroundExecutorProvider;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class ProductController extends BaseUiController<ProductController.ProductUi, ProductController.ProductUiCallbacks> {
    private BackgroundExecutor mExecutor;

    public ProductController() {
        mExecutor = BackgroundExecutorProvider.providerBackgroundExecutor();
    }

    public interface ProductUi extends BaseUiController.Ui<ProductUiCallbacks> {
        void updateTabBar();
    }

    public interface StoreUi extends ProductUi {

    }

    public interface ProStateUi extends ProductUi {
        void refreshStateChange();
    }

    public interface MallUi extends ProStateUi {
        void refreshProductList(List<Product> products);

    }

    public interface DetailUi extends ProStateUi {
        void refreshProDetail(ProductDetail detail);
    }

    public interface CommentsUi extends ProductUi {
        void refreshComment(List<ProComment> comments);
    }

    public interface CodeUi extends ProductUi {
        void showBadCodeMsg(String msg);
        void codeSuccessed(CodeInfo codeInfo);
    }

    public interface ProductUiCallbacks {
    }

    public interface ProductStoreCallbacks extends ProductUiCallbacks {
        void fetchProBre(String items);
    }

    public interface ProductCommentsCallbacks extends ProductUiCallbacks {
        void fetchProComments(String proId, String page, String perPage);
    }

    public interface ProductCodeCallbacks extends ProductUiCallbacks {
        void verifyCode(String code);
    }

    public interface ProductDetailCallbacks extends ProductUiCallbacks {
        void fetchDetail(String item);
        void fetchShareInfo(String id);
    }

    public interface ProductMallCallbacks extends ProductUiCallbacks {
        void fetchProducts(String page, String perpage, String city, String cid, String ccid, String dest, String sort);
    }

    @Override
    protected ProductUiCallbacks createUiCallbacks(ProductUi ui) {
        if (ui instanceof StoreUi) {
            return new ProductStoreCallbacks() {

                @Override
                public void fetchProBre(String items) {
                    mExecutor.execute(new ProductFetchStoreProductRunnable(items));
                }
            };
        } else if (ui instanceof MallUi) {
            return new ProductMallCallbacks() {

                @Override
                public void fetchProducts(String page, String perpage, String city, String cid, String ccid, String dest, String sort) {
                    mExecutor.execute(new ProductFetchMallProductTask(page, perpage, city, cid, ccid, dest, sort));
                }
            };
        }else if (ui instanceof DetailUi) {
            return new ProductDetailCallbacks() {

                @Override
                public void fetchDetail(String item) {
                    mExecutor.execute(new ProductFetchProDetailTask(item));
                }

                @Override
                public void fetchShareInfo(String id) {
                    mExecutor.execute(new ProductFetchShareInfoTask(id));
                }
            };
        } else if(ui instanceof CodeUi){
            return  new ProductCodeCallbacks(){
                @Override
                public void verifyCode(String code) {
                    mExecutor.execute(new ProCodeVerifyTask(code));
                }
            };
        } else if(ui instanceof CommentsUi){
            return  new ProductCommentsCallbacks(){
                @Override
                public void fetchProComments(String proId, String page, String perPage) {
                    mExecutor.execute(new ProductFetchCommentsTask(proId, page, perPage));
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
    public void fetchProductBre(ProductState.InnFetchProductBreEvent event) {
        Log.v("tour0888", "fetchProductBre" + event.infos.size());
        this.getDisplay().showProductBre(event.infos);
    }

    @Subscribe
    public void fetTaglist(ProductState.ProductFetchTagListEvent event) {
        for (Ui item : getUis()) {
            if (item instanceof StoreUi) {
                ((StoreUi) item).updateTabBar();
                break;
            }
        }
    }

    @Subscribe
    public void getProductSubmitUrl(ProductState.ProductFetchSubmitUrlEvent event) {
        mExecutor.execute(new ProductFetchSubmitTransferTask(event.item));
    }

    @Subscribe
    public void notifyTipStateChanged(ProductState.ProductTipStateChangeEvent event){
        TourApplication.instance().getDaoProperty().saveProperty(ProductState.Tip, event.state);
        for(Ui item : getUis()){
            if(item instanceof ProductController.ProStateUi){
                ((ProductController.ProStateUi)item).refreshStateChange();
            }
        }
    }

    @Subscribe
    public void changeProShelfState(ProductState.ProductChangeShelfStateEvent event){
        mExecutor.execute(new ProductChangeShelfStateTask(event.tag_id,event.pro_id,event.state));
    }

    private class ProductFetchSubmitTransferTask extends ProductFetchSubmitTransferRunnable {

        public ProductFetchSubmitTransferTask(String item) {
            super(item);
        }

        @Override
        public void onSuccess(TransferEntity result) {
            getDisplay().showWebViewNotify(result.msg, 2);
        }
    }

    private class ProductFetchMallProductTask extends ProductFetchMallProductRunnable {

        public ProductFetchMallProductTask(String page, String perpage, String city, String cid, String ccid, String dest, String sort) {
            super(page, perpage, city, cid, ccid, dest, sort);
        }

        public void onSuccess(ProductsEntity result) {
//            Log.v(TAG, "result code " + result.code);
//            Log.v(TAG, "result ProductsEntity msg " + result.msg);
//            Log.v(TAG, "result errorInfo " + result.errorInfo);
            for(Ui item : getUis()){
                if(item instanceof ProductController.MallUi){
                    ((ProductController.MallUi)item).refreshProductList(result.msg);
                    break;
                }
            }

//        this.getBus().post(new ProductState.InnFetchProductBreEvent(result.msg));
        }

    }

    private class ProductFetchProDetailTask extends ProductFetchProDetailRunnable {

        public ProductFetchProDetailTask(String item) {
            super(item);
        }

        @Override
        public void onSuccess(ProductDetailEntity result) {
            for(Ui item : getUis()){
                if(item instanceof ProductController.DetailUi){
                    ((ProductController.DetailUi)item).refreshProDetail(result.msg);
                    break;
                }
            }
        }
    }

    private class ProductFetchShareInfoTask extends ProFetchShareInfoRunnable {

        public ProductFetchShareInfoTask(String inn_id) {
            super(inn_id);
        }

        @Override
        public void onSuccess(StoreShareEntity result) {
            Log.v("tourshare","result "+result.msg.name);
          getDisplay().showShareUI(result.msg.thumb, result.msg.name, result.msg.url);
        }
    }

    private class ProCodeVerifyTask extends ProductFetchCodeVeriStateRunnable{
        public ProCodeVerifyTask(String item) {
            super(item);
        }

        public void onSuccess(CodeInfoEntity result){
            for(Ui item : getUis()){
                if(item instanceof ProductController.CodeUi){
                    ((ProductController.CodeUi)item).codeSuccessed(result.msg);
                    break;
                }
            }
        }

        public void onSuccessBadCode(int code, String errorInfo){
            Toast.makeText(TourApplication.instance().getApplicationContext(), errorInfo, Toast.LENGTH_SHORT).show();
            for(Ui item : getUis()){
                if(item instanceof ProductController.CodeUi){
                    ((ProductController.CodeUi)item).showBadCodeMsg(errorInfo);
                    break;
                }
            }
        }
    }

    private class ProductFetchCommentsTask extends ProductFetchCommentsRunnable {
        public ProductFetchCommentsTask(String productId, String page, String perpage) {
            super(productId, page, perpage);
        }

        public void onSuccess(ProCommentsEntity result){
            for(Ui item : getUis()){
                if(item instanceof ProductController.CommentsUi){
                    ((ProductController.CommentsUi)item).refreshComment(result.msg);
                    break;
                }
            }
        }
    }

    private class ProductChangeShelfStateTask extends ProductChangeShelfStateRunnable{
        public ProductChangeShelfStateTask(String tag_id,String product_id ,String action){
            super(tag_id, product_id,action);
        }

        public void onSuccess(StateEntity result){
            for(Ui item : getUis()){
                if(item instanceof ProductController.ProStateUi){
                    ((ProductController.ProStateUi)item).refreshStateChange();
                }
            }
        }
    }


}
