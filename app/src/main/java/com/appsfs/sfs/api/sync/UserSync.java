package com.appsfs.sfs.api.sync;

import android.util.Log;

import com.appsfs.sfs.Objects.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dunglv on 5/9/16.
 */
public class UserSync {
    private int id;
    private String phone;
    private String accountable_type;
    private int accountable_id;
    private boolean status;
    private double latitude;//Vi do
    private double longitude; // Kinh do

    // shipper or shop information
    private AccountSync accountSync;


    public static final String SHIPPER_TYPE = "shipper";
    public static final String SHOP_TYPE = "shop";

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public UserSync() {
        super();
    }

    public UserSync(JSONObject user, AccountSync account) throws JSONException {
        Log.d("aa", user.toString());
        this.id = user.getInt("id");
        this.accountable_id = user.getInt("accountable_id");
        this.accountable_type = user.getString("accountable_type");
        this.status = user.getBoolean("status");
        this.phone = user.getString("phone");
        this.latitude = user.getDouble("latitude");
        this.longitude = user.getDouble("longitude");
        this.accountSync = account;
    }

    public UserSync(JSONObject user) throws JSONException {
        this(user, null);
        if (SHIPPER_TYPE.equalsIgnoreCase(accountable_type)) {
            accountSync = new ShipperSync(user.getJSONObject("accountable").getJSONObject(SHIPPER_TYPE), this);
        } else if (SHOP_TYPE.equalsIgnoreCase(accountable_type)){
            accountSync = new ShopSync(user.getJSONObject("accountable").getJSONObject(SHOP_TYPE), this);
        }
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getAccountable_type() {
        return accountable_type;
    }

    public int getAccountable_id() {
        return accountable_id;
    }

    public boolean isStatus() {
        return status;
    }

    public AccountSync getAccountSync() {
        return accountSync;
    }

    public int getRole() {
        if("Shipper".equalsIgnoreCase(accountable_type)) {
            return 0;
        }
        return 1;
    }

}
