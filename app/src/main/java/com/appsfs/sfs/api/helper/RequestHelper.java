package com.appsfs.sfs.api.helper;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by dunglv on 5/9/16.
 */
public class RequestHelper {
//    public static final String API_URL = "https://boiling-oasis-10082.herokuapp.com";
    public static final String API_URL = "http://192.168.1.37:3000";
    private JsonObjectRequest request;
    private int method;
    private JSONObject params;
    private RequestQueueHelper requestQueue;

    public RequestHelper(Context context, String api_path, int method, JSONObject params, Response.Listener listener, Response.ErrorListener errorListener) {
        requestQueue = RequestQueueHelper.getInstance(context);
        request = new JsonObjectRequest(method, API_URL + api_path, params, listener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return AccessHeader.toHeader() == null ? super.getHeaders() : AccessHeader.toHeader();
            }
        };
    }

    public void start(String tag) {
        requestQueue.cancelPendingRequests(tag);
        requestQueue.addToRequestQueue(request, tag);
    }
}
