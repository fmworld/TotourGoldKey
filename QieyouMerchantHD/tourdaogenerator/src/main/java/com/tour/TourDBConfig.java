package com.tour;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class TourDBConfig {
    private String sourceDir ="lib/src/main/java";
    private int DBVersion =1;
    private String sourceStructure = "com.fm.fmlib.dao";
    public enum TableName{
        User,
        Product,
        Inn,
        MyProperty,
    }
    public enum UserTable{
        account,
        password,
        token,
        islogin,
        role,
        state,
        userMobile,
        innerName,
        innerHead
    }

    public enum InnTable{
        innId,
        innName,
        innProducts,
        innHead,
        innerHead,
        innerScore,
        innerServe,
        innerContact,
        innerMoblie,
        innerIdentify,
        innerTodayServe
    }

    public enum PropertyTable{
        key,
        value,
    }

    public enum ProductTable{
        product_id,
        thumb,
        product_images,
        product_name,
        category,
        category_id,
        tag_id,
        tag_name,
        old_price,
        price,
        quantity,
        tuan_end_time,
        tuan_noteend_time,
        booking_info,
        content,
        keyword,
        traveler,
        innholder,

    }

    public String getSourceDir() {
        return sourceDir;
    }

    public int getDBVersion() {
        return DBVersion;
    }

    public String getSourceStructure() {
        return sourceStructure;
    }
}
