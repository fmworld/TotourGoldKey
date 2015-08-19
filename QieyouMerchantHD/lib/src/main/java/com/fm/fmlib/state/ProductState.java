package com.fm.fmlib.state;

import com.fm.fmlib.dao.ProductBreviary;
import com.fm.fmlib.tour.bean.ProductInfo;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/3.
 */
public interface ProductState extends BaseState {
    public static class InnFetchProductBreEvent {
        public List<ProductBreviary> infos;

        public InnFetchProductBreEvent(List<ProductBreviary> infos) {
            this.infos = infos;
        }
    }

    public static class ProductFetchTagListEvent {

        public ProductFetchTagListEvent() {
        }
    }



}
