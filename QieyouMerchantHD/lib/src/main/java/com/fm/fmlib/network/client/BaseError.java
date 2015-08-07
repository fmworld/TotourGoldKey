package com.fm.fmlib.network.client;

/**
 * Created by qieyou on 2015/7/30.
 */
public class BaseError extends Throwable {
    private Response response;
    private boolean networkError;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public boolean isNetworkError(){
        return networkError;
    }
}
