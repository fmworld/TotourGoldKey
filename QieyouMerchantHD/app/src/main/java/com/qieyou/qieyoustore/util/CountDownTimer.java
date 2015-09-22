package com.qieyou.qieyoustore.util;

import android.app.Activity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhoufeng'an on 2015/9/15.
 */
public class CountDownTimer extends Timer {
    private int codeInvalidateTime = 60;
    private long startCount;
    private int leftTimes;
    private Activity mActivity;
    private CountListener mListener;
    private Runnable taskRunnale;
    public interface CountListener{
        void refreshCount(int leftTimes);
    }



    public CountDownTimer(Activity mActivity, CountListener listener){
        this.mActivity = mActivity;
        this.mListener = listener;
        taskRunnale = new Runnable(){
            @Override
            public void run() {
                if(null != mListener){
                    mListener.refreshCount(leftTimes);
                }
            }
        };
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            leftTimes = (int) (codeInvalidateTime - (System.currentTimeMillis() - startCount) / 1000);
            if(null != taskRunnale){
                mActivity.runOnUiThread(taskRunnale);
            }
        }
    };

    public void setCodeInvalidateTime(int codeInvalidateTime) {
        this.codeInvalidateTime = codeInvalidateTime;
    }

    public void schedule(int delay, int period) {
        startCount = System.currentTimeMillis();
        super.schedule(task, delay, period);
    }
}
