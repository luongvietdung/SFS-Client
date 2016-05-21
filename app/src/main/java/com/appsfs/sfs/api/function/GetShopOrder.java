package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.appsfs.sfs.api.helper.CustomRespond;
import com.appsfs.sfs.api.helper.RequestHelper;
import com.appsfs.sfs.api.sync.UserSync;

import org.json.JSONObject;

/**
 * Created by dunglv on 5/22/16.
 */
public class GetShopOrder extends RequestHelper {

    public static final String LIST_ORDERS = "LIST ORDERS";

    public GetShopOrder(Context context, Response.Listener<CustomRespond> listener, Response.ErrorListener errorListener, UserSync userSync) {
        super(context, "/api/users/" + userSync.getId() + "/detail_orders", Request.Method.GET, new JSONObject(), listener, errorListener, LIST_ORDERS);
    }

    public void start() {
        super.start(LIST_ORDERS);
    }
}
