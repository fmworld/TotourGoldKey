package com.qieyou.qieyoustore;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.fm.fmlib.Display;
import com.fm.fmlib.state.UserState;

/**
 * Created by zhoufeng'an on 2015/8/12.
 */
public class SettingActivity extends BaseTourActivity implements View.OnClickListener {


    protected int getContentViewLayoutId() {
        return R.layout.activity_setting_dialog;
    }

    protected void handleIntent(Intent intent, Display display) {
        findViewById(R.id.setting_close).setOnClickListener(this);
        String type = intent.getStringExtra("type");
        initTitle(type);
        display.showSettingItem(type);
    }

    private void initTitle(String type) {
        if (UserState.Setting.about.toString().equals(type)) {
            ((TextView)findViewById(R.id.setting_title))
                    .setText(this.getString(R.string.home_setting_about));

        }
        else if(UserState.Setting.changepwd.toString().equals(type)) {
            ((TextView)findViewById(R.id.setting_title))
                    .setText(this.getString(R.string.change_pwd_title));

        }
        else if(UserState.Setting.feedback.toString().equals(type)) {
            ((TextView)findViewById(R.id.setting_title))
                    .setText(this.getString(R.string.change_feedback_title));
        }
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
}
