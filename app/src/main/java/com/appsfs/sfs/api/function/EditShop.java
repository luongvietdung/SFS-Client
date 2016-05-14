package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.appsfs.sfs.Objects.Shop;
import com.appsfs.sfs.api.helper.RequestHelper;

import org.json.JSONObject;

/**
 * Created by dunglv on 5/15/16.
 */
public class EditShop {
    private RequestHelper requestHelper;

    public EditShop(Context context, Response.Listener listener, Response.ErrorListener errorListener, Shop shop) {
        this.requestHelper = new RequestHelper(context, "/api/shops/" + shop.getId(), Request.Method.PUT, params(shop), listener, errorListener);
    }

    public void start() {
        requestHelper.start("SHOP UPDATE");
    }

    private JSONObject params(Shop shop) {
        try {
            JSONObject shopJson = new JSONObject();
            shopJson.put("name", shop.getName());
            shopJson.put("address", shop.getAddress());
            shopJson.put("money", String.valueOf(shop.getMoney()));
            shopJson.put("product_name", shop.getProduct());
            shopJson.put("latitude", shop.getLatitude());
            shopJson.put("longitude", shop.getLongitude());
            return new JSONObject().put("shop", shopJson);
        } catch (Exception e) {
            e.getMessage();
        }
        return new JSONObject();
    }
}
