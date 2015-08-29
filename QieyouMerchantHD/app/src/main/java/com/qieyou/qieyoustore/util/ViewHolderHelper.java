package com.qieyou.qieyoustore.util;

import android.content.Context;
import android.net.Uri;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by zfa on 2015/7/24.
 */
public class ViewHolderHelper {
    public int position;
    public SparseArray<View> mViews;
    public View mConvertView;
    public Context mContext;

    public static ViewHolderHelper get(Context context,int position, View convertView, ViewGroup parent, int layoutId){
        if(null == convertView){
            return new ViewHolderHelper(context, parent, layoutId, position);
        }
        ViewHolderHelper existingHelper = (ViewHolderHelper)convertView.getTag();
        existingHelper.position = position;
        return existingHelper;
    }

    public static ViewHolderHelper get(Context context,int position, View convertView, ViewGroup parent, View view){
        if(null == convertView){
            return new ViewHolderHelper(context, parent, view, position);
        }
        ViewHolderHelper existingHelper = (ViewHolderHelper)convertView.getTag();
        existingHelper.position = position;
        return existingHelper;
    }

    protected ViewHolderHelper(Context context, ViewGroup parent, int layoutId, int position){
        this.mContext = context;
        this.position = position;
        this.mViews = new SparseArray<View>();
        this.mConvertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);

        mConvertView.setTag(this);
    }

    protected ViewHolderHelper(Context context, ViewGroup parent, View view, int position){
        this.mContext = context;
        this.position = position;
        this.mViews = new SparseArray<View>();
        this.mConvertView = view;
        mConvertView.setTag(this);
    }

    protected <T extends View> T findViewById(int viewId){
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }

    public void setVisibility(int viewId, int state){
        findViewById(viewId).setVisibility(state);
    }

    public ViewHolderHelper setText(int viewId, String value){
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }

    public ViewHolderHelper setTextColor(int viewId, int colorRes){
        TextView view = findViewById(viewId);
        view.setTextColor(mContext.getResources().getColor(colorRes));
        return this;
    }

    public ViewHolderHelper setText(int viewId, Spanned value){
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }



    public ViewHolderHelper setFlags(int viewId, int value){
        TextView view = findViewById(viewId);
        view.getPaint().setFlags(value);;
        return this;
    }

    public ViewHolderHelper setBackgroundColor(int viewId, int color){
        View view = findViewById(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolderHelper setBackgroundResource(int viewId, int resource){
        View view = findViewById(viewId);
        view.setBackgroundResource(resource);
        return this;
    }

    public ViewHolderHelper setText(int viewId, String value, Object tag){
        TextView view = findViewById(viewId);
        view.setText(value);
        view.setTag(tag);
        return this;
    }

    public ViewHolderHelper setImageFromUrl(int viewId, String url, int res){
        SimpleDraweeView imageView = findViewById(viewId);
        imageView.setImageURI(Uri.parse(url));
        return this;
    }

    public ViewHolderHelper setImageFromRes(int viewId, int res){
        ImageView imageView = findViewById(viewId);
        imageView.setImageResource(res);
        return this;
    }



    public ViewHolderHelper setClickListener(int viewId, View.OnClickListener listener){
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolderHelper setClickListener(int viewId, View.OnClickListener listener, Object tag){
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        view.setTag(tag);
        return this;
    }

    public ViewHolderHelper setTag(int viewId, Object tag){
        View view = findViewById(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolderHelper setCompoundDrawablesWithIntrinsicBounds(int viewId, int left, int top, int right, int bottom){
        ((TextView)findViewById(viewId)).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        return this;
    }

    public ViewHolderHelper setAdapter(int viewId, BaseAdapter adapter){
        ((AbsListView)findViewById(viewId)).setAdapter(adapter);
        return this;
    }

    public ViewHolderHelper setClickListener(int viewId, AdapterView.OnItemClickListener listener){
        ((AbsListView)findViewById(viewId)).setOnItemClickListener(listener);
        return this;
    }
    public ViewHolderHelper setClickListener(int viewId, AdapterView.OnItemClickListener listener, Object tag){
        AbsListView absListView = findViewById(viewId);
        absListView.setOnItemClickListener(listener);
        absListView.setTag(tag);
        return this;
    }

    public ViewHolderHelper setMaxLines(int viewId, int lines){
        ((TextView)findViewById(viewId)).setMaxLines(lines);
        return this;
    }

    public ViewHolderHelper setEllipsize(int viewId, TextUtils.TruncateAt at){
        ((TextView)findViewById(viewId)).setEllipsize(at);
        return this;
    }

    public ViewHolderHelper setMyLayoutAmountInfo(int viewId, String title, String info){
//        ((MyLayoutAmountInfo)findViewById(viewId)).setTitleText(title);
//        ((MyLayoutAmountInfo)findViewById(viewId)).setAmountText(info);
        return this;
    }

    public ViewHolderHelper setImageURI(int viewId, Uri uri){
        ((SimpleDraweeView)findViewById(viewId)).setImageURI(uri);
        return this;
    }
}
