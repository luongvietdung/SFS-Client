package com.appsfs.sfs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.appsfs.sfs.R;
import com.appsfs.sfs.Utils.SFSPreference;
import com.appsfs.sfs.Objects.User;
import com.appsfs.sfs.Utils.Utils;
import com.appsfs.sfs.api.function.DeleteUser;
import com.appsfs.sfs.api.function.EditUser;
import com.appsfs.sfs.api.helper.CustomRespond;
import com.appsfs.sfs.api.sync.ShipperSync;
import com.appsfs.sfs.api.sync.UserSync;
import com.appsfs.sfs.database.DatabaseHelperShipper;
import com.appsfs.sfs.database.DatabaseHelperShop;
import com.appsfs.sfs.database.DatabaseHelperUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by longdv on 4/18/16.
 */
public class EditProfileActivity extends AppCompatActivity implements Response.Listener<CustomRespond>, Response.ErrorListener{

    EditText phone_number,password, confirm_password;
    Button mButtonSaveProfile,mButtonDeleteProfile;
    UserSync userSync;
//    DatabaseHelperUser databaseHelperUser;
//    DatabaseHelperShipper databaseHelperShipper;
//    DatabaseHelperShop databaseHelperShop;
    SFSPreference mPreference;
    ArrayList<User> mArrayUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        phone_number = (EditText) findViewById(R.id.profile_phone_number);
        password = (EditText) findViewById(R.id.profile_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        mButtonSaveProfile = (Button) findViewById(R.id.btn_save_profile);
        mButtonDeleteProfile = (Button) findViewById(R.id.btn_delete_profile);
        mPreference = SFSPreference.getInstance(getApplicationContext());

        try {
            userSync = new UserSync(new JSONObject(mPreference.getString("user_json", "")));
            phone_number.setText(userSync.getPhone());

        } catch (JSONException e) {
            Log.e("aaa", e.getMessage());
            e.getMessage();
        }

//        databaseHelperUser = DatabaseHelperUser.getInstance(getApplicationContext());
//        databaseHelperShipper = DatabaseHelperShipper.getInstance(getApplicationContext());
//        databaseHelperShop = DatabaseHelperShop.getInstance(getApplicationContext());


//        final String txtPhone = mPreference.getString("user_phone","");
//        String txtEmail = mPreference.getString("user_email","");
//        final int role = 0;
//        mPreference.getInt("user_role", role);
//        phone_number.setText(txtPhone);
//        email.setText(txtEmail);

        mButtonSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone_number.getText().toString().equals("")) {
                    Utils.getInstance().showToast(EditProfileActivity.this,"Please enter phone!");

                } else if (!password.getText().toString().equals(confirm_password.getText().toString())) {
                    Utils.getInstance().showToast(EditProfileActivity.this,"Password and confirm password not match!");
                }
                else {
                    User user = new User();
                    user.setId(userSync.getId());
                    user.setPhoneNumbers(phone_number.getText().toString());
                    user.setPassword(password.getText().toString());
                    requestAPI(user, userSync);
                }
                /*Update table db : user, shop, shipper*/
//                if (phone_number.getText().toString().equals("") ||
//                        email.getText().toString().equals("")) {
//
//                } else {
//                    boolean isOk = databaseHelperUser.updatePhoneNumber(txtPhone,
//                                                                        phone_number.getText().toString(),
//                                                                        email.getText().toString());
//                    if (isOk) {
//                        if (role == 0) {
//                            databaseHelperShipper.updatePhoneNumber(txtPhone, phone_number.getText().toString());
//                        } else if (role == 1) {
//                            databaseHelperShop.updatePhoneNumber(txtPhone, phone_number.getText().toString());;
//                        }
//
//                        Utils.getInstance().showToast(EditProfileActivity.this,"Edit profile success!");
//
//                    }
//                }


            }
        });

        mButtonDeleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Hanh dong nguy hiem chua enable
                deleteUser(userSync);

            }
        });

    }

    private void requestAPI(User user, UserSync userSync) {
        EditUser editUser = new EditUser(EditProfileActivity.this, this, this, user, userSync);
        editUser.start();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("FAIL", "FAIL");
        Utils.getInstance().showToast(EditProfileActivity.this,"Phone number change dupplicate!");
    }

    @Override
    public void onResponse(CustomRespond response) {
        Log.e("SUCCESS", response.getData().toString());
        if (response.getFrom().equalsIgnoreCase(DeleteUser.DELETE_USER)) {
            Utils.getInstance().changeActivity(EditProfileActivity.this,MainActivity.class);
        } else {
            mPreference.putString("user_json", response.getData().toString());
            Utils.getInstance().showToast(EditProfileActivity.this,"Save profile success !");
        }

    }

    private void deleteUser(UserSync userSync) {
//        Utils.getInstance().showDiaglog(EditProfileActivity.this,"Delete user!", "Are you sure?");
        DeleteUser deleteUser = new DeleteUser(EditProfileActivity.this, this, this, userSync);
        deleteUser.start();
    }

}
