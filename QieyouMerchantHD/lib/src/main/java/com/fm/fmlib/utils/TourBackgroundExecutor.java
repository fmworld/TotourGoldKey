package com.fm.fmlib.utils;

import android.os.*;
import android.os.Process;
import android.util.Log;

import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.tour.entity.BaseEntity;

import java.util.concurrent.ExecutorService;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/30.
 */
public class TourBackgroundExecutor implements BackgroundExecutor {
    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService mExecutorService;

    public TourBackgroundExecutor(ExecutorService mExecutorService) {
        this.mExecutorService = mExecutorService;
    }

    @Override
    public <R> void execute(NetworkCallRunnable<R> runnable) {
        mExecutorService.execute(new TourNetworkRunner<>(runnable));
    }

    public class TourNetworkRunner<R> implements Runnable {
        private final NetworkCallRunnable<R> mNetworkCallRunnable;

        public TourNetworkRunner(NetworkCallRunnable<R> runnable) {
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
            RetrofitError retrofitError = null;

            try {
                result = mNetworkCallRunnable.doBackgroundCall();
            } catch (RetrofitError re) {
                retrofitError = re;
                Log.d(((Object) this).getClass().getSimpleName(), "Error while completing network call", re);
            }
            if (result != null) {
                if (!isBadNetworkState((BaseEntity) result)) {
                    mNetworkCallRunnable.onSuccessInBackground(result);
                }
            }
            sHandler.post(new ResultCallback(result, retrofitError));
        }

        private class ResultCallback implements Runnable {
            private final R mResult;
            private final RetrofitError mRetrofitError;

            private ResultCallback(R result, RetrofitError retrofitError) {
                mResult = result;
                mRetrofitError = retrofitError;
            }

            @Override
            public void run() {
                if (mResult != null) {
                    if (!isBadNetworkState((BaseEntity) mResult)) {
                        mNetworkCallRunnable.onSuccess(mResult);
                    }else{
                        mNetworkCallRunnable.onSuccessBadCode(((BaseEntity) mResult).code,((BaseEntity) mResult).errorInfo);
                    }
                } else if (mRetrofitError != null) {
                    mNetworkCallRunnable.onError(mRetrofitError);
                }
                mNetworkCallRunnable.onFinished();
            }
        }
    }

    public boolean isBadNetworkState(BaseEntity entity) {
        if (1 == entity.code) {
            return false;
        }
        return true;
    }
}
