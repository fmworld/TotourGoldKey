package com.fm.fmlib.tour.entity;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class GoddsDetailEntity extends BaseEntity{
    public ProDetail msg;
    public class ProDetail{
        public String product_id;
        public String inn_id;
        public int is_qieyou;
        public int is_tuan;
        public int is_express;
        public String state;
        public String product_name;
        public String content;
        public String category;
        public String category_id;
        public float price;
        public float old_price;
        public float purchase_price;
        public float agent;
        public String thumb;
        public String gallery;
        public int quantity;
        public int bought_count;
        public int product_fav;
        public long hit;
        public float score;
        public int comments;
        public String note;
        public String booking_info;
        public long tuan_end_time;
        public String product_images;
        public String facility;
        public String keyword;
        public String commentary;
        public String innholder;
        public String traveler;
        public long create_time;
        public long update_time;
        public String inn_name;
        public String inn_address;
        public String inn_summary;
        public String inner_telephone;
        public String inner_moblie_number;
        public double lon;
        public double lat;
        public String bdgps;
        public int sale_license;
        public int on_shelves;
        public List<TuanInfo> tuan;
    }

    public class TuanInfo{

    }
}
