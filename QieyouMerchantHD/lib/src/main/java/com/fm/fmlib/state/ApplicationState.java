package com.fm.fmlib.state;

import com.fm.fmlib.tour.bean.ProductInfo;

/**
 * Created by zhoufeng'an on 2015/8/14.
 */
public class ApplicationState implements InnState {
    private ProductInfo aeProductInfo;
    @Override
    public void setProductInfo(ProductInfo info) {
        aeProductInfo =info;
    }

    @Override
    public ProductInfo getProductInfo() {
        return aeProductInfo;
    }
}
