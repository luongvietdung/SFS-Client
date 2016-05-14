package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appsfs.sfs.api.helper.AccessHeader;
import com.appsfs.sfs.api.helper.RequestHelper;
import com.appsfs.sfs.api.helper.RequestQueueHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dunglv on 5/9/16.
 */
public class LoginUser {
    private JsonObjectRequest loginRequest;
    private static final int METHOD = Request.Method.POST;
    private RequestQueueHelper requestQueue;
    private static final String LOGIN_URL = RequestHelper.API_URL + "/auth/sign_in";

    public LoginUser(String phone, String password, Context context, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

        try {
            JSONObject params = new JSONObject();
            params.put("password", password);
            params.put("phone", phone);
            requestQueue = RequestQueueHelper.getInstance(context);
            loginRequest = new JsonObjectRequest(METHOD, LOGIN_URL, params, listener, errorListener) {
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    AccessHeader.setHeader(response.headers);
                    return super.parseNetworkResponse(response);
                }
            };
        } catch (JSONException e) {
            e.getMessage();

        }
    }

    public void start() {
        requestQueue.cancelPendingRequests("LOGIN");
        requestQueue.addToRequestQueue(loginRequest, "LOGIN");
    }
}
