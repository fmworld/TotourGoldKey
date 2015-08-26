package com.fm.fmlib.tour.entity;

import com.fm.fmlib.dao.LaunchProfile;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/7/30.
 */
public class LaunchProfileEntity extends  BaseEntity{
    public Profiles msg;
    public class Profiles{
        public LaunchProfile ad;
        public LaunchProfile shop;
        public LaunchProfile slider;
        public List<LaunchProfile> store;
    }
}
