package com.fm.fmlib;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.fm.fmlib.controllers.UserController;
import com.fm.fmlib.tasks.GoodsFetchCommentDetailRunnable;
import com.fm.fmlib.tasks.GoodsFetchCommentListRunnable;
import com.fm.fmlib.tasks.GoodsFetchCommentReplyListRunnable;
import com.fm.fmlib.tasks.GoodsFetchDetailRunnable;
import com.fm.fmlib.tasks.GoodsFetchHomeRunnable;
import com.fm.fmlib.tasks.GoodsFetchShopTagsRunnable;
import com.fm.fmlib.tasks.GoodsProductInfo4EditRunnable;
import com.fm.fmlib.tasks.GoodsShelved4OtherRunnable;
import com.fm.fmlib.tasks.InnMngFetchHomeRunnable;
import com.fm.fmlib.tasks.TrasInnManagerRunnable;
import com.fm.fmlib.tasks.TrasSbmOrderRunnable;
import com.fm.fmlib.tasks.UserFindPwdRuunable;
import com.fm.fmlib.tasks.UserLoginInRuunable;
import com.fm.fmlib.tasks.UserLoginOutRuunable;
import com.fm.fmlib.tour.Service.GoodsService;
import com.fm.fmlib.tour.params.GoodsFetchHomeParams;

public class MainActivity extends AppCompatActivity {
    SimpleDraweeView my_image_view;
private UserController usrController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_image_view = (SimpleDraweeView) this.findViewById(R.id.my_image_view);
        my_image_view.setAspectRatio(1f);
//        my_image_view.setImageURI(Uri.parse("http://img1.fjtv.net/material/news/img/2015/07/4b9db2f97de68cbf05df2517be05db1a.jpg"));
        GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = hierarchyBuilder.setRoundingParams(new RoundingParams().setCornersRadius(350)).build();
        my_image_view.getHierarchy().setFadeDuration(0);
        hierarchy.setFadeDuration(0);
        my_image_view.setHierarchy(hierarchy);
        usrController = new UserController();
    }

    public void onResume(){
        super.onResume();
        usrController.atachUI();
    }

    public void onPause(){
        super.onPause();
        usrController.unatachUI();
    }
    private void draweeViewRenderingEnable(SimpleDraweeView mSimpleDraweeView) {
        Uri uri = Uri.parse("http://i2.hoopchina.com.cn/blogfile/201306/12/137100806559026.jpg");
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

    public void showPic(View view) {
//        draweeViewRenderingEnable(my_image_view);
//        my_image_view.setImageURI(Uri.parse("http://i2.hoopchina.com.cn/blogfile/201306/12/137100806559026.jpg"));

//        findPwd();
//        transfer2SmtOrder();
//        transfer2innManager();

//        fetchInnMngHomePage();

        GoodsFetchTags();
//        goodsShelvedOther();
//        goodsFetchHome();
//        FetchGoodsDetail();
//        FetchGoodsCommentDetail();
//        goodsFetchHome();
//        fetchGoodsCommentList();
//        fetchGoodsCommentDetail();

//        fetchGoodsCommentReplyList();
//        goodsProductInfo4Edit();
    }


    public void operate(View view) {
        if (R.id.button1 == view.getId()) {
            loginIn();
        } else if (R.id.button2 == view.getId()) {
            loginOut();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loginIn() {
        FmApplication.instance().getmExecutor().execute(new UserLoginInRuunable("18612540330", "qieyou"));
    }

    private void findPwd() {
        FmApplication.instance().getmExecutor().execute(new UserFindPwdRuunable("18612540339"));
    }

    private void loginOut() {
        FmApplication.instance().getmExecutor().execute(new UserLoginOutRuunable());
    }

    private void transfer2SmtOrder() {
        FmApplication.instance().getmExecutor().execute(new TrasSbmOrderRunnable("64"));
    }

    private void transfer2innManager() {
        FmApplication.instance().getmExecutor().execute(new TrasInnManagerRunnable());
    }

    private void fetchInnMngHomePage() {
        FmApplication.instance().getmExecutor().execute(new InnMngFetchHomeRunnable());
    }

    private void GoodsFetchTags() {
        FmApplication.instance().getmExecutor().execute(new GoodsFetchShopTagsRunnable("0", "3", ""));
    }

    private void goodsShelvedOther() {
        FmApplication.instance().getmExecutor().execute(new GoodsShelved4OtherRunnable("510", "7"));
    }

    private void goodsFetchHome() {
        GoodsFetchHomeParams homeParams = new GoodsFetchHomeParams();
        homeParams.perpage = 5;
        homeParams.page = 0;
        FmApplication.instance().getmExecutor().execute(new GoodsFetchHomeRunnable(homeParams));
    }

//    private void FetchGoodsDetail() {
//        FmApplication.instance().getmExecutor().execute(new GoodsFetchDetailRunnable("1"));
//    }

    private void fetchGoodsCommentDetail() {
        FmApplication.instance().getmExecutor().execute(new GoodsFetchCommentDetailRunnable("517"));
    }

    private void fetchGoodsCommentList(){
        FmApplication.instance().getmExecutor().execute(new GoodsFetchCommentListRunnable("1", GoodsService.CommentType.pic.toString(), "6","20"));

    }

    private void fetchGoodsCommentReplyList(){
        FmApplication.instance().getmExecutor().execute(new GoodsFetchCommentReplyListRunnable("3","item","0","10"));
    }

    private void goodsProductInfo4Edit(){
        FmApplication.instance().getmExecutor().execute(new GoodsProductInfo4EditRunnable("1"));
    }
}
