package com.fm.fmlib.state;

import com.fm.fmlib.dao.ProductBreviary;
import com.fm.fmlib.tour.bean.ProductInfo;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/3.
 */
public interface ProductState extends BaseState {
    String Tip ="tip";
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


    class ProductTipStateChangeEvent {
        public String state;
        public ProductTipStateChangeEvent(String state){
            this.state = state;
        }
    }

    class ProductFetchSubmitUrlEvent {
        public String item;
        public ProductFetchSubmitUrlEvent(String item) {
            this.item = item;
        }
    }

    class ProductChangeShelfStateEvent {
        public String tag_id;
        public String pro_id;
        public String state;
        public ProductChangeShelfStateEvent(String tag_id,String pro_id,String state) {
            this.tag_id = tag_id;
            this.pro_id = pro_id;
            this.state = state;
        }
    }


    class ProductAddNewTagEvent {


    }


}
