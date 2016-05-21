package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.appsfs.sfs.Objects.Orders;
import com.appsfs.sfs.api.helper.CustomRespond;
import com.appsfs.sfs.api.helper.RequestHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dunglv on 5/22/16.
 */
public class CreateOrder {
    public static final String CREATE_ORDER = "CREATE_ORDER";
    private RequestHelper requestHelper;

    public CreateOrder(Context context, Response.Listener<CustomRespond> listener, Response.ErrorListener errorListener, Orders orders) {
        requestHelper = new RequestHelper(context, "/api/users/" + orders.getOwner().getId() + "/detail_orders", Request.Method.POST, createParams(orders), listener, errorListener, CREATE_ORDER);
    }

    public void start() {
        requestHelper.start(CREATE_ORDER);
    }

    private JSONObject createParams(Orders orders) {
        try {
            JSONObject order = new JSONObject();
            order.put("phone_customer", orders.getPhoneCustomer());
            order.put("phone_shipper", orders.getPhoneShipper());
            order.put("code", orders.getCodeOrder());
            order.put("code_checking", orders.getCodeCheckOrder());
            return new JSONObject().put("detail_order", order);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }
}
