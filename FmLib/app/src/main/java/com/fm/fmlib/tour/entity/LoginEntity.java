package com.fm.fmlib.tour.entity;

/**
 * Created by zhoufeng'an on 2015/7/30.
 */
public class LoginEntity extends  BaseEntity{
    public Msg msg;
    public class Msg{
        public String token;
        public String role;
        public String state;
    }
}
