package com.qieyou.qieyoustore.Adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.fragment.ProductComments;
import com.qieyou.qieyoustore.util.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/26.
 */
public class ProCommentPicsAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    List<String> pics;

    public ProCommentPicsAdapter() {
        pics = new ArrayList<>();
    }

    public ProCommentPicsAdapter(List<String> pics) {
        this.pics = pics;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.list_item_comment_pic,null);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.setImageURI(R.id.comment_pic, Uri.parse(pics.get(i)));
    }

    @Override
    public int getItemCount() {
        return null == pics ? 0 : pics.size();
    }

    public void setPics(List<String> _pics) {
        pics = _pics;
        this.notifyDataSetChanged();
    }
}
