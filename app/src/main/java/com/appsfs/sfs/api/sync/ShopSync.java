package com.appsfs.sfs.api.sync;

import com.appsfs.sfs.Objects.IParam;
import com.appsfs.sfs.Objects.Shop;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dunglv on 5/9/16.
 */
public class ShopSync extends AccountSync{
    private String product_name; // product of shop;

    public ShopSync(JSONObject account) throws JSONException {
        super(account);
        this.product_name = account.getString("product_name");
    }

    public ShopSync(JSONObject account, UserSync user) throws JSONException {
        super(account, user);
        this.product_name = account.getString("product_name");
    }

    public String getProduct_name() {
        return product_name;
    }




}
