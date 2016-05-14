package com.appsfs.sfs.api.sync;

import com.appsfs.sfs.Objects.IParam;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dunglv on 5/9/16.
 */
public class ShipperSync extends AccountSync {
    private int fee; // money ship

    public ShipperSync(JSONObject account) throws JSONException {
        super(account);
        this.fee = account.getInt("fee");
    }

    public ShipperSync(JSONObject account, UserSync user) throws JSONException {
        super(account, user);
        this.fee = account.getInt("fee");
    }

    public int getFee() {
        return fee;
    }


}
