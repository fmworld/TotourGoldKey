package com.qieyou.qieyoustore.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.utils.DisplayUtil;
import com.fm.fmlib.utils.StringUtils;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.TourStringUtil;

/**
 * Created by zhoufeng'an on 2015/8/17.
 */
public class KeywordsLinearLayout extends LinearLayout implements View.OnClickListener, TextView.OnEditorActionListener {
    private EditText editor;
    private int maxWords = 5;

    public KeywordsLinearLayout(Context context) {
        super(context);
        initHint();
    }

    public KeywordsLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHint();
    }

    public KeywordsLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHint();
    }

    public void addKeyWord(String tag) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, DisplayUtil.dip2px(this.getContext(), 75));
        params.rightMargin = DisplayUtil.dip2px(this.getContext(), 7);
        View view = View.inflate(this.getContext(), R.layout.widget_pro_keyword_item, null);
        ((TextView) view.findViewById(R.id.pro_add_keyword_value)).setText(tag);
        view.findViewById(R.id.pro_delete_keyword).setOnClickListener(this);
        view.setLayoutParams(params);
        view.findViewById(R.id.pro_delete_keyword).setTag(getChildCount() - 2);
        this.addView(view, this.getChildCount() - 2);
    }
    public void addKeyWords(String tags) {
        if(null != tags){
            String [] words = tags.split(",");
            for(String item : words){
                addKeyWord(item);
            }
        }
    }

    private void initHint() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.rightMargin = DisplayUtil.dip2px(this.getContext(), 7);
        View view = View.inflate(this.getContext(), R.layout.widget_pro_keyword_add, null);
        view.setOnClickListener(this);
        view.setLayoutParams(params);
        this.addView(view);
        view.setTag(getChildCount() - 1);
        TextView view_hint = new TextView(this.getContext());
        view_hint.setText(this.getContext().getString(R.string.home_mgr_pro_ae_keyword_hint));
        view_hint.setTextColor(this.getContext().getResources().getColor(R.color.home_mgr_pro_ae_keyword_hint));
        this.addView(view_hint);
//        addKeyWord("第一个");
//        addKeyWord("第二个");
    }

    @Override
    public void onClick(View v) {
        if (R.id.pro_delete_keyword == v.getId()) {
            this.removeViewAt((int) v.getTag());
        } else if (R.id.pro_add_keyword_layout == v.getId()) {
            initAddTextEdit();
        }
    }

    private void initAddTextEdit() {
        if (this.getChildCount() >=maxWords+2){
            Toast.makeText(TourApplication.instance(),this.getContext().getString(R.string.home_mgr_pro_ae_keyword_max_str,maxWords),Toast.LENGTH_SHORT).show();
            return;
        }
            addNewTagUI();

        editor = new EditText(this.getContext());
        editor.setMinWidth(DisplayUtil.dip2px(this.getContext(), 50));
        initEditKeyEvent();
        editor.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_ACTION_NONE);
        this.addView(editor, this.getChildCount() - 2);
        editor.setOnEditorActionListener(this);
        editor.requestFocus();
    }

    private void addNewTagUI() {

        if (null == editor) {
            return;
        }
        String value = editor.getText().toString();
        if (!TourStringUtil.isNULLorEmpty(value)) {
            editor.setOnEditorActionListener(null);
            this.removeView(editor);
            editor = null;
            this.addKeyWord(value);
        }
//        Log.v("keywords", "keywords " + getKeyWords()) ;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == KeyEvent.ACTION_DOWN || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NONE) {
            addNewTagUI();
            return true;
        }
        return false;
    }

    private void initEditKeyEvent() {
        if (null == editor) {
            return;
        }
        editor.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == keyCode && event.getAction() == KeyEvent.ACTION_DOWN) {
                    addNewTagUI();
                    return true;
                }
                return false;
            }
        });
    }

    public String getKeyWords() {
        if (2 < this.getChildCount()) {
            TextView tag;
            StringBuffer words = new StringBuffer();

            for (int i = 0; i < this.getChildCount() - 3; i++) {
                tag = (TextView) getChildAt(i).findViewById(R.id.pro_add_keyword_value);
                if (null != tag) {
                    words.append(tag.getText().toString()).append(",");
                }
            }

            if (this.getChildCount() - 3 >= 0) {
                tag = (TextView) getChildAt(this.getChildCount() - 3).findViewById(R.id.pro_add_keyword_value);
                if (null != tag) {
                    words.append(tag.getText().toString());
                }
            }
            return words.toString();
        }

        return "";
    }

    public void setMaxWords(int maxWords) {
        this.maxWords = maxWords;
    }
}
