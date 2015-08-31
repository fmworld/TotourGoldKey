package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.tour.bean.ProductDetail;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.ProductTagsDialog;
import com.qieyou.qieyoustore.util.CodeBusinessMap;
import com.qieyou.qieyoustore.util.DateUtil;
import com.qieyou.qieyoustore.util.TourStringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class ProDetailRightAdapter extends BaseAdapter implements View.OnClickListener {

    private ProductTagsDialog tagsDialog;
    enum State {
        intro,
        price,
        score,
        comment,
        detail,
        attention
    }

    ProductDetail detail;
    List<State> states;
    int currentIndex = 0;
    private Context mContext;
    private AdapterView.OnItemClickListener itemClickListener;
    private View scroll;

    public ProDetailRightAdapter(Context mContext, ProductDetail detail) {
        this.mContext = mContext;
        states = new ArrayList<>();
        states.add(State.intro);
        states.add(State.price);
        states.add(State.score);
        states.add(State.comment);
        states.add(State.detail);
        states.add(State.attention);
        this.detail = detail;
    }


    @Override
    public int getCount() {
        return null == states ? 0 : states.size();
    }

    @Override
    public Object getItem(int position) {
        return states.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        State item = states.get(position);
        if (item == State.intro) {
            return initIntro();
        }

        if (item == State.price) {
            return initPriceView();
        }

        if (item == State.score) {
            return initScoreView();
        }

        if (item == State.comment) {
            return initCommentView();
        }

        if (item == State.detail) {
            return initDetailView();
        }

        if (item == State.attention) {
            return initAttentionView();
        }


        return convertView;
    }

    public void onItemClick(int index) {
        currentIndex = index;
        this.notifyDataSetInvalidated();
    }

    public void setdata(ProductDetail detail) {
        this.detail = detail;
        this.notifyDataSetInvalidated();
    }

    private View initIntro() {
        View view = View.inflate(mContext, R.layout.list_item_pro_detail_intro, null);
        if (null == detail) {
            return view;
        }
        ((TextView) view.findViewById(R.id.detail_intro_content)).setText(detail.content);
        LinearLayout linear = ((LinearLayout) view.findViewById(R.id.detail_intro_tags));

        String[] tags = detail.keyword.split(",");
//        String[] tags = "会觉得,hdfsj,合适的风景".split(",");
        if (null != tags) {
            for (String item : tags) {
                if (!TourStringUtil.isNULLorEmpty(item))
                    linear.addView(initIntroTag(item));
            }
        }

        return view;
    }

    private View initIntroTag(String tagS) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.rightMargin = DisplayUtil.dip2px(mContext, 6);
        TextView tag = new TextView(mContext);
        tag.setText(tagS);
        tag.setBackgroundResource(R.drawable.bg_coners_black13_round);
        tag.setTextColor(mContext.getResources().getColor(R.color.white_1));
        tag.setTextSize(12);
        tag.setPadding(2, 2, 2, 2);
        tag.setLayoutParams(params);
        return tag;
    }

    private View initPriceView() {
        View view = View.inflate(mContext, R.layout.list_item_pro_detail_price, null);
        if (null == detail) {
            return view;
        }
        ((TextView) view.findViewById(R.id.pro_detail_price))
                .setText(Html.fromHtml(mContext.getString(R.string.detail_price_str, detail.price)));
        ((TextView) view.findViewById(R.id.pro_detail_old_price))
                .setText(mContext.getString(R.string.detail_old_price_str, detail.old_price));
        ((TextView) view.findViewById(R.id.pro_detail_old_price))
                .getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        String showTip = TourApplication.instance().getDaoProperty().getValue(ProductState.Tip);
        if (null == showTip || "0".equals(showTip)) {
            (view.findViewById(R.id.pro_detail_tip)).setVisibility(View.GONE);
        } else {
            (view.findViewById(R.id.pro_detail_tip)).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.pro_detail_tip))
                    .setText(Html.fromHtml(mContext.getString(R.string.detail_tip_str,
                            TourStringUtil.isNULLorEmpty(detail.agent) ? "0.00" : detail.agent)));
        }


        ((TextView) view.findViewById(R.id.pro_detail_remainders))
                .setText(mContext.getString(R.string.detail_remainders_str,
                        DateUtil.formatDate(Long.valueOf(detail.tuan_end_time) * 1000)));
        (view.findViewById(R.id.detail_sale_state))
                .setBackgroundResource(CodeBusinessMap.productStateStr(detail) ?
                        R.drawable.bg_coners_oringe_round : R.drawable.bg_coners_gray_12_round);
        ((TextView) view.findViewById(R.id.detail_sale_state))
                .setText(CodeBusinessMap.productGetStateStr(detail));
        initSaleState((TextView) view.findViewById(R.id.detail_sale_state),CodeBusinessMap.productStateStr(detail), detail.product_id);
        initShelfState((TextView) view.findViewById(R.id.detail_shelf_state), "1".equals(detail.on_shelves));

        return view;
    }

    private void initSaleState(TextView view, boolean saleable,String pro_id) {
        if(saleable){
            view.setBackgroundResource(R.drawable.bg_coners_oringe_round);
            view.setText(mContext.getString(R.string.sale_state_able));
            view.setTag(pro_id);
            view.setOnClickListener(this);
        }else{
            view.setBackgroundResource(R.drawable.bg_coners_gray_12_round);
            view.setText(CodeBusinessMap.productUnsaleStateStr(detail));
            view.setOnClickListener(null);
        }
    }

    private void initShelfState(TextView view, boolean shelfed) {
        if (shelfed) {
            view.setText(mContext.getString(R.string.mall_pro_has_shelved));
            view.setBackgroundResource(R.drawable.bg_coners_gray_round);
            view.setTextColor(mContext.getResources().getColor(R.color.green));
            view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mall_pro_has_shelf
                    , 0, 0, 0);
            view.setOnClickListener(null);
        } else {
            view.setText(mContext.getString(R.string.mall_pro_to_shelve));
            view.setBackgroundResource(R.drawable.bg_coners_bule_round);
            view.setTextColor(mContext.getResources().getColor(R.color.white));
            view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mall_pro_to_shelf
                    , 0, 0, 0);
            view.setOnClickListener(this);
        }
    }

    private View initScoreView() {
        View view = View.inflate(mContext, R.layout.list_item_pro_detail_score, null);
        if (null == detail) {
            return view;
        }
        ((TextView) view.findViewById(R.id.pro_detail_comment_num)).setText(mContext.getString(R.string.detail_score_comments_str, detail.comments));

        view.setOnClickListener(this);
        return view;
    }

    private View initCommentView() {
        View view = View.inflate(mContext, R.layout.list_item_pro_detail_comment, null);
        if (null == detail) {
            return view;
        }
        ((TextView) view.findViewById(R.id.pr_detail_comment_tra_value))
                .setText(TourStringUtil.isNULLorEmpty(detail.traveler) ?
                        mContext.getString(R.string.detail_comment_empty) : detail.traveler);
        ((TextView) view.findViewById(R.id.pr_detail_comment_boss_value))
                .setText(TourStringUtil.isNULLorEmpty(detail.innholder) ?
                        mContext.getString(R.string.detail_comment_empty) : detail.innholder);
        return view;
    }

    private View initDetailView() {
        View view = View.inflate(mContext, R.layout.list_item_pro_detail_button, null);
        if (null == detail) {
            return view;
        }
        ((TextView) view.findViewById(R.id.pro_detail_item_button)).setText(mContext.getString(R.string.detail_detail_title));
        ((TextView) view.findViewById(R.id.pro_detail_item_button)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.detail_pro_detail, 0, 0, 0);
        view.setTag(State.detail);
        view.setOnClickListener(this);
        return view;
    }

    private View initAttentionView() {
        View view = View.inflate(mContext, R.layout.list_item_pro_detail_button, null);
        if (null == detail) {
            return view;
        }
        ((TextView) view.findViewById(R.id.pro_detail_item_button)).setText(mContext.getString(R.string.detail_attention_title));
        ((TextView) view.findViewById(R.id.pro_detail_item_button)).setCompoundDrawablesWithIntrinsicBounds(R.drawable.detail_pro_attention, 0, 0, 0);
        view.setTag(State.attention);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (R.id.pro_detail_score_layout == v.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("item", detail.product_id);
            bundle.putString("score", detail.score);
            bundle.putString("comments", detail.comments);
            bundle.putString("1", detail.comment_score.one);
            bundle.putString("2", detail.comment_score.two);
            bundle.putString("3", detail.comment_score.three);
            bundle.putString("4", detail.comment_score.four);
            bundle.putString("5", detail.comment_score.five);
            ((BaseTourActivity) mContext).getDisplay()
                    .showHomeThirdContent(MainController.HomeMenu.COMMENTS, bundle);
        } else if (R.id.pro_detail_item_button_layout == v.getId() && null != scroll) {
            scroll.setScrollY(scroll.findViewById(State.detail == (State) v.getTag() ?
                    R.id.detail_pro_detail_layout : R.id.detail_pro_attention_layout).getTop());
//
        } else if (R.id.detail_sale_state == v.getId()) {
            TourApplication.instance().getmBus()
                    .post(new ProductState.ProductFetchSubmitUrlEvent((String)v.getTag()));
        } else if (R.id.detail_shelf_state == v.getId()) {
            if (null == tagsDialog) {
                tagsDialog = new ProductTagsDialog(mContext, R.style.translucent);
            }
            tagsDialog.setConfirmListener(new ProductTagsDialog.ConfirmListener() {
                @Override
                public void tagSelected(String tag_id) {
                    TourApplication.instance().getmBus()
                            .post(new ProductState.ProductChangeShelfStateEvent
                                    (tag_id,detail.product_id,"up" ));
                }
            });
            tagsDialog.show();
        }
    }

    public void setScroll(View scroll) {
        this.scroll = scroll;
    }
}
