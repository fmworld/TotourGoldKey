package com.fm.fmlib.state;

import com.fm.fmlib.dao.ProductBreviary;
import com.fm.fmlib.tour.bean.ProductInfo;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/3.
 */
public interface ProductState extends BaseState {
    enum LaunchProfileType{
        ad,
        shop,
        slider,
        store
    }
    class InnFetchProductBreEvent {
        public List<ProductBreviary> infos;

        public InnFetchProductBreEvent(List<ProductBreviary> infos) {
            this.infos = infos;
        }
    }

     class ProductFetchTagListEvent {

        public ProductFetchTagListEvent() {
        }
    }

    class ProductFetchSubmitUrlEvent {
        public String item;
        public ProductFetchSubmitUrlEvent(String item) {
            this.item = item;
        }
    }



}
