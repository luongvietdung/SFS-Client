package com.appsfs.sfs.api.function;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.appsfs.sfs.Objects.Shop;
import com.appsfs.sfs.Objects.User;
import com.appsfs.sfs.api.helper.RequestHelper;
import com.appsfs.sfs.api.sync.UserSync;

import org.json.JSONObject;

/**
 * Created by dunglv on 5/15/16.
 */
public class EditUser {
    public static final String EDIT_USER = "Edit User";
    private RequestHelper requestHelper;

    public EditUser(Context context, Response.Listener listener, Response.ErrorListener errorListener, User user, UserSync userSync) {
        this.requestHelper = new RequestHelper(context, "/api/users/" + user.getId(), Request.Method.PUT, params(user, userSync), listener, errorListener, EDIT_USER);
    }

    public void start() {
        requestHelper.start("SHOP UPDATE");
    }

    private JSONObject params(User user, UserSync userSync) {
        try {
            JSONObject userJson = new JSONObject();
            if (!user.getPhoneNumbers().equals(userSync.getPhone())) {
                userJson.put("phone", user.getPhoneNumbers());
            }
            if (!user.getPassword().equals("")) {
                userJson.put("password", user.getPassword());
                userJson.put("password_confirmation", user.getPassword());
            }
            return new JSONObject().put("user", userJson);
        } catch (Exception e) {
            e.getMessage();
        }
        return new JSONObject();
    }
}
