package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.appsfs.sfs.api.helper.RequestHelper;

import org.json.JSONObject;

/**
 * Created by dunglv on 5/14/16.
 */
public class LogoutUser extends RequestHelper {

    public static final String SIGN_OUT_USER = "Sign out user";

    public LogoutUser(Context context, Response.Listener listener, Response.ErrorListener errorListener) {
        super(context, "/auth/sign_out", Request.Method.DELETE, new JSONObject(), listener, errorListener, SIGN_OUT_USER);
    }

    public void start() {
        requestQueue.cancelAll();
        super.start("LOGOUT");
    }
}
