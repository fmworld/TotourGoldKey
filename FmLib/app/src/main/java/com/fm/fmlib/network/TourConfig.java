package com.fm.fmlib.network;

/**
 * Created by qieyou on 2015/7/30.
 */
public class TourConfig {
    private final String release_root_url ="http://b.totour.com";
    private final String dev_root_url ="http://bdev.totour.com";
    private String endpoint;

    public  TourConfig(){
        initDevUrl();
    }

    private void initDevUrl(){
        endpoint = dev_root_url;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
