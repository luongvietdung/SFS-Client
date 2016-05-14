package com.appsfs.sfs.api.function;

import android.content.Context;
import android.location.Location;

import com.android.volley.Request;
import com.android.volley.Response;
import com.appsfs.sfs.Objects.User;
import com.appsfs.sfs.api.helper.RequestHelper;

import org.json.JSONObject;

/**
 * Created by dunglv on 5/14/16.
 */
public class UpdateUser {


    private RequestHelper requestHelper;

     public UpdateUser(Context context,int id,double lat, double lng,Response.Listener listener, Response.ErrorListener errorListener) {
         requestHelper = new RequestHelper(context,"/api/users/" + id, Request.Method.PUT, creatParams(lat,lng), listener, errorListener);

     }


    public void start() {
        requestHelper.start("LOCATION");
    }

    private JSONObject creatParams(double lat,double lng) {
        try {
            JSONObject users = new JSONObject();
            users.put("latitude", lat);
            users.put("longitude", lng);
            JSONObject object = new JSONObject();
            object.put("user", users);
            return object;
        } catch (Exception e) {
            e.getMessage();
            return  new JSONObject();
        }
    }
}
