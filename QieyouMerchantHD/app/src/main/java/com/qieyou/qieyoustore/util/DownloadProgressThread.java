package com.qieyou.qieyoustore.util;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Handler;

import com.fm.fmlib.utils.DownloadManagerUtil;

/**
 * Created by zhoufeng'an on 2015/9/22.
 */
public class DownloadProgressThread extends Thread {
    private boolean isRunning = false;
    private long downloadId;
    private Handler handler;
    private Context context;
    private ProgressListener mProgressListener;
    final int[] values = new int[]{-2, -1, 0};

    private Runnable updateRunnale = new Runnable() {
        @Override
        public void run() {
            float vlaue = (float) (100 * (double) values[0] / (double) values[1]);
            if (null != mProgressListener) {
                mProgressListener.updateProgress(vlaue);
            }
        }
    };

    public interface ProgressListener {
        void startProgress();

        void updateProgress(float progress);

        void endProgress(long downloadId);
    }

    public DownloadProgressThread(Context context, Handler handler, ProgressListener mProgressListener, long downloadId) {
        this.downloadId = downloadId;
        this.handler = handler;
        this.context = context;
        this.mProgressListener = mProgressListener;
    }

    public void start(){
        isRunning = true;
        super.start();
    }

    public void run() {


        //start
        if(isRunning)
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (null != mProgressListener) {
                    mProgressListener.startProgress();
                }
            }
        });

        //update
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        while (8 != values[2]&&isRunning) {
            DownloadManagerUtil.getBytesAndStatus(dm,query, downloadId, values);
            if (0 < values[1]) {

                handler.post(updateRunnale);
            }
        }

        //end
        if (8 == values[2]&&isRunning) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    isRunning = false;
                    if (null != mProgressListener) {
                        mProgressListener.endProgress(downloadId);
                    }
                }
            });
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stopProgress() {
        this.isRunning = false;
    }

    public void cancelTask(){
        stopProgress();
        DownloadManager dm =
                (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        dm.remove(downloadId);
    }

    public void clear(){
        stopProgress();
        this.downloadId = 0;
        this.handler = null;
        this.context = null;
        this.mProgressListener = null;
    }
}
