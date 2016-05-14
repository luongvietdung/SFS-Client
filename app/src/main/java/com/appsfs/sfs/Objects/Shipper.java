package com.appsfs.sfs.Objects;

import com.appsfs.sfs.api.helper.RequestHelper;

import org.json.JSONObject;

/**
 * Created by longdv on 4/18/16.
 */
public class Shipper implements IParam{
    private int id;
    private String mName;
    private String mPhoneNumber;
    private String mAddress;
    private int money;
    private int money_ship;
    private double mLatitude;//Vi do
    private double mLongitude; // Kinh do

    private User user;

    public Shipper() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public int getMoneyShip() {
        return money_ship;
    }

    public void setMoneyShip(int money_ship) {
        this.money_ship = money_ship;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public JSONObject toParamCreater() {
        try {
            JSONObject userParams = new JSONObject();
            userParams.put("phone", user.getPhoneNumbers());
            userParams.put("password", user.getPassword());
            userParams.put("password_confirmation", user.getPassword());
            JSONObject shipper = new JSONObject();
            shipper.put("user_attributes", userParams);
            shipper.put("name", mName);
            shipper.put("address", mAddress);
            shipper.put("money", String.valueOf(money));
            shipper.put("fee", String.valueOf(money_ship));
            JSONObject params = new JSONObject();
            params.put("shipper", shipper);
            return params;
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    @Override
    public String getUrlCtreater() {
        return RequestHelper.API_URL + "/api/shippers";
    }
}
