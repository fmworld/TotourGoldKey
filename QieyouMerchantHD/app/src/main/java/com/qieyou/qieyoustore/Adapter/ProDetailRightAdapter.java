package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.tour.bean.ProductDetail;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.CodeBusinessMap;
import com.qieyou.qieyoustore.util.DateUtil;
import com.qieyou.qieyoustore.util.TourStringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class ProDetailRightAdapter extends BaseAdapter implements View.OnClickListener {


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

//        String[] tags = detail.keyword.split(",");
        String[] tags = "会觉得,hdfsj,合适的风景".split(",");
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
        ((TextView) view.findViewById(R.id.pro_detail_price)).setText(Html.fromHtml(mContext.getString(R.string.detail_price_str, detail.price)));
        ((TextView) view.findViewById(R.id.pro_detail_old_price)).setText(mContext.getString(R.string.detail_old_price_str, detail.old_price));
        ((TextView) view.findViewById(R.id.pro_detail_old_price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        ((TextView) view.findViewById(R.id.pro_detail_tip))
                .setText(Html.fromHtml(mContext.getString(R.string.detail_tip_str,
                        TourStringUtil.isNULLorEmpty(detail.facility) ? "0.00" : detail.facility)));
        ((TextView) view.findViewById(R.id.pro_detail_remainders))
                .setText(mContext.getString(R.string.detail_remainders_str, DateUtil.formatDate(Long.valueOf(detail.tuan_end_time) * 1000)));
        if (CodeBusinessMap.productStateStr(detail)) {
            (view.findViewById(R.id.detail_sale_state)).setBackgroundResource(R.drawable.bg_coners_oringe_round);
            ((TextView) view.findViewById(R.id.detail_sale_state)).setText(mContext.getString(R.string.sale_state_able));
        } else {
            (view.findViewById(R.id.detail_sale_state)).setBackgroundResource(R.drawable.bg_coners_gray_12_round);
            ((TextView) view.findViewById(R.id.detail_sale_state)).setText(mContext.getString(R.string.sale_state_unable));
        }
        (view.findViewById(R.id.detail_shelf_state)).setBackgroundResource(R.drawable.bg_coners_gray_12_round);
        return view;
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
        ((TextView) view.findViewById(R.id.pr_detail_comment_tra_value)).setText(detail.traveler);
        ((TextView) view.findViewById(R.id.pr_detail_comment_boss_value)).setText(detail.innholder);
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
                    .showHomeThirdContent(MainController.HomeMenu.COMMENTS,bundle);
        }

        if(R.id.pro_detail_item_button_layout == v.getId()&& null != scroll){
            if(State.detail == (State)v.getTag()){
                scroll.setScrollY(scroll.findViewById(R.id.detail_pro_detail_layout).getTop());
//                ((ScrollView)scroll).smoothScrollTo();
            }else{
                scroll.setScrollY(scroll.findViewById(R.id.detail_pro_attention_layout).getTop());
            }
        }
    }

    public void setScroll(View scroll) {
        this.scroll = scroll;
    }
}
