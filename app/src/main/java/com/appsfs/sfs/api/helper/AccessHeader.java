package com.appsfs.sfs.api.helper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dunglv on 5/9/16.
 */
public class AccessHeader {
    private static final String ACCESS_TOKEN = "Access-Token";
    private static final String CLIENT = "Client";
    private static final String UID = "Uid";
    private static final String TOKEN_TYPE = "Token-Type";
    private static final String EXPIRY = "Expiry";

    private static final Map<String, String> header = new HashMap<>();


    public static void setHeader(Map<String, String> respondHeader) {
        resetAccessHeader();
        header.put(ACCESS_TOKEN, respondHeader.get(ACCESS_TOKEN));
        header.put(CLIENT, respondHeader.get(CLIENT));
        header.put(EXPIRY, respondHeader.get(EXPIRY));
        header.put(TOKEN_TYPE, respondHeader.get(TOKEN_TYPE));
        header.put(UID, respondHeader.get(UID));
    }

    public static Map<String, String> toHeader() {
        if (header.isEmpty()) {
            return null;
        }
        return header;
    }

    public static void resetAccessHeader() {
        header.clear();
    }

}
