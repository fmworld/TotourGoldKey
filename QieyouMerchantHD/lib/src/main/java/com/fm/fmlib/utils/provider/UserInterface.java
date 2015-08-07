package com.fm.fmlib.utils.provider;

import com.fm.fmlib.dao.user;

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

    boolean saveUser();

    boolean saveWithUser(user copy);

    void setState(String state);

    String getState();

}
