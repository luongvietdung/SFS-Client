package com.appsfs.sfs.api.helper;

import org.json.JSONObject;

/**
 * Created by dunglv on 5/15/16.
 */
public class CustomRespond {
    private JSONObject data;
    private int statusCode;
    private String from;

    public CustomRespond() {
    }

    public CustomRespond(JSONObject data, int statusCode, String from) {

        this.data = data;
        this.statusCode = statusCode;
        this.from = from;
    }

    public JSONObject getData() {
        return data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getFrom() {
        return from;
    }
}
