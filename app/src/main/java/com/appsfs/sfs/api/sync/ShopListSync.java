package com.appsfs.sfs.api.sync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by longdv on 5/14/16.
 */
public class ShopListSync {
    private ArrayList<ShopSync> shopSyncs;

    public ShopListSync() {
        super();
    }

    public ShopListSync (JSONObject jsonObject) throws JSONException {
        shopSyncs = new ArrayList<ShopSync>();

        JSONArray shops = jsonObject.getJSONArray("shops");
        for (int i = 0; i < shops.length(); i++) {

            JSONObject object = shops.getJSONObject(i);
            ShopSync  shopSync = new ShopSync(object);

            shopSyncs.add(shopSync);
        }
    }

    public ArrayList<ShopSync> getListShopSync() {
//        if (shipperSyncs == null) {
//            shipperSyncs = new ArrayList<ShipperSync>();
//        }
        return shopSyncs;
    }

}
