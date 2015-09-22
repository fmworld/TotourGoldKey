package com.fm.fmlib.models;

import com.fm.fmlib.dao.User;
import com.fm.fmlib.tour.bean.UserInfo;

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

    boolean saveWithUser(User copy);

    boolean saveWithUserInfo(UserInfo info);

    void setState(String state);

    String getState();

}
