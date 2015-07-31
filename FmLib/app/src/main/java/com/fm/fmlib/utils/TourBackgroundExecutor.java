package com.fm.fmlib.utils;

import android.os.*;
import android.os.Process;
import android.util.Log;

import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.client.BaseError;

import java.util.concurrent.ExecutorService;

/**
 * Created by zhoufeng'an on 2015/7/30.
 */
public class TourBackgroundExecutor implements BackgroundExecutor{
    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService mExecutorService;
    public TourBackgroundExecutor(ExecutorService mExecutorService){
        this.mExecutorService = mExecutorService;
    }

    @Override
    public <R> void execute(NetworkCallRunnable<R> runnable) {
        mExecutorService.execute(new TourNetworkRunner<>(runnable));
    }

    public class TourNetworkRunner<R> implements Runnable{
        private final NetworkCallRunnable<R> mNetworkCallRunnable;
        public TourNetworkRunner(NetworkCallRunnable<R> runnable){
            mNetworkCallRunnable = runnable;
        }
        @Override
        public void run() {
            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    mNetworkCallRunnable.onPreTourCall();
                }
            });

            R result = null;
            BaseError retrofitError = null;

            try {
                result = mNetworkCallRunnable.doBackgroundCall();
            } catch (BaseError re) {
                retrofitError = re;
                Log.d(((Object) this).getClass().getSimpleName(), "Error while completing network call", re);
            }

            sHandler.post(new ResultCallback(result, retrofitError));
        }
        private class ResultCallback implements Runnable {
            private final R mResult;
            private final BaseError mRetrofitError;

            private ResultCallback(R result, BaseError retrofitError) {
                mResult = result;
                mRetrofitError = retrofitError;
            }

            @Override
            public void run() {
                if (mResult != null) {
                    mNetworkCallRunnable.onSuccess(mResult);
                } else if (mRetrofitError != null) {
                    mNetworkCallRunnable.onError(mRetrofitError);
                }
                mNetworkCallRunnable.onFinished();
            }
        }
    }


}
