package com.appsfs.sfs.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.appsfs.sfs.Objects.User;
import com.appsfs.sfs.R;
import com.appsfs.sfs.Utils.SFSPreference;
import com.appsfs.sfs.Objects.Shipper;
import com.appsfs.sfs.Utils.Utils;
import com.appsfs.sfs.api.function.RegisterUser;
import com.appsfs.sfs.database.DatabaseHelperShipper;
import com.appsfs.sfs.database.DatabaseHelperUser;

import org.json.JSONObject;

/**
 * Created by longdv on 4/21/16.
 */
public class ShipperInfomationActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    EditText mNameShipper,mPhoneShipper,mAddress,mMoney,mMoneyShip;
    SFSPreference mSfsPreference;
    Button mButtonComplete;
    DatabaseHelperShipper databaseHelperShipper;
    DatabaseHelperUser databaseHelperUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_info);

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

        databaseHelperShipper = DatabaseHelperShipper.getInstance(ShipperInfomationActivity.this);
        databaseHelperUser = DatabaseHelperUser.getInstance(ShipperInfomationActivity.this);
        mSfsPreference = SFSPreference.getInstance(ShipperInfomationActivity.this);

        mNameShipper = (EditText)findViewById(R.id.ship_name);
        mPhoneShipper = (EditText) findViewById(R.id.ship_phone);
        mAddress = (EditText) findViewById(R.id.ship_address);
        mMoney = (EditText) findViewById(R.id.ship_money);
        mMoneyShip = (EditText) findViewById(R.id.ship_money_ship);
        mButtonComplete = (Button) findViewById(R.id.btn_ship_add);

        mPhoneShipper.setText(mSfsPreference.getString("pre_phone_number", ""));
        mPhoneShipper.setEnabled(false);

        mButtonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameShipper.getText().toString();
                String phone = mPhoneShipper.getText().toString();
                String address = mAddress.getText().toString();
                String money = mMoney.getText().toString();
                String money_ship = mMoneyShip.getText().toString();
                if (name.equals("")) {
                    Utils.getInstance().showToast(getApplicationContext(), "Name Empty");
                } else if (address.equals("")) {
                    Utils.getInstance().showToast(getApplicationContext(), "Address Empty");
                } else if (money.equals("")) {
                    Utils.getInstance().showToast(getApplicationContext(), "Money Empty");
                } else if (money_ship.equals("")) {
                    Utils.getInstance().showToast(getApplicationContext(), "Money Empty");
                } else {
                    User user = new User();
                    user.setEmail(mSfsPreference.getString("pre_email", ""));
                    user.setPassword(mSfsPreference.getString("pre_password", ""));
                    user.setPhoneNumbers(mSfsPreference.getString("pre_phone_number", ""));
                    user.setRole(0);
                    Shipper shipper = new Shipper();
                    shipper.setName(name);
                    shipper.setPhoneNumber(phone);
                    shipper.setAddress(address);
                    shipper.setMoney(Integer.valueOf(money));
                    shipper.setMoneyShip(Integer.valueOf(money_ship));
                    shipper.setUser(user);
                    requestAPI(shipper);

//                    long ret_insert = databaseHelperShipper.insertShipperInfo(shipper);
//                    if (ret_insert != -1) {
//
//                        Utils.getInstance().changeActivity(ShipperInfomationActivity.this,LoginActivity.class);
//
//                    } else {
//                        Utils.getInstance().showToast(ShipperInfomationActivity.this,"Not completed!");
//                    }
                }

            }
        });


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Utils.getInstance().showToast(ShipperInfomationActivity.this,"Phone duplicate");
    }

    @Override
    public void onResponse(JSONObject response) {
        // Insert to database local
        Utils.getInstance().changeActivity(ShipperInfomationActivity.this,LoginActivity.class);
    }

    private void requestAPI(Shipper shipper) {
        RegisterUser registerUser = new RegisterUser(shipper, getApplicationContext(),this, this);
        registerUser.start();
    }
}
