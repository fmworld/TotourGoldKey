package com.qieyou.qieyoustore.util;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qieyou.qieyoustore.ui.widget.ScoreLinearLayout;

/**
 * Created by zfa on 2015/7/24.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder  {
    public SparseArray<View> mViews;
    public View mConvertView;

    public RecyclerViewHolder(View view){
        super(view);
        this.mViews = new SparseArray<View>();
        this.mConvertView = view;
    }


    protected <T extends View> T findViewById(int viewId){
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }

    public RecyclerViewHolder setVisibility(int viewId, int state){
        findViewById(viewId).setVisibility(state);
        return this;
    }

    public RecyclerViewHolder setText(int viewId, String value){
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }

    public RecyclerViewHolder setText(int viewId, Spanned value){
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }



    public RecyclerViewHolder setFlags(int viewId, int value){
        TextView view = findViewById(viewId);
        view.getPaint().setFlags(value);;
        return this;
    }

    public RecyclerViewHolder setBackgroundColor(int viewId, int color){
        View view = findViewById(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public RecyclerViewHolder setBackgroundResource(int viewId, int resource){
        View view = findViewById(viewId);
        view.setBackgroundResource(resource);
        return this;
    }

    public RecyclerViewHolder setText(int viewId, String value, Object tag){
        TextView view = findViewById(viewId);
        view.setText(value);
        view.setTag(tag);
        return this;
    }

    public RecyclerViewHolder setImageFromUrl(int viewId, String url, int res){
        SimpleDraweeView imageView = findViewById(viewId);
        imageView.setImageURI(Uri.parse(url));
        return this;
    }

    public RecyclerViewHolder setImageFromRes(int viewId, int res){
        ImageView imageView = findViewById(viewId);
        imageView.setImageResource(res);
        return this;
    }



    public RecyclerViewHolder setClickListener(int viewId, View.OnClickListener listener){
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setClickListener(int viewId, View.OnClickListener listener, Object tag){
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        view.setTag(tag);
        return this;
    }

    public RecyclerViewHolder setTag(int viewId, Object tag){
        View view = findViewById(viewId);
        view.setTag(tag);
        return this;
    }

    public RecyclerViewHolder setCompoundDrawablesWithIntrinsicBounds(int viewId, int left, int top, int right, int bottom){
        ((TextView)findViewById(viewId)).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        return this;
    }

    public RecyclerViewHolder setAdapter(int viewId, BaseAdapter adapter){
        ((AbsListView)findViewById(viewId)).setAdapter(adapter);
        return this;
    }

    public RecyclerViewHolder setClickListener(int viewId, AdapterView.OnItemClickListener listener){
        ((AbsListView)findViewById(viewId)).setOnItemClickListener(listener);
        return this;
    }
    public RecyclerViewHolder setClickListener(int viewId, AdapterView.OnItemClickListener listener, Object tag){
        AbsListView absListView = findViewById(viewId);
        absListView.setOnItemClickListener(listener);
        absListView.setTag(tag);
        return this;
    }

    public RecyclerViewHolder setMaxLines(int viewId, int lines){
        ((TextView)findViewById(viewId)).setMaxLines(lines);
        return this;
    }

    public RecyclerViewHolder setEllipsize(int viewId, TextUtils.TruncateAt at){
        ((TextView)findViewById(viewId)).setEllipsize(at);
        return this;
    }

    public RecyclerViewHolder setMyLayoutAmountInfo(int viewId, String title, String info){
//        ((MyLayoutAmountInfo)findViewById(viewId)).setTitleText(title);
//        ((MyLayoutAmountInfo)findViewById(viewId)).setAmountText(info);
        return this;
    }

    public RecyclerViewHolder setImageURI(int viewId, Uri uri){
        ((SimpleDraweeView)findViewById(viewId)).setImageURI(uri);
        return this;
    }

    public RecyclerViewHolder setScore(int viewId, float score){
        ((ScoreLinearLayout)findViewById(viewId)).setScore(score);
        return this;
    }

    public RecyclerViewHolder setLayoutManager(int viewId, RecyclerView.LayoutManager manager){
        ((RecyclerView)findViewById(viewId)).setLayoutManager(manager);
        return this;
    }

    public RecyclerViewHolder setRecyclerAdapter(int viewId, RecyclerView.Adapter adapter){
        ((RecyclerView)findViewById(viewId)).setAdapter(adapter);
        return this;
    }

}
