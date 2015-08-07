package com.fm.fmlib.tour.entity;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class ShopTagsEntity extends BaseEntity{
    public List<Msg> msg;

    public class Msg{
        public String tag_id;
        public String tag_name;
    }
}
