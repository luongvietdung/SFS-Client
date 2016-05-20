package com.appsfs.sfs.api.helper;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

/**
 * Created by dunglv on 5/15/16.
 */
public class CustomVolleyError extends VolleyError {
    public CustomVolleyError(NetworkResponse response, Throwable cause) {
        super(response);
        super.initCause(cause);
        Log.e("aa", "aaaaaa");
    }

}
