package com.fm.fmlib.tour.entity;

/**
 * Created by zhoufeng'an on 2015/7/30.
 */
public class StoreInfoEntity extends  BaseEntity{
    public StoreInfoJson msg;

    public class StoreInfoJson{
        public innInfoJson inn;
    }
    public class innInfoJson{
        public String inn_id;
        public String inn_name;
        public String inn_products;
        public String inn_head;
        public String inner_head;
        public String inner_score;
        public String inner_serve;
        public String inner_contact;
        public String inner_moblie;
        public String inner_identify;
        public String inner_today_serve;
    }
}
