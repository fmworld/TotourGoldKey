package com.qieyou.qieyoustore;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fm.fmlib.Display;
import com.fm.fmlib.controllers.MainController;
import com.qieyou.qieyoustore.Adapter.HomeNavigationAdapter;
import com.qieyou.qieyoustore.bean.HomeNaviItem;
import com.qieyou.qieyoustore.ui.fragment.HomeMall;
import com.qieyou.qieyoustore.util.TourPicConfig;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class HomeAcitvity extends BaseTourActivity implements MainController.NavigationUi, View.OnClickListener {
    private MainController.HomeMenu currentMenuTag;
    private MainController.HomeMenu currentContentTag;
    private MainController.HomeMenu currentSecondContentTag;
    private MainController.HomeMenu currentThirdContentTag;

    private HomeNavigationAdapter naviAdapter;
    private boolean meunuFragCanMove = true;
    private MainController.NavigationCallbacks mNavigationCallbacks;
    private boolean firstResume = true;

    protected int getContentViewLayoutId() {
        return R.layout.activity_home;
    }

    protected void handleIntent(Intent intent, Display display) {
        initNavigation();
    }

    private void initNavigation() {
        naviAdapter = new HomeNavigationAdapter(this);
        ((ListView) findViewById(R.id.home_items)).setAdapter(naviAdapter);
        ((ListView) findViewById(R.id.home_items)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                naviAdapter.onItemClick(position);
                HomeAcitvity.this.getDisplay()
                        .showHomeMenuItem(((HomeNaviItem) naviAdapter.getItem(position)).tag,
                                ((HomeNaviItem) naviAdapter.getItem(position)).title);
            }
        });
        findViewById(R.id.home_navi_user).setOnClickListener(this);
        findViewById(R.id.home_menu_layout).setOnClickListener(this);
        getDisplay().showHomeMenuItem(MainController.HomeMenu.STORE_GALLERY);
    }


    public void selectNavigationItem(MainController.HomeMenu index) {
        if (null != naviAdapter && null != index) {
            naviAdapter.onItemClick(index.ordinal());
        }
    }

    public void selectCurrentNavigationItem() {
        selectNavigationItem(this.getCurrentContentTag());
    }

    @Override
    public void onClick(View v) {
        if (R.id.home_navi_user == v.getId()) {
            this.getDisplay().showHomeProfileItem(MainController.HomeMenu.PERSON_INFO);
        } else if (R.id.home_menu_layout == v.getId()) {
            this.getDisplay().hideHomeProfile();
        }
    }

    public boolean isMeunuFragCanMove() {
        return meunuFragCanMove;
    }

    public void setMeunuFragCanMove(boolean meunuFragCanMove) {
        this.meunuFragCanMove = meunuFragCanMove;
    }

    @Override
    public void fetchUserInfo() {

    }

    @Override
    public void setCallbacks(MainController.MainUiCallbacks callbacks) {
        mNavigationCallbacks = (MainController.NavigationCallbacks) callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        getController().attachUi(this);
        if (firstResume) {

            mNavigationCallbacks.fetchProOptions();
            mNavigationCallbacks.fetchUserInfo();
            mNavigationCallbacks.fetchStoreInfo();
            mNavigationCallbacks.fetchTagList();
            firstResume = false;
        }

    }

    @Override
    protected void onPause() {
        getController().detachUi(this);
        super.onPause();
    }

    MainController getController() {
        return MerchanthdApplication.instance().getmMainController();
    }

    public MainController.HomeMenu getCurrentMenuTag() {
        return currentMenuTag;
    }

    public void setCurrentMenuTag(MainController.HomeMenu currentMenuTag) {
        this.currentMenuTag = currentMenuTag;
    }

    public MainController.HomeMenu getCurrentContentTag() {
        return currentContentTag;
    }

    public void setCurrentContentTag(MainController.HomeMenu currentContentTag) {
        this.currentContentTag = currentContentTag;
    }

    public MainController.HomeMenu getCurrentSContentTag() {
        return currentSecondContentTag;
    }

    public void setCurrentSContentTag(MainController.HomeMenu currentSContentTag) {
        this.currentSecondContentTag = currentSContentTag;
    }

    public void setCurrentThirdContentTag(MainController.HomeMenu currentThirdContentTag) {
        this.currentThirdContentTag = currentThirdContentTag;
    }

    public MainController.HomeMenu getCurrentThirdContentTag() {
        return currentThirdContentTag;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("take pic", "onActivityResult  " + requestCode+" resultCode "+resultCode);

        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK) { //
            if (requestCode == TourPicConfig.REQUEST_CODE_PIC_CAMERA) { // 发送照片
                if (data != null) {
                    Uri selectedImage = data.getData();
                    Log.v("take pic", "REQUEST_CODE_PIC_CAMERA " + data.getData());
                    getDisplay().showProLocalImg(selectedImage);

                }

            } else if (requestCode == TourPicConfig.REQUEST_CODE_PIC_LOCAL) { // 发送本地图片
                if (data != null) {
                    Log.v("take pic", "REQUEST_CODE_PIC_LOCAL " + data.getData());
                    Uri selectedImage = data.getData();
                    getDisplay().showProLocalImg(selectedImage);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Fragment fragment = this.getFragmentManager().findFragmentByTag(this.getCurrentContentTag().toString());
//        if (null != fragment && fragment instanceof HomeMall) {
//            if (((HomeMall) fragment).isPopWindowShowed()) {
//                ((HomeMall) fragment).dismissPopWindow();
//                return true;
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }


}

