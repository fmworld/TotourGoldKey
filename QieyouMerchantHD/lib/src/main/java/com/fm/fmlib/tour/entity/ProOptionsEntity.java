package com.fm.fmlib.tour.entity;

import com.fm.fmlib.dao.CategoryList;
import com.fm.fmlib.dao.CategoryTitle;
import com.fm.fmlib.dao.LocalList;
import com.fm.fmlib.dao.LocalTitle;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/7/30.
 */
public class ProOptionsEntity extends  BaseEntity{
    public ProOption msg;

    public class ProOption{
        public ProCategory category;
        public ProLocal local;
    }
    public class ProCategory{
        public List<CategoryTitle> title;
        public List<CategoryList> list;
    }

    public class ProLocal{
        public List<LocalTitle> title;
        public List<LocalList> list;
    }
}
