package com.appsfs.sfs.api.sync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dunglv on 5/22/16.
 */
public class OrderListSync {
    private ArrayList<OrderSync> orderSyncs;

    public OrderListSync(JSONObject list) {
        orderSyncs = new ArrayList<>();
        try {
            JSONArray orders = list.getJSONArray("detail_orders");
            for (int i = 0; i < orders.length(); i++) {
                OrderSync order = new OrderSync(orders.getJSONObject(i));
                orderSyncs.add(order);
            }
        } catch (JSONException e) {
            e.getMessage();
        }
    }

    public ArrayList<OrderSync> getOrderSyncs() {
        return orderSyncs;
    }
}
