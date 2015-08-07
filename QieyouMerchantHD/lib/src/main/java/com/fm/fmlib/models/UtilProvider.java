package com.fm.fmlib.models;

import com.fm.fmlib.utils.BackgroundExecutor;
import com.fm.fmlib.utils.TourBackgroundExecutor;

import java.util.concurrent.Executors;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class UtilProvider {

    public static BackgroundExecutor provideMultiThreadExecutor() {
        final int numberCores = Runtime.getRuntime().availableProcessors();
        return new TourBackgroundExecutor(Executors.newFixedThreadPool(numberCores * 2 + 1));
    }
}