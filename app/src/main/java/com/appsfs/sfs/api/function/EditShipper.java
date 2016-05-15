package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.appsfs.sfs.Objects.Shipper;
import com.appsfs.sfs.Objects.Shop;
import com.appsfs.sfs.api.helper.RequestHelper;

import org.json.JSONObject;

/**
 * Created by dunglv on 5/15/16.
 */
public class EditShipper {
    private RequestHelper requestHelper;

    public EditShipper(Context context, Response.Listener listener, Response.ErrorListener errorListener, Shipper shipper) {
        this.requestHelper = new RequestHelper(context, "/api/shippers/" + shipper.getId(), Request.Method.PUT, params(shipper), listener, errorListener);
    }

    public void start() {
        requestHelper.start("SHIPPER UPDATE");
    }

    private JSONObject params(Shipper shipper) {
        try {
            JSONObject shopJson = new JSONObject();
            shopJson.put("name", shipper.getName());
            shopJson.put("address", shipper.getAddress());
            shopJson.put("money", String.valueOf(shipper.getMoney()));
            shopJson.put("fee", String.valueOf(shipper.getMoneyShip()));
            return new JSONObject().put("shipper", shopJson);
        } catch (Exception e) {
            e.getMessage();
        }
        return new JSONObject();
    }
}
