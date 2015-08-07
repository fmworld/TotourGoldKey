package com.fm.fmlib.utils.provider;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public interface UserInterface {
    String getToken();

    void setToken(String token);

    String getAccount();

    void setAccount(String account);

    //获取MD5后的密码
    String getPassword();

    void setPassword(String password);

    boolean isLogined();

    void setLogined(boolean islogined);

    void setState(String state);

    String getState();

    boolean saveUser();
}
