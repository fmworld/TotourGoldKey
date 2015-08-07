package com.fm.fmlib.state;

/**
 * Created by zhoufeng'an on 2015/8/3.
 */
public interface UserState extends BaseState {
    public static class UserLoginExecutedEvent {
        public final int callingId;
        public final String name;
        public final String pwd;

        public UserLoginExecutedEvent(int callingId, String name, String pwd) {
            this.callingId = callingId;
            this.name = name;
            this.pwd = pwd;
        }
    }

    public static class UserLoginAccessCodeEvent {
    }

    public static class UserResetPasswordEvent {
    }
}
