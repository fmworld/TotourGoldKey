package com.tour;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.tasks.UserSetNewPwdRuunable;
import com.fm.fmlib.tour.Totour0888;
import com.fm.fmlib.utils.BackgroundExecutor;
import com.fm.fmlib.utils.provider.BackgroundExecutorProvider;
import com.fm.fmlib.utils.provider.Networkprovider;

/**
 * Created by zhoufeng'an on 2015/8/7.
 */
public class TourTest {
    private BackgroundExecutor mExecutor;

    public TourTest() {
        this.mExecutor = BackgroundExecutorProvider.providerBackgroundExecutor();
        TourApplication.testGenerate();
    }

    public void changePassword() {
        mExecutor.execute(new UserSetNewPwdRuunable("18612540330", "1234", "qieyou"));
    }
}
