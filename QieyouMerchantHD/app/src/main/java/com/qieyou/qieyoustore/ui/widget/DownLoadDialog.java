package com.qieyou.qieyoustore.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.fenjuly.library.ArrowDownloadButton;
import com.fm.fmlib.utils.DownloadManagerUtil;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.DownloadProgressThread;


/**
 * Created by zhoufeng'an on 2015/8/30.
 */
public class DownLoadDialog extends Dialog implements View.OnClickListener, DownloadProgressThread.ProgressListener {
    private Uri uri;
    private RelativeLayout content;
    private ArrowDownloadButton mArrowDownloadButton;
    private static Handler handler = new Handler();
    private DownloadProgressThread mDownloadProgressThread;
    private Context mContext;
    private View.OnClickListener dismissListener;

    public DownLoadDialog(Context context) {
        super(context, R.style.translucent);
        mContext = context;
        initUI();
    }

    public DownLoadDialog(Context context, int theme) {
        super(context, R.style.load_translucent);
        mContext = context;
        initUI();
    }

    private void initUI() {
        content = (RelativeLayout) View.inflate(this.getContext(), R.layout.widget_download_notify, null);
        this.setContentView(content);
        content.findViewById(R.id.update_cancle).setOnClickListener(this);
        content.findViewById(R.id.update_confirm).setOnClickListener(this);
        mArrowDownloadButton = (ArrowDownloadButton) content.findViewById(R.id.arrow_download_button);
    }

    public void showNotify(Uri uri) {
        if (!isShowing()) {
            this.uri = uri;
            show();
        }
    }

    public void setDismissListener(View.OnClickListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.update_cancle) {
            if(null != mDownloadProgressThread)
            mDownloadProgressThread.cancelTask();

            if(null != dismissListener){
                dismissListener.onClick(v);
            }
            dismiss();
            return;
        }

        if (v.getId() == R.id.update_confirm) {
            if (null == mDownloadProgressThread) {
                startProgressUpdate();
            } else if (!mDownloadProgressThread.isRunning()) {
                mDownloadProgressThread.clear();
                startProgressUpdate();
            }
            return;
        }

    }

    private void startProgressUpdate() {
        mArrowDownloadButton.reset();
        mDownloadProgressThread =
                new DownloadProgressThread(DownLoadDialog.this.getContext(), handler,
                        this, DownloadManagerUtil.download(this.getContext(), uri));
        mDownloadProgressThread.start();
    }

    protected void onStop() {
        super.onStop();
        if (null != mDownloadProgressThread) {
            mDownloadProgressThread.clear();
        }
        mArrowDownloadButton.reset();
    }

    @Override
    public void startProgress() {
        mArrowDownloadButton.startAnimating();
    }

    @Override
    public void updateProgress(float progress) {
        mArrowDownloadButton.setProgress(progress);
    }

    @Override
    public void endProgress(long downloadId) {

        DownloadManagerUtil.installApk(mContext, downloadId);
        handler.post(new Runnable() {
            @Override
            public void run() {
                DownLoadDialog.this.dismiss();
            }
        });
    }
}
