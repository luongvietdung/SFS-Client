package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.appsfs.sfs.api.helper.RequestHelper;

import org.json.JSONObject;

/**
 * Created by dunglv on 5/14/16.
 */
public class LogoutUser extends RequestHelper {
    public LogoutUser(Context context, Response.Listener listener, Response.ErrorListener errorListener) {
        super(context, "/auth/sign_out", Request.Method.POST, new JSONObject(), listener, errorListener);
    }

    public void start() {
        super.start("LOGOUT");
    }
}
