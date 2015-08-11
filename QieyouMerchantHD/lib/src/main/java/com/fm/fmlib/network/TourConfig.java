package com.fm.fmlib.network;

/**
 * Created by qieyou on 2015/7/30.
 */
public class TourConfig {
    private final String release_root_url = "http://b.totour.com";
    private final String dev_root_url = "http://bdev.totour.com";
    private final String imageRoot="http://static.totour.com";
    private String endpoint;
    private static TourConfig config;

    public static TourConfig instance() {
        if (null == config) {
            config = new TourConfig();
        }
        return config;
    }

    public TourConfig() {
        initDevUrl();
    }

    private void initDevUrl() {
        endpoint = dev_root_url;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getImageRoot(){
        return imageRoot;
    }
}
