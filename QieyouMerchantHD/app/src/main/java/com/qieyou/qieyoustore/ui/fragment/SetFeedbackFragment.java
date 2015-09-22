package com.qieyou.qieyoustore.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fm.fmlib.controllers.UserController;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.fm.fmlib.utils.PackageInfoUtl;
import com.qieyou.qieyoustore.util.ToastUtil;
import com.qieyou.qieyoustore.util.TourStringUtil;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class SetFeedbackFragment extends AnimListenFragment implements UserController.SetFeedbackUi, View.OnClickListener {
    private UserController.SetFeedbackCallbacks mSetFeedbackCallbacks;
    private EditText feedback;

    public SetFeedbackFragment() {
    }


    public static SetFeedbackFragment create() {
        SetFeedbackFragment fragment = new SetFeedbackFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, null);


        view.findViewById(R.id.feedback_submit).setOnClickListener(this);
        view.findViewById(R.id.feedback_submit).setFocusable(true);
        view.findViewById(R.id.feedback_submit).setFocusableInTouchMode(true);
        view.findViewById(R.id.feedback_submit).requestFocus();
        feedback = ((EditText) view.findViewById(R.id.feedback_content));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);
    }

    @Override
    public void onPause() {
        getController().detachUi(this);
        super.onPause();
    }

    UserController getController() {
        return MerchanthdApplication.instance().getmMainController().getmUserController();
    }

    @Override
    public void onClick(View v) {
        if (R.id.feedback_submit == v.getId()) {
            String vlaue = feedback.getText().toString();
            if (TourStringUtil.isNULLorEmpty(vlaue)) {
                ToastUtil.showShortToast(this.getString(R.string.change_feedback_fail_notify));
            } else {
                mSetFeedbackCallbacks.submitFeedback(vlaue, PackageInfoUtl.getVersionName(this.getActivity()));
            }
        }
    }

    @Override
    public void setCallbacks(UserController.UserUiCallbacks callbacks) {
        mSetFeedbackCallbacks = (UserController.SetFeedbackCallbacks)callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void submitFeedbackSuccess() {
        ToastUtil.showShortToast("意见反馈提交成功");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SetFeedbackFragment.this.getActivity().finish();
            }
        }, 2000);
    }
}
