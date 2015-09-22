package com.fm.fmlib.state;

import com.fm.fmlib.dao.User;

/**
 * Created by zhoufeng'an on 2015/8/3.
 */


public interface UserState extends BaseState {
    enum Setting{
        about,
        feedback,
        changepwd
    }
    class UserLoginExecutedEvent {
        public final int callingId;
        public final String name;
        public final String pwd;

        public UserLoginExecutedEvent(int callingId, String name, String pwd) {
            this.callingId = callingId;
            this.name = name;
            this.pwd = pwd;
        }
    }

    class UserFetchUserInfoEvent{

    }

    class UserLoginAccessCodeEvent {
    }

    class UserResetPasswordEvent {
    }

    User getDefaultLoginUser();

    boolean loginOut();

}
