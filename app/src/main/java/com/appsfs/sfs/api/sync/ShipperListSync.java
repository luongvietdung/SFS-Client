package com.appsfs.sfs.api.sync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by longdv on 5/14/16.
 */
public class ShipperListSync {
    private ArrayList<ShipperSync> shipperSyncs;

    public ShipperListSync() {
        super();
    }

    public ShipperListSync (JSONObject jsonObject) throws JSONException{
        shipperSyncs = new ArrayList<ShipperSync>();

        JSONArray shippers = jsonObject.getJSONArray("shippers");
        for (int i = 0; i < shippers.length(); i++) {

            JSONObject object = shippers.getJSONObject(i);
            ShipperSync  shipperSync = new ShipperSync(object);

            shipperSyncs.add(shipperSync);
        }
    }

    public ArrayList<ShipperSync> getListShipperSync() {
//        if (shipperSyncs == null) {
//            shipperSyncs = new ArrayList<ShipperSync>();
//        }
        return shipperSyncs;
    }
}
