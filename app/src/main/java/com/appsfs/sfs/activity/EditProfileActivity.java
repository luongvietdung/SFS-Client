package com.appsfs.sfs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appsfs.sfs.R;
import com.appsfs.sfs.Utils.SFSPreference;
import com.appsfs.sfs.Objects.User;
import com.appsfs.sfs.Utils.Utils;
import com.appsfs.sfs.database.DatabaseHelperShipper;
import com.appsfs.sfs.database.DatabaseHelperShop;
import com.appsfs.sfs.database.DatabaseHelperUser;

import java.util.ArrayList;

/**
 * Created by longdv on 4/18/16.
 */
public class EditProfileActivity extends AppCompatActivity {

    EditText phone_number,email;
    Button mButtonSaveProfile,mButtonDeleteProfile;
    DatabaseHelperUser databaseHelperUser;
    DatabaseHelperShipper databaseHelperShipper;
    DatabaseHelperShop databaseHelperShop;
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
        email = (EditText) findViewById(R.id.profile_email);
        mButtonSaveProfile = (Button) findViewById(R.id.btn_save_profile);
        mButtonDeleteProfile = (Button) findViewById(R.id.btn_delete_profile);

        databaseHelperUser = DatabaseHelperUser.getInstance(getApplicationContext());
        databaseHelperShipper = DatabaseHelperShipper.getInstance(getApplicationContext());
        databaseHelperShop = DatabaseHelperShop.getInstance(getApplicationContext());

        mPreference = SFSPreference.getInstance(getApplicationContext());

        final String txtPhone = mPreference.getString("user_phone","");
        String txtEmail = mPreference.getString("user_email","");
        final int role = 0;
        mPreference.getInt("user_role", role);
        phone_number.setText(txtPhone);
        email.setText(txtEmail);

        mButtonSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Update table db : user, shop, shipper*/
                if (phone_number.getText().toString().equals("") ||
                        email.getText().toString().equals("")) {

                } else {
                    boolean isOk = databaseHelperUser.updatePhoneNumber(txtPhone,
                                                                        phone_number.getText().toString(),
                                                                        email.getText().toString());
                    if (isOk) {
                        if (role == 0) {
                            databaseHelperShipper.updatePhoneNumber(txtPhone, phone_number.getText().toString());
                        } else if (role == 1) {
                            databaseHelperShop.updatePhoneNumber(txtPhone, phone_number.getText().toString());;
                        }

                        Utils.getInstance().showToast(EditProfileActivity.this,"Edit profile success!");

                    }
                }


            }
        });

        mButtonDeleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*delete table db : user, shop, shipper*/
                long ret_user = databaseHelperUser.deleteUserInfo(txtPhone);
                if (ret_user != -1) {
                    if (role == 0) {
                        databaseHelperShipper.deleteShipperInfo(txtPhone);
                    } else if (role == 1) {
                        databaseHelperShop.deleteShopInfo(txtPhone);
                    }

                    Utils.getInstance().changeActivity(EditProfileActivity.this,MainActivity.class);
                }
            }
        });
        mArrayUsers = new ArrayList<User>();
        mArrayUsers = databaseHelperUser.getAllUsers();



    }
}
