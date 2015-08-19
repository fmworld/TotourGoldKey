package com.fm.fmlib.utils;

import com.fm.fmlib.dao.CategoryList;
import com.fm.fmlib.dao.CategoryTitle;
import com.fm.fmlib.dao.Inn;
import com.fm.fmlib.dao.LocalList;
import com.fm.fmlib.dao.LocalTitle;
import com.fm.fmlib.dao.ProductTag;
import com.fm.fmlib.tour.bean.ProductInfo;
import com.fm.fmlib.tour.entity.StoreCardEntity;
import com.fm.fmlib.tour.params.ProductParams;

/**
 * Created by zhoufeng'an on 2015/8/11.
 */
public class MappingUtil {
    public static void inn2inn(Inn srctemp, Inn target){
        target.setInnName(srctemp.getInnName());
        target.setInnProducts(srctemp.getInnProducts());
        target.setInnHead(srctemp.getInnHead());
        target.setInnerServe(srctemp.getInnerServe());
        target.setInnerTodayServe(srctemp.getInnerTodayServe());
        target.setInnerContact(srctemp.getInnerContact());
        target.setInnerHead(srctemp.getInnerHead());
        target.setInnerIdentify(srctemp.getInnerIdentify());
        target.setInnerMoblie(srctemp.getInnerMoblie());
        target.setInnerScore(srctemp.getInnerScore());
    }

    public static Inn innInfoJson2inn(StoreCardEntity.innInfoJson infoJson){
        Inn inn = new Inn();
        inn.setInnName(infoJson.inn_name);
        inn.setInnProducts(infoJson.inn_products);
        inn.setInnHead(infoJson.inn_head);
        inn.setInnerServe(infoJson.inner_serve);
        inn.setInnerTodayServe(infoJson.inner_today_serve);
        inn.setInnerContact(infoJson.inner_contact);
        inn.setInnerHead(infoJson.inner_head);
        inn.setInnerIdentify(infoJson.inner_identify);
        inn.setInnerMoblie(infoJson.inner_moblie);
        inn.setInnerScore(infoJson.inner_score);
        return inn;
    }

    public static void tag2tag(ProductTag src, ProductTag target){
        target.setItem_count(src.getItem_count());
        target.setTag_id(src.getTag_id());
        target.setItem_seq(src.getItem_seq());
        target.setTag_name(src.getTag_name());
        target.setTag_seq(src.getTag_seq());
    }

    public static void cTitle2cTitle(CategoryTitle src, CategoryTitle target){
        target.setId(src.getId());
        target.setName(src.getName());
    }

    public static void cList2cList(CategoryList src, CategoryList target){
        target.setName(src.getName());
        target.setCategory(src.getCategory());
        target.setCategory_id(src.getCategory_id());
    }

    public static void lTitle2lTitle(LocalTitle src, LocalTitle target){
        target.setDest_id(src.getDest_id());
        target.setDest_name(src.getDest_name());
    }

    public static void lList2lList(LocalList src, LocalList target){
        target.setDest_id(src.getDest_id());
        target.setLocal_id(src.getLocal_id());
        target.setLocal_name(src.getLocal_name());
    }

}
