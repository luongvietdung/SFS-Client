package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.appsfs.sfs.api.helper.CustomRespond;
import com.appsfs.sfs.api.helper.RequestHelper;
import com.appsfs.sfs.api.sync.UserSync;

import org.json.JSONObject;

/**
 * Created by dunglv on 5/16/16.
 */
public class DeleteUser extends RequestHelper {

    public static final String DELETE_USER = "delete user";

    public DeleteUser(Context context, Response.Listener<CustomRespond> listener, Response.ErrorListener errorListener, UserSync userSync) {
        super(context, "/api/users/" + userSync.getId(), Request.Method.DELETE, new JSONObject(), listener, errorListener, DELETE_USER);
    }

    public void start() {
        super.start("DELETEUSER");
    }
}
