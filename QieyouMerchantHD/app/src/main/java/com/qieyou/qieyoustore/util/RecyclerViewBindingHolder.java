package com.qieyou.qieyoustore.util;

import android.support.v7.widget.RecyclerView;

import android.view.View;

import com.qieyou.qieyoustore.databinding.ListItemPsnCommentBinding;


/**
 * Created by zhoufeng'an on 2015/9/18.
 */
public class RecyclerViewBindingHolder extends RecyclerView.ViewHolder {
    private ListItemPsnCommentBinding binding;
    public RecyclerViewBindingHolder(View itemView) {
        super(itemView);
    }

    public ListItemPsnCommentBinding getBinding() {
        return binding;
    }

    public void setBinding(ListItemPsnCommentBinding binding) {
        this.binding = binding;
    }
}
