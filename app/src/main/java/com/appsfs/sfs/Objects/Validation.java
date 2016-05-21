package com.appsfs.sfs.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dunglv on 5/22/16.
 */
public class Validation  {
    private String phoneShop;
    private String codeOrder;
    private String codeCheckOrder;

    public Validation() {
    }

    public String getPhoneShop() {
        return phoneShop;
    }

    public void setPhoneShop(String phoneShop) {
        this.phoneShop = phoneShop;
    }

    public String getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(String codeOrder) {
        this.codeOrder = codeOrder;
    }

    public String getCodeCheckOrder() {
        return codeCheckOrder;
    }

    public void setCodeCheckOrder(String codeCheckOrder) {
        this.codeCheckOrder = codeCheckOrder;
    }

    public JSONObject toJson() {
        try {
            JSONObject order = new JSONObject();
            order.put("phone_shop", phoneShop);
            order.put("code", codeOrder);
            order.put("code_checking", codeCheckOrder);
            return new JSONObject().put("validation", order);
        } catch (JSONException e) {
            e.getMessage();
            return new JSONObject();
        }
    }
}
