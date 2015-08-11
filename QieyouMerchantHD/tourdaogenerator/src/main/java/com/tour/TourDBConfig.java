package com.tour;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class TourDBConfig {
    private String sourceDir ="lib/src/main/java";
    private int DBVersion =1;
    private String sourceStructure = "com.fm.fmlib.dao";
    public enum TableName{
        user,
        product,
        inn
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
