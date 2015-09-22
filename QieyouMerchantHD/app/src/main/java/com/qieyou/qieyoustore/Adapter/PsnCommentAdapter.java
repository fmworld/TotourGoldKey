package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.databinding.ListItemPsnCommentBinding;
import com.qieyou.qieyoustore.model.PsnComment;
import com.qieyou.qieyoustore.util.RecyclerViewBindingHolder;
import com.qieyou.qieyoustore.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/26.
 */
public class PsnCommentAdapter extends RecyclerView.Adapter<RecyclerViewBindingHolder> {
    List<PsnComment> comments;
    private Context mContext;

    public PsnCommentAdapter(Context context) {
        mContext = context;
        comments = new ArrayList<>();
    }

    @Override
    public RecyclerViewBindingHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ListItemPsnCommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this.mContext),
                R.layout.list_item_psn_comment, viewGroup, false);
        RecyclerViewBindingHolder holder = new RecyclerViewBindingHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewBindingHolder recyclerViewHolder, int i) {
        PsnComment comment = comments.get(i);
        recyclerViewHolder.getBinding().setComment(comment);
    }

    @Override
    public int getItemCount() {
        return null == comments ? 0 : comments.size();
    }

    public void setComments(List<PsnComment> _comments) {
        comments = _comments;
        this.notifyDataSetChanged();
    }
}
