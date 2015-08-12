package com.fm.fmlib.state;

/**
 * Created by zhoufeng'an on 2015/8/3.
 */
public interface InnState extends BaseState {
    public static class InnToFetchShareInfoEvent {
        public final String inn_id;

        public InnToFetchShareInfoEvent(String inn_id) {
            this.inn_id = inn_id;
        }
    }

    public static class InnFetchShareInfoEvent {
        public String thumb;
        public String name;
        public String url;

        public InnFetchShareInfoEvent(String thumb, String name, String url) {
            this.thumb = thumb;
            this.name = name;
            this.url = url;
        }
    }

}
