package com.qieyou.qieyoustore.util;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.Product;
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

    /**
     * 是否可以立即抢购
     * @param info
     * @return
     */
    public static boolean productStateStr(ProductBreviary info){
        if(null == info.getState()){
            return  false;
        }
        String state = info.getState().toUpperCase();
        if(Integer.valueOf(info.getQuantity())>0){
            if(ProductState.Y.toString().equals(state)){
                return true;
            }

            if(ProductState.T.toString().equals(state)){
                return Long.valueOf(info.getTuan_end_time()) > System.currentTimeMillis()/1000;
            }
        }

        return false;
    }

    /**
     * 是否可以立即抢购
     * @param info
     * @return
     */
    public static boolean productStateStr(ProductDetail info){
        String state = info.state.toUpperCase();
        if(Integer.valueOf(info.quantity)>0){
            if(ProductState.Y.toString().equals(state)){
                return true;
            }

            if(ProductState.T.toString().equals(state)){
                return Long.valueOf(info.tuan_end_time) > System.currentTimeMillis()/1000;
            }
        }

        return false;
    }

    /**
     * 是否可以立即抢购
     * @param info
     * @return
     */
    public static boolean productStateStr(Product info){
        String state = info.getState().toUpperCase();
        if(Integer.valueOf(info.getQuantity())>0){
            if(ProductState.Y.toString().equals(state)){
                return true;
            }

            if(ProductState.T.toString().equals(state)){
               return Long.valueOf(info.getTuan_end_time()) > System.currentTimeMillis()/1000;
            }
        }

        return false;
    }

    /**
     * 抢购状态
     * @param info
     * @return
     */
    public static String productGetStateStr(Product info){
        String state = info.getState().toUpperCase();
        if(productStateStr(info)){
            return TourApplication.instance().getBaseContext().getString(R.string.sale_state_able);
        }

        if(ProductState.N.toString().equals(state)){
            return TourApplication.instance().getBaseContext().getString(R.string.mall_pro_to_shelve);
        }

        if(Long.valueOf(info.getTuan_end_time()) <= System.currentTimeMillis()/1000){
            return TourApplication.instance().getBaseContext().getString(R.string.mall_pro_end_time);
        }


        if(Integer.valueOf(info.getQuantity()) <= 0){
            return TourApplication.instance().getBaseContext().getString(R.string.sale_state_unable);

        }


        return "";
    }

    /**
     * 抢购状态
     * @param info
     * @return
     */
    public static String productGetStateStr(ProductDetail info){

        String state = info.state.toUpperCase();

        if(productStateStr(info)){
            return TourApplication.instance().getBaseContext().getString(R.string.sale_state_able);
        }

        if(ProductState.N.toString().equals(state)){
            return TourApplication.instance().getBaseContext().getString(R.string.mall_pro_to_shelve);
        }

        if(Long.valueOf(info.tuan_end_time) <= System.currentTimeMillis()/1000){
            return TourApplication.instance().getBaseContext().getString(R.string.mall_pro_end_time);
        }

        if(Integer.valueOf(info.quantity) <= 0){
            return TourApplication.instance().getBaseContext().getString(R.string.sale_state_unable);
        }



        return "";
    }


    public static String productUnsaleStateStr(Product info){
        String state = info.getState().toUpperCase();

        if(ProductState.N.toString().equals(state)){
            return TourApplication.instance().getBaseContext().getString(R.string.mall_pro_to_shelve);
        }

        if(Long.valueOf(info.getTuan_end_time()) <= System.currentTimeMillis()/1000){
            return TourApplication.instance().getBaseContext().getString(R.string.mall_pro_end_time);
        }


        if(Integer.valueOf(info.getQuantity()) <= 0){
            return TourApplication.instance().getBaseContext().getString(R.string.sale_state_unable);

        }


        return "";
    }

    public static String productUnsaleStateStr(ProductDetail info){
        String state = info.state.toUpperCase();

        if(ProductState.N.toString().equals(state)){
            return TourApplication.instance().getBaseContext().getString(R.string.mall_pro_to_shelve);
        }

        if(Long.valueOf(info.tuan_end_time) <= System.currentTimeMillis()/1000){
            return TourApplication.instance().getBaseContext().getString(R.string.mall_pro_end_time);
        }


        if(Integer.valueOf(info.quantity) <= 0){
            return TourApplication.instance().getBaseContext().getString(R.string.sale_state_unable);

        }


        return "";
    }
}
