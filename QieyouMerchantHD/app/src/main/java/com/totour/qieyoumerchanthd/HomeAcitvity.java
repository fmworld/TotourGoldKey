package com.totour.qieyoumerchanthd;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fm.fmlib.Display;
import com.fm.fmlib.controllers.MainController;
import com.totour.qieyoumerchanthd.Adapter.HomeNavigationAdapter;
import com.totour.qieyoumerchanthd.fragment.HomeManager;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class HomeAcitvity extends BaseTourActivity implements MainController.NavigationUi, View.OnClickListener {
    private MainController.HomeMenu currentMenuTag;
    private MainController.HomeMenu currentContentTag;
    private HomeNavigationAdapter naviAdapter;
    private boolean meunuFragCanMove = true;
    private MainController.NavigationCallbacks mNavigationCallbacks;

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
                HomeAcitvity.this.getDisplay().showHomeMenuItem((MainController.HomeMenu) naviAdapter.getItem(position));
            }
        });
        findViewById(R.id.home_navi_user).setOnClickListener(this);
        findViewById(R.id.home_menu_layout).setOnClickListener(this);

//        ((SimpleDraweeView)findViewById(R.id.home_navi_user)).setImageURI(Uri.parse("http://img1.fjtv.net/material/news/img/2015/07/4b9db2f97de68cbf05df2517be05db1a.jpg"));
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
        mNavigationCallbacks = (MainController.NavigationCallbacks)callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getController().attachUi(this);
        mNavigationCallbacks.fetchUserInfo();
        mNavigationCallbacks.fetchStoreInfo();
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
}
