package com.fm.fmlib.state;

/**
 * Created by zhoufeng'an on 2015/8/3.
 */
public interface HomeState extends BaseState {
    public static class HomeManagerFetchEvent{
        public String managerUrl;
        public HomeManagerFetchEvent(String url){
            managerUrl = url;
        }
    }
}
