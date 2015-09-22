package com.fm.fmlib.state;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.User;
import com.fm.fmlib.tour.bean.ProductInfo;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Created by zhoufeng'an on 2015/8/14.
 */
@Singleton
public class ApplicationState implements InnState, UserState {
    private ProductInfo aeProductInfo;

    @Inject
    public ApplicationState() {
    }

    @Override
    public void setProductInfo(ProductInfo info) {
        aeProductInfo = info;
    }

    @Override
    public ProductInfo getProductInfo() {
        return aeProductInfo;
    }

    @Override
    public User getDefaultLoginUser() {
        User user = TourApplication.instance().getDaoUser();

        if (null != user && user.getIslogin().booleanValue()) {
            return user;
        }
        return null;
    }

    @Override
    public boolean loginOut() {
        User user = TourApplication.instance().getDaoUser();

        if (null != user && user.getIslogin().booleanValue()) {
            user.setIslogin(Boolean.FALSE);
            TourApplication.instance().updateData(user);
            return true;
        }

        return false;
    }
}
