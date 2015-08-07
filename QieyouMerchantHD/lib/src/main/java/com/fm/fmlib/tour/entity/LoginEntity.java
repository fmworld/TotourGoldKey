package com.fm.fmlib.tour.entity;

/**
 * Created by zhoufeng'an on 2015/7/30.
 */
public class LoginEntity extends  BaseEntity{
    public LoginInfo msg;
    public class LoginInfo{
        public String token;
        public String role;
        public String state;
    }
}
