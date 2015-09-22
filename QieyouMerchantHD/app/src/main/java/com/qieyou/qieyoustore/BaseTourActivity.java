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

import net.soulwolf.image.picturelib.PictureFrom;
import net.soulwolf.image.picturelib.PictureProcess;
import net.soulwolf.image.picturelib.listener.OnPicturePickListener;

import java.io.File;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class BaseTourActivity extends AppCompatActivity implements OnPicturePickListener {
    public final String TAG = this.getClass().getSimpleName();
    private MainController mMainController;
    private Display mDisplay;
    private LoadDialog load_dialog;
    private PictureProcess mPictureProcess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Request Progress Bar in Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.v(TAG, "density " + DisplayUtil.getDensity(this));
        super.onCreate(savedInstanceState);

        setContentView(getContentViewLayoutId());
        Log.v("AnimListenFragment", "onCreate ");
        mMainController = MerchanthdApplication.instance().getmMainController();
        mDisplay = new AndroidDisplay(this);
        handleIntent(getIntent(), getDisplay());
    }

    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.v("AnimListenFragment", "onNewIntent " );
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

    public void dismissLoading() {
        if (null != load_dialog) {
            load_dialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainController.attachDisplay(mDisplay);
        mMainController.init();
    }

    @Override
    protected void onPause() {
        mMainController.suspend();
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
    public void selectPicFromLocal(int max) {
//        Intent intent;
//        if (Build.VERSION.SDK_INT < 19) {
//            intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//        } else {
//            intent = new Intent
//                    (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        }
//        startActivityForResult(intent, TourPicConfig.REQUEST_CODE_PIC_LOCAL);
        if(null == mPictureProcess){
            mPictureProcess = new PictureProcess(this);
            // Gallery Select!
        }
        mPictureProcess.setPictureFrom(PictureFrom.GALLERY);
        mPictureProcess.setClip(false);
        mPictureProcess.setMaxPictureCount(max);
        mPictureProcess.execute(this);
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
            File cameraFile = new File(this.getCacheDir() + "/" +
                    System.currentTimeMillis() + ".jpg");
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra
                            (MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                    TourPicConfig.REQUEST_CODE_PIC_CAMERA);
        } catch (Exception e) {
        }
    }

    public void launchCamera() {
        Intent startCustomCameraIntent = new Intent(this, CameraActivity.class);
        startActivityForResult(startCustomCameraIntent, TourPicConfig.REQUEST_CODE_PIC_CAMERA);
//        if(null == mPictureProcess){
//            mPictureProcess = new PictureProcess(this);
//            //Now Photograph
//        }
//        mPictureProcess.setPictureFrom(PictureFrom.CAMERA);
//        mPictureProcess.setClip(false);
//        mPictureProcess.setMaxPictureCount(1);
//        mPictureProcess.execute(this);
    }

    @Override
    public void onSuccess(List<String> list) {
//        getDisplay().showProLocalImg(selectedImage);
        Log.v("testcamera", "onSuccess ");
        for(String uri : list){
            getDisplay().showProLocalImg(Uri.fromFile(new File(uri)));
            Log.v("testcamera", "uri "+  uri);
        }
    }

    @Override
    public void onError(Exception e) {
        Log.v("testcamera", "onError ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { //
            if (requestCode == TourPicConfig.REQUEST_CODE_PIC_CAMERA) { // 发送照片
                if (data != null) {
                    Uri selectedImage = data.getData();
                    Log.v("take pic", "REQUEST_CODE_PIC_CAMERA " + data.getData());
                    getDisplay().showProLocalImg(selectedImage);
                    return;
                }

            }
        }

        if(null != mPictureProcess&&requestCode != TourPicConfig.REQUEST_CODE_PIC_CAMERA){
            mPictureProcess.onProcessResult(requestCode, resultCode, data);
        }
    }
}
