package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appsfs.sfs.Objects.IParam;
import com.appsfs.sfs.api.helper.RequestQueueHelper;

import org.json.JSONObject;

/**
 * Created by dunglv on 5/9/16.
 */
public class RegisterUser {
    private JsonObjectRequest registerRequest;
    private static final int METHOD = Request.Method.POST;
    private RequestQueueHelper requestQueue;

    public RegisterUser(IParam param, Context context, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        requestQueue = RequestQueueHelper.getInstance(context);
        registerRequest = new JsonObjectRequest(METHOD, param.getUrlCtreater(), param.toParamCreater(), listener, errorListener);
    }

    public void start() {
        requestQueue.cancelPendingRequests("REGISTER");
        requestQueue.addToRequestQueue(registerRequest, "REGISTER");
    }
}
