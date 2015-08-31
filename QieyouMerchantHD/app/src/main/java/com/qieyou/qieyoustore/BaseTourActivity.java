package com.qieyou.qieyoustore;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.desmond.squarecamera.CameraActivity;
import com.fm.fmlib.Display;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.ui.widget.LoadDialog;
import com.qieyou.qieyoustore.util.TourPicConfig;

import java.io.File;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class BaseTourActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getSimpleName();
    private MainController mMainController;
    private Display mDisplay;
    private LoadDialog load_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Request Progress Bar in Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.v(TAG, "density " + DisplayUtil.getDensity(this));
        super.onCreate(savedInstanceState);

        setContentView(getContentViewLayoutId());

        mMainController = MerchanthdApplication.instance().getmMainController();
//        mDisplay = new AndroidDisplay(this, mDrawerLayout);
        mDisplay = new AndroidDisplay(this);
        handleIntent(getIntent(), getDisplay());
    }

    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent, getDisplay());
    }

    protected void handleIntent(Intent intent, Display display) {
    }

    public void showLoading() {
        if (null == load_dialog) {
            load_dialog = new LoadDialog(this, R.style.translucent);
        }

        load_dialog.show();
    }

    public void dismissLoading(

    ) {
        if (null != load_dialog) {
            load_dialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainController.attachDisplay(mDisplay);
//        mMainController.setHostCallbacks(this);
        mMainController.init();
    }

    @Override
    protected void onPause() {
        mMainController.suspend();
//      mMainController.setHostCallbacks(null);
        mMainController.detachDisplay(mDisplay);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisplay = null;
    }


    public Display getDisplay() {
        return mDisplay;
    }

    /**
     * 从图库选取图片
     */
    public void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent
                    (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, TourPicConfig.REQUEST_CODE_PIC_LOCAL);
    }

    /**
     * 拍照获取图片
     */
    public void selectPicFromCamera() {
        try {
            if (!TourPicConfig.hasSDCard()) {
                Toast.makeText(getApplicationContext(), "SD卡不存在，不能拍照",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            File cameraFile = new File(TourPicConfig.getRootFilePath() + getPackageName() + "/" +
                    System.currentTimeMillis() + ".jpg");
            cameraFile.getParentFile().mkdirs();
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra
                            (MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                    TourPicConfig.REQUEST_CODE_PIC_CAMERA);
        } catch (Exception e) {
        }


    }

    public void launchCamera() {
        Intent startCustomCameraIntent = new Intent(this, CameraActivity.class);
        startActivityForResult(startCustomCameraIntent, TourPicConfig.REQUEST_CODE_PIC_CAMERA);
    }

}
