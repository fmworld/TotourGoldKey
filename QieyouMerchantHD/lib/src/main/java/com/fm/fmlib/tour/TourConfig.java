package com.fm.fmlib.tour;

/**
 * Created by qieyou on 2015/7/30.
 */
public class TourConfig {
    private final static int DEV = 1;
    private final static int RELE = 2;
    private final String release_root_url = "http://b.totour.com";
    private final String dev_root_url = "http://bdev.totour.com";
    private final String imageRoot = "http://static.totour.com";
    private String endpoint;
    private static TourConfig config;

    public static TourConfig instance() {
        if (null == config) {
            config = new TourConfig();
        }
        return config;
    }

    private TourConfig() {
        initUrl(RELE);
    }

    private void initUrl(int type) {
        if (type == RELE) {
            initReleUrl();
        } else {
            initDevUrl();
        }
    }

    private void initDevUrl() {
        endpoint = dev_root_url;
    }

    private void initReleUrl(){
        endpoint = release_root_url;
    }
    public String getEndpoint() {
        return endpoint;
    }

    public String getImageRoot() {
        return imageRoot;
    }
}
