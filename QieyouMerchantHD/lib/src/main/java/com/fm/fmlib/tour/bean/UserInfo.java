package com.fm.fmlib.tour.bean;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class UserInfo {
    public String account = "18612540330";
    public String password = "qieyou";
    public String token;
    public String role;
    public String state;
    public String user_mobile;
    public String inner_name;
    public String inner_head;

    public UserInfo(String user_mobile, String inner_head, String inner_name){
        this.user_mobile = user_mobile;
        this.inner_head= inner_head;
        this.inner_name= inner_name;
    }
}
