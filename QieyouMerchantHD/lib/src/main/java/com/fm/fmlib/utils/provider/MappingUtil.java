package com.fm.fmlib.utils.provider;

import com.fm.fmlib.dao.Inn;
import com.fm.fmlib.tour.entity.StoreCardEntity;

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
}
