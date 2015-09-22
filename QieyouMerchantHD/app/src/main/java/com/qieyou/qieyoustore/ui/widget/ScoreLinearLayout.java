package com.qieyou.qieyoustore.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.TourStringUtil;

/**
 * Created by zhoufeng'an on 2015/8/17.
 */
public class ScoreLinearLayout extends LinearLayout {
    private int MaxStar = 5;
    private float score;
    private int fullRes;
    private int emptyRes;
    private int color;
    private String unit;
    private boolean hasText;
    public ScoreLinearLayout(Context context) {
        super(context);
        intView(null);
    }

    public ScoreLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        intView(attrs);
    }

    public ScoreLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intView(attrs);
    }

    private void intView(AttributeSet attrs) {
        if (null != attrs) {
            TypedArray a = this.getContext().obtainStyledAttributes(attrs,
                    R.styleable.ScoreStar);
            score = a.getFloat(R.styleable.ScoreStar_score, 5.0f);
            unit = a.getString(R.styleable.ScoreStar_unit);
            hasText = a.getBoolean(R.styleable.ScoreStar_hasText, true);
            fullRes = a.getResourceId(R.styleable.ScoreStar_fullScore, R.drawable.star_full);
            emptyRes = a.getResourceId(R.styleable.ScoreStar_emptyScore, R.drawable.star_empty);
            color = a.getColor(R.styleable.ScoreStar_scoreTextColor, this.getResources().getColor(R.color.white));
            a.recycle();
        }else{
            score = 5.0f;
            unit ="";
            hasText = true;
            fullRes = R.drawable.star_full;
            emptyRes = R.drawable.star_empty;
            color = this.getResources().getColor(R.color.white);
        }

        setScore(score);

    }

    public void setScore(float score) {
        this.removeAllViews();
        this.setGravity(Gravity.CENTER);
        int scoreNum = (int) score;
        LinearLayout.LayoutParams params;
        ImageView star;
        for (int i = 0; i < MaxStar; i++) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            star = new ImageView(this.getContext());
            star.setImageResource(i < scoreNum ? fullRes : emptyRes);
            params.rightMargin = DisplayUtil.dip2px(this.getContext(), 5);
            params.gravity= Gravity.CENTER;
            star.setLayoutParams(params);
            this.addView(star);
        }
        if(hasText){
            TextView view = new TextView(this.getContext());
            view.setText(String.valueOf(score) + unit);
            view.setTextColor(color);
            this.addView(view);
        }

    }
}
