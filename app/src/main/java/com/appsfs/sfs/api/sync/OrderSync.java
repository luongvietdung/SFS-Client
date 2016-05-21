package com.appsfs.sfs.api.sync;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dunglv on 5/22/16.
 */
public class OrderSync {
    private String codeOrder;
    private String phoneCustomer;
    private String phoneShipper;
    private String codeCheckOrder;
    private String date;
    private boolean status;
    private int id;

    public OrderSync(JSONObject order) {
        try {
            this.id = order.getInt("id");
            this.codeCheckOrder = order.getString("code_checking");
            this.codeOrder = order.getString("code");
            this.phoneCustomer = order.getString("phone_customer");
            this.phoneShipper = order.getString("phone_shipper");
            this.status = order.getBoolean("status");
            this.date = order.getString("date");
        } catch (JSONException e) {
            e.getMessage();
        }
    }

    public String getCodeOrder() {
        return codeOrder;
    }

    public String getPhoneCustomer() {
        return phoneCustomer;
    }

    public String getPhoneShipper() {
        return phoneShipper;
    }

    public String getCodeCheckOrder() {
        return codeCheckOrder;
    }

    public String getDate() {
        return date;
    }

    public boolean getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }
}
