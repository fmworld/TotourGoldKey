package com.fm.fmlib.tour.entity;

/**
 * Created by zhoufeng'an on 2015/7/30.
 */
public class StoreShareEntity extends  BaseEntity{
    public ShareInfo msg;

    public class ShareInfo{
        public String thumb;
        public String name;
        public String url;
    }
}
