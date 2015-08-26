package com.qieyou.qieyoustore.util;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.ProductBreviary;
import com.fm.fmlib.tour.bean.ProductDetail;
import com.qieyou.qieyoustore.R;

/**
 * Created by zhoufeng'an on 2015/8/19.
 */
public class CodeBusinessMap {
    enum ProductState{
        Y,//在售
        D,//删除
        N,//下架
        T//在团购
    }
    public static boolean productStateStr(ProductBreviary info){
        String state = info.getState();
        if(ProductState.Y.toString().equals(state)||ProductState.T.toString().equals(state)){
                return Integer.valueOf(info.getQuantity()) > 0;
        }

        return false;
    }

    public static boolean productStateStr(ProductDetail info){
        String state = info.state;
        if(ProductState.Y.toString().equals(state)||ProductState.T.toString().equals(state)){
            return Integer.valueOf(info.quantity) > 0;
        }

        return false;
    }

//    public static String getString(String  state){
//        if(ProductState.Y.toString().equals(state))
//        {
//            TourApplication.instance().getString(R.string)
//        }
//    }
}
