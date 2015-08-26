package com.fm.fmlib.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table LOCAL_LIST.
 */
public class LocalList {

    /** Not-null value. */
    private String dest_id;
    /** Not-null value. */
    private String local_id;
    /** Not-null value. */
    private String local_name;

    public LocalList() {
    }

    public LocalList(String dest_id, String local_id, String local_name) {
        this.dest_id = dest_id;
        this.local_id = local_id;
        this.local_name = local_name;
    }

    /** Not-null value. */
    public String getDest_id() {
        return dest_id;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDest_id(String dest_id) {
        this.dest_id = dest_id;
    }

    /** Not-null value. */
    public String getLocal_id() {
        return local_id;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    /** Not-null value. */
    public String getLocal_name() {
        return local_name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

}