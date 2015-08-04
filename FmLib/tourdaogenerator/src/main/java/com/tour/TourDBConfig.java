package com.tour;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class TourDBConfig {
    private String sourceDir ="app/src/main/java";
    private int DBVersion =1;
    private String sourceStructure = "com.fm.fmlib.dao";
    public enum TableName{
        user,
        product
    }
    public enum UserTable{
        account,
        password,
        token,
        islogin,
        role,
        state
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
