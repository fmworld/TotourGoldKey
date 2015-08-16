package com.fm.fmlib.state;

import com.fm.fmlib.tour.bean.ProductInfo;

/**
 * Created by zhoufeng'an on 2015/8/3.
 */
public interface InnState extends BaseState {
    public static class InnFetchProductInfoEvent {
        public ProductInfo info;

        public InnFetchProductInfoEvent(ProductInfo info) {
            this.info = info;
        }
    }

    public static class InnFetchShareInfoEvent {
        public String thumb;
        public String name;
        public String url;

        public InnFetchShareInfoEvent(String thumb, String name, String url) {
            this.thumb = thumb;
            this.name = name;
            this.url = url;
        }
    }


    public static class InnFetchPaymentTypeEvent {
        public String paymentUrl;

        public InnFetchPaymentTypeEvent(String paymentUrl) {
            this.paymentUrl = paymentUrl;
        }

    }

    void setProductInfo(ProductInfo info);

    ProductInfo getProductInfo();

}
