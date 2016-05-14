package com.appsfs.sfs.Objects;


import com.appsfs.sfs.api.helper.RequestHelper;

import org.json.JSONObject;

/**
 * Created by longdv on 4/18/16.
 */
public class Shop implements IParam{
    private int id;
    private String mName;
    private String mPhoneNumber;
    private String mAddress;
    private String mProduct;
    private int money;
    private double mLatitude;//Vi do
    private double mLongitude; // Kinh do
    private User user;

    public Shop() {
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

    public String getProduct() {
        return mProduct;
    }

    public void setProduct(String mProduct) {
        this.mProduct = mProduct;
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

    public JSONObject toParamCreater() {
        try {
            JSONObject userParams = new JSONObject();
            userParams.put("phone", user.getPhoneNumbers());
            userParams.put("password", user.getPassword());
            userParams.put("password_confirmation", user.getPassword());
            userParams.put("latitude", mLatitude);
            userParams.put("longitude", mLongitude);
            JSONObject shop = new JSONObject();
            shop.put("user_attributes", userParams);
            shop.put("name", mName);
            shop.put("address", mAddress);
            shop.put("money", String.valueOf(money));
            shop.put("product_name", String.valueOf(getProduct()));
            JSONObject params = new JSONObject();
            params.put("shop", shop);
            return params;
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    @Override
    public String getUrlCtreater() {
        return RequestHelper.API_URL + "/api/shops";
    }
}
