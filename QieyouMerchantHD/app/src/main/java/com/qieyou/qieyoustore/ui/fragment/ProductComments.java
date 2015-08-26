package com.qieyou.qieyoustore.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fm.fmlib.controllers.ProductController;
import com.fm.fmlib.tour.bean.ProComment;
import com.qieyou.qieyoustore.Adapter.ProCommentAdapter;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class ProductComments extends AnimListenFragment implements ProductController.CommentsUi, View.OnClickListener {
    private final int PERPAGE = 10;
    private ProductController.ProductCommentsCallbacks mProductCommentsCallbacks;
    private String item;
    private View content;
    private int page;
    private ProCommentAdapter commentsAdapter;
    public ProductComments() {
    }

    public static ProductComments create() {
        ProductComments fragment = new ProductComments();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_product_comments, null);
        (content.findViewById(R.id.comments_layout)).setOnClickListener(this);
        item = (String) this.getArguments().get("item");
        String score = (String) this.getArguments().get("score");
        String comments = (String) this.getArguments().get("comments");
        String star1 = (String) this.getArguments().get("1");
        String star2 = (String) this.getArguments().get("2");
        String star3 = (String) this.getArguments().get("3");
        String star4 = (String) this.getArguments().get("4");
        String star5 = (String) this.getArguments().get("5");
        ((TextView)content.findViewById(R.id.comments_score_vlaue)).setText(score);
        ((TextView)content.findViewById(R.id.comments_num_vlaue)).setText(this.getString(R.string.pro_comments_score_commentnum,comments));
        ((TextView)content.findViewById(R.id.score_level_5_value)).setText(star5);
        ((TextView)content.findViewById(R.id.score_level_4_value)).setText(star4);
        ((TextView)content.findViewById(R.id.score_level_3_value)).setText(star3);
        ((TextView)content.findViewById(R.id.score_level_2_value)).setText(star2);
        ((TextView)content.findViewById(R.id.score_level_1_value)).setText(star1);
        (content.findViewById(R.id.pro_comments_back)).setOnClickListener(this);
        content.setOnClickListener(this);
        this.setAnimationListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (null != mProductCommentsCallbacks) {
                    mProductCommentsCallbacks.fetchProComments(item, String.valueOf(page), String.valueOf(PERPAGE));
                }
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        ((RecyclerView) content.findViewById(R.id.comment_list)).setLayoutManager(manager);
        commentsAdapter = new ProCommentAdapter(this.getActivity());
        ((RecyclerView) content.findViewById(R.id.comment_list)).setAdapter(commentsAdapter);
        return content;
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

    ProductController getController() {
        return MerchanthdApplication.instance().getmMainController().getProductController();
    }


    @Override
    public void setCallbacks(ProductController.ProductUiCallbacks callbacks) {
        mProductCommentsCallbacks = (ProductController.ProductCommentsCallbacks) callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (R.id.pro_comments_back == v.getId()) {
            ((BaseTourActivity) this.getActivity()).getDisplay().hideHomeThirdContent();
        } else if (content == v) {
            ((BaseTourActivity) this.getActivity()).getDisplay().hideHomeThirdContent();
        }
    }


    @Override
    public void updateTabBar() {
    }

    @Override
    public void refreshComment(List<ProComment> detail) {
//        ((TextView)content.findViewById(R.id.comments_score_vlaue)).setText();
//        ((TextView)content.findViewById(R.id.comments_num_vlaue)).setText();
//        ((TextView)content.findViewById(R.id.score_level_5_value)).setText();
//        ((TextView)content.findViewById(R.id.score_level_4_value)).setText();
//        ((TextView)content.findViewById(R.id.score_level_3_value)).setText();
//        ((TextView)content.findViewById(R.id.score_level_2_value)).setText();
//        ((TextView)content.findViewById(R.id.score_level_1_value)).setText();
        List<ProComment> comments = new ArrayList<>();
        ProComment comment;
        for(int i = 0; i <10;i++){
            comment = new ProComment();
            comment.headimg="http://www.sixqq.com/uploads/allimg/131216/1-131216153502-50.jpg";
            comment.points="4.6";
            comment.user_name="龇牙";
            comment.note="世界就是这样，需要你去坚持";
            comment.picture="http://img1.cache.netease.com/catchpic/3/36/363DED42C3AA0CE04BF2C9CB781E7C7E.jpg,http://c.hiphotos.baidu.com/image/pic/item/7a899e510fb30f2478d27203ca95d143ad4b0361.jpg,http://a.hiphotos.baidu.com/image/pic/item/0df431adcbef7609937636822cdda3cc7cd99eaa.jpg,http://h.hiphotos.baidu.com/image/pic/item/80cb39dbb6fd5266ff2aa215a918972bd40736a9.jpg";
            comments.add(comment);
        }

        commentsAdapter.setComments(comments);
    }
}
