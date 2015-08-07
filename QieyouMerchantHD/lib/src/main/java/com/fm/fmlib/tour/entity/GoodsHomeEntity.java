package com.fm.fmlib.tour.entity;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class GoodsHomeEntity extends BaseEntity{
    public List<Product> msg;

    public class Product {
        public String product_id;
        public String state;
        public String product_name;
        public float price;
        public float old_price;
        public float score;
        public int comments;
        public float agent;
        public int shelf_count;
        public String content;
        public String thumb;
        public int quantity;
        public int bought_count;
        public long tuan_end_time;
        public int category;
        public String category_id;
        public double lon;
        public double lat;
        public String bdgps;
        public int on_shelves;
    }


}
