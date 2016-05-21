package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.appsfs.sfs.Objects.Validation;
import com.appsfs.sfs.api.helper.CustomRespond;
import com.appsfs.sfs.api.helper.RequestHelper;

import org.json.JSONObject;

/**
 * Created by dunglv on 5/22/16.
 */
public class VailidationOrder extends RequestHelper {

    public static final String VALIDATION = "VALIDATION";

    public VailidationOrder(Context context, Response.Listener<CustomRespond> listener, Response.ErrorListener errorListener, Validation validation) {
        super(context, "/api/order_validations", Request.Method.POST, validation.toJson(), listener, errorListener, VALIDATION);
    }

    public void start() {
        super.start(VALIDATION);
    }
}
