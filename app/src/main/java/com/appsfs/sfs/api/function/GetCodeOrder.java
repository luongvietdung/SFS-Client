package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.appsfs.sfs.api.helper.CustomRespond;
import com.appsfs.sfs.api.helper.RequestHelper;
import com.appsfs.sfs.api.sync.UserSync;

import org.json.JSONObject;


/**
 * Created by dunglv on 5/21/16.
 */
public class GetCodeOrder extends RequestHelper {

    public static final String NEW_CODE_ORDER = "CODE ORDER";

    public GetCodeOrder(Context context, Response.Listener<CustomRespond> listener, Response.ErrorListener errorListener, UserSync userSync) {
        super(context, "/api/users/" + userSync.getId() + "/detail_orders/new", Request.Method.GET, new JSONObject(), listener, errorListener, NEW_CODE_ORDER);
    }

    public void start() {
        super.start("CODE ORDER");
    }
}
