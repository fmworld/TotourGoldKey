package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fm.fmlib.tour.bean.ProComment;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.RecyclerViewHolder;
import com.qieyou.qieyoustore.util.TourStringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/26.
 */
public class ProCommentAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    List<ProComment> comments;
    private Context mContext;

    public ProCommentAdapter(Context context) {
        mContext = context;
        comments = new ArrayList<>();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.list_item_pro_comment, null);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {

        ProComment comment = comments.get(i);
        recyclerViewHolder.setImageURI(R.id.comment_icon, Uri.parse(comment.headimg))
                .setText(R.id.comment_name, comment.user_name)
                .setScore(R.id.comment_score_layout, Float.valueOf(comment.points))
                .setText(R.id.comments_text, comment.note);

        List<String> pics = generatePics(comment.picture);
        if (null != pics) {

                LinearLayoutManager picsManager = new LinearLayoutManager(mContext);
                picsManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                ProCommentPicsAdapter adapter = new ProCommentPicsAdapter(pics);
                recyclerViewHolder
                        .setVisibility(R.id.comment_item_pics, View.VISIBLE)
                        .setLayoutManager(R.id.comment_item_pics, picsManager)
                        .setRecyclerAdapter(R.id.comment_item_pics,adapter);

        } else {
            recyclerViewHolder.setVisibility(R.id.comment_item_pics, View.GONE);
        }

//
    }

    private List<String> generatePics(String picstr){
        if (!TourStringUtil.isNULLorEmpty(picstr)){
            String[] pics = picstr.split(",");
            if(0 < pics.length){
                return Arrays.asList(pics);
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return null == comments ? 0 : comments.size();
    }

    public void setComments(List<ProComment> _comments) {
        comments = _comments;
        this.notifyDataSetChanged();
    }
}
