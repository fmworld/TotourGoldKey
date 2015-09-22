package com.qieyou.qieyoustore;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fm.fmlib.Display;
import com.fm.fmlib.controllers.MainController;
import com.qieyou.qieyoustore.Adapter.HomeNavigationAdapter;
import com.qieyou.qieyoustore.bean.HomeNaviItem;

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
    private long backIntentStamp=0;

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


    public void onBackPressed(){
        if(getDisplay().hideHomeProfile()){
            return;
        }

        if(getDisplay().hideHomeThirdContent()){
            return;
        }

        if(getDisplay().hideHomeSecondContent()){
            return;
        }

        long currentTime = System.currentTimeMillis();

        if(currentTime-backIntentStamp <= 2000){
            finish();
        }else{
            backIntentStamp = System.currentTimeMillis();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}

