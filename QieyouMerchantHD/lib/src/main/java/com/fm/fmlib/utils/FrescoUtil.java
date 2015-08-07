package com.fm.fmlib.utils;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by zhoufeng'an on 2015/7/29.
 */
public class FrescoUtil {

    public static void draweeViewRenderingEnable(SimpleDraweeView mSimpleDraweeView, Uri uri){
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(mSimpleDraweeView.getController())
                .build();
        mSimpleDraweeView.setController(controller);
    }
}
