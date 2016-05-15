package com.appsfs.sfs.api.helper;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dunglv on 5/9/16.
 */
public class RequestQueueHelper {

    private static RequestQueueHelper requestQueueHelper;
    private RequestQueue requestQueue;
    private Context context;

    private static final String TAG = "Other";

    public RequestQueueHelper(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        requestQueue.add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        requestQueue.add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    public void cancelAll() {
        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    public static synchronized RequestQueueHelper getInstance(Context context) {
        if (requestQueueHelper == null) {
            requestQueueHelper = new RequestQueueHelper(context);
        }
        return requestQueueHelper;
    }

}
