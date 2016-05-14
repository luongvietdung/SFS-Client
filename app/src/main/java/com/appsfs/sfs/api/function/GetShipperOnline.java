package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.appsfs.sfs.api.helper.RequestHelper;

import org.json.JSONObject;

/**
 * Created by dunglv on 5/14/16.
 */
public class GetShipperOnline extends RequestHelper {
    public GetShipperOnline(Context context, Response.Listener listener, Response.ErrorListener errorListener) {
        super(context, "/api/shippers", Request.Method.GET, new JSONObject(), listener, errorListener);
    }

    public void start() {
        super.start("SHIPPERS");
    }
}
