package com.fm.fmlib.network.client;

/**
 * Created by zhoufeng'an on 2015/7/30.
 */
public class Response {
    private int status;
    private String strEntity;

    public int getStatus() {
        return status;
    }

    public String getStrEntity() {
        return strEntity;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStrEntity(String strEntity) {
        this.strEntity = strEntity;
    }
}
