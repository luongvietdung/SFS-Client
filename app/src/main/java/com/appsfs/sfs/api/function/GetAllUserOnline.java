package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.appsfs.sfs.api.helper.RequestHelper;

import org.json.JSONObject;

/**
 * Created by dunglv on 5/14/16.
 */
public class GetAllUserOnline extends RequestHelper {

    public static final String GET_USER_ONLINE = "Get user online";

    public GetAllUserOnline(Context context, Response.Listener listener, Response.ErrorListener errorListener) {
        super(context, "/api/users", Request.Method.GET, new JSONObject(), listener, errorListener, GET_USER_ONLINE);
    }

    public void start() {
        super.start("USERS");
    }
}
