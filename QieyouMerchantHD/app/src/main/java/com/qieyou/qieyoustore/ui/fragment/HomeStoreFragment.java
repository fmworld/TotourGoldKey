package com.qieyou.qieyoustore.ui.fragment;

import com.fm.fmlib.controllers.ProductController;
import com.fm.fmlib.dao.ProductBreviary;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/19.
 */
public class HomeStoreFragment extends AnimListenFragment implements ProductController.StoreUi{
    protected ProductController.ProductStoreCallbacks mProductStoreCallbacks;
    @Override
    public void updateTabBar() {

    }

    @Override
    public void setCallbacks(ProductController.ProductUiCallbacks callbacks) {
        mProductStoreCallbacks = (ProductController.ProductStoreCallbacks) callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    public void showProductBre(List<ProductBreviary> pros) {

    }

    @Override
    public void showEmptyItem() {

    }
}
