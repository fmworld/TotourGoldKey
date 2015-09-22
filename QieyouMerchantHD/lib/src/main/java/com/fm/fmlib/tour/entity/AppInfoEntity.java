package com.fm.fmlib.tour.entity;

/**
 * Created by zhoufeng'an on 2015/9/21.
 */
public class AppInfoEntity extends BaseEntity {
    public AppTemp msg;
    public class AppTemp {
        public AppInfo app;
    }

    public class AppInfo {
        public String version;
        public String link;
        public int vercode;
    }
}
