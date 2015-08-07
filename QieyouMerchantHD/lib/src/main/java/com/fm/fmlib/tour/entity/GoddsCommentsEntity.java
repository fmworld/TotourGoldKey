package com.fm.fmlib.tour.entity;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class GoddsCommentsEntity extends BaseEntity{
    public List<Comment> msg;
    public class Comment{
        public String comment_id;
        public String user_id;
        public float points;
        public int has_pic;
        public long note;
        public String picture;
        public long likeNum;
        public long replyNum;
        public long create_time;
        public String user_name;
        public String headimg;
    }

    public class TuanInfo{

    }
}
