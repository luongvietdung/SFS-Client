package com.appsfs.sfs.api.sync;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dunglv on 5/9/16.
 */
public class AccountSync {
    protected int id;
    protected String name;
    protected String address;
    protected int money;

    // user information of shop or ship
    protected UserSync user;

    public AccountSync(JSONObject account) throws JSONException {
        this(account, null);
        Log.e("aaa", account.getJSONObject("user").toString());
        this.user = new UserSync(account.getJSONObject("user"), this);
    }

    public AccountSync(JSONObject account, UserSync user) throws JSONException {
        this.id = account.getInt("id");
        this.address = account.getString("address");
        this.name = account.getString("name");
        this.money = account.getInt("money");
        this.user = user;
    }

    public double getLatitude() {
        return user.getLatitude();
    }

    public String  getPhoneNumber() {
        return user.getPhone();
    }
    public double getLongitude() {
        return user.getLongitude();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getMoney() {
        return money;
    }

    public UserSync getUser() {
        return user;
    }
}
