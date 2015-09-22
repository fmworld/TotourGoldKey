package com.fm.fmlib.tour;

import android.app.DownloadManager;
import android.os.Environment;

import com.fm.fmlib.TourApplication;

import java.io.File;

/**
 * Created by qieyou on 2015/7/30.
 */
public class TourConfig {
    public final static int DEV = 1;
    public final static int RELE = 2;
    private int currentModel = 0;
    private final String release_root_url = "http://b.totour.com";
    private final String dev_root_url = "http://bdev.totour.com";
    private final String release_imageRoot = "http://static.totour.com";
    private final String dev_imageRoot = "http://static.dev.totour.com";
    private String imageRoot;
    private String endpoint;
    private static TourConfig config;

    public static TourConfig instance() {
        if (null == config) {
            config = new TourConfig();
        }
        return config;
    }

    private TourConfig() {
        initUrl(DEV);
    }

    private void initUrl(int type) {
        currentModel = type;
        if (currentModel == RELE) {
            initReleUrl();
        } else {
            initDevUrl();
        }
    }

    public int getModel() {
        return currentModel;
    }

    public boolean isRelease() {
        return currentModel == RELE;
    }

    private void initDevUrl() {
        imageRoot = dev_imageRoot;
        endpoint = dev_root_url;
    }

    private void initReleUrl() {
        imageRoot = release_imageRoot;
        endpoint = release_root_url;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getImageRoot() {
        return imageRoot;
    }

    public File getPicCache() {
        return TourApplication.instance().getCacheDir();
    }

    public File getDownloadCache() {
        return TourApplication.instance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);

    }
}
