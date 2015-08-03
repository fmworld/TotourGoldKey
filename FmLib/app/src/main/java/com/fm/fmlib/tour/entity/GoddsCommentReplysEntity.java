package com.fm.fmlib.tour.entity;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class GoddsCommentReplysEntity extends BaseEntity {
    public List<Reply> msg;

    public class Reply {
        public String reply_id;
        public String comment_id;
        public String create_user_id;
        public String reply_user_id;
        public String note;
        public long create_time;
        public String create_nick_name;
        public String reply_nick_name;
    }

    public class TuanInfo {

    }
}
