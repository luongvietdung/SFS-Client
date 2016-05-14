package com.appsfs.sfs.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.appsfs.sfs.Objects.User;
import com.appsfs.sfs.R;
import com.appsfs.sfs.Utils.GeolocationUtils;
import com.appsfs.sfs.Utils.SFSPreference;
import com.appsfs.sfs.Objects.Shop;
import com.appsfs.sfs.Utils.Utils;
import com.appsfs.sfs.api.function.RegisterUser;
import com.appsfs.sfs.api.sync.ShopSync;
import com.appsfs.sfs.database.DatabaseHelperShop;
import com.appsfs.sfs.database.DatabaseHelperUser;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

/**
 * Created by longdv on 4/20/16.
 */
public class ShopInfomationActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    EditText mNameShop,mPhoneShop,mAddressShop,mProduct,mMoney;
    SFSPreference mSfsPreference;
    Button mButtonComplete;
    DatabaseHelperShop databaseHelperShop;
    DatabaseHelperUser databaseHelperUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
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

        databaseHelperShop = DatabaseHelperShop.getInstance(ShopInfomationActivity.this);
        databaseHelperUser = DatabaseHelperUser.getInstance(ShopInfomationActivity.this);
        mSfsPreference = SFSPreference.getInstance(ShopInfomationActivity.this);



        mNameShop = (EditText)findViewById(R.id.shop_name);
        mPhoneShop = (EditText) findViewById(R.id.shop_phone);
        mAddressShop = (EditText) findViewById(R.id.shop_address);
        mProduct = (EditText) findViewById(R.id.shop_product);
        mMoney = (EditText) findViewById(R.id.shop_money);
        mButtonComplete = (Button) findViewById(R.id.btn_shop_add);

        mPhoneShop.setText(mSfsPreference.getString("pre_phone_number", ""));
        mPhoneShop.setEnabled(false);

        mButtonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mNameShop.getText().toString();
                String phone = mPhoneShop.getText().toString();
                String address = mAddressShop.getText().toString();
                String product = mProduct.getText().toString();
                String money = mMoney.getText().toString();
                if (name.equals("")) {
                    Utils.getInstance().showToast(getApplicationContext(), "Name Empty");
                } else if (address.equals("")) {
                    Utils.getInstance().showToast(getApplicationContext(), "Address Empty");
                } else if (money.equals("")) {
                    Utils.getInstance().showToast(getApplicationContext(), "Money Empty");
                } else if (product.equals("")) {
                    Utils.getInstance().showToast(getApplicationContext(), "Product Empty");
                } else {

                    User user = new User();
                    user.setEmail(mSfsPreference.getString("pre_email", ""));
                    user.setPassword(mSfsPreference.getString("pre_password", ""));
                    user.setPhoneNumbers(mSfsPreference.getString("pre_phone_number", ""));
                    user.setRole(1);

                    LatLng latLng = null;
                    Shop shop = new Shop();
                    shop.setName(name);
                    shop.setPhoneNumber(phone);
                    shop.setAddress(address);
                    shop.setProduct(product);
                    shop.setMoney(Integer.valueOf(money));
                    shop.setUser(user);

                    latLng = GeolocationUtils.getInstance().getLatLongFromAddress(ShopInfomationActivity.this,address);
                    shop.setLatitude(latLng.latitude);
                    shop.setLongitude(latLng.longitude);

                    requestAPI(shop);



//                    long ret_insert = databaseHelperShop.insertShopInfo(shop);
//                    if (ret_insert != -1) {
//                        Utils.getInstance().changeActivity(ShopInfomationActivity.this,LoginActivity.class);
//
//                    } else {
//                        Utils.getInstance().showToast(ShopInfomationActivity.this,"Not completed!");
//                    }

                }

            }
        });

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Utils.getInstance().showToast(ShopInfomationActivity.this,"phone duplicate");
    }

    @Override
    public void onResponse(JSONObject response) {
        Utils.getInstance().changeActivity(ShopInfomationActivity.this,LoginActivity.class);
    }

    private void requestAPI(Shop shop) {
        RegisterUser registerUser = new RegisterUser(shop, getApplicationContext(),this, this);
        registerUser.start();
    }
}
