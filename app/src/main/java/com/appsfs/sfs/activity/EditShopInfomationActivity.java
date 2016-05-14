package com.appsfs.sfs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appsfs.sfs.R;
import com.appsfs.sfs.Utils.GeolocationUtils;
import com.appsfs.sfs.Utils.SFSPreference;
import com.appsfs.sfs.Objects.Shop;
import com.appsfs.sfs.Objects.User;
import com.appsfs.sfs.Utils.Utils;
import com.appsfs.sfs.database.DatabaseHelperShop;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by longdv on 4/28/16.
 */
public class EditShopInfomationActivity extends AppCompatActivity {
    EditText name,address,product,money;
    Button mButtonSaveInformation;
    DatabaseHelperShop databaseHelperShop;
    SFSPreference mPreference;
    ArrayList<User> mArrayUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop);

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

        databaseHelperShop = DatabaseHelperShop.getInstance(EditShopInfomationActivity.this);
        mPreference = SFSPreference.getInstance(EditShopInfomationActivity.this);

        name = (EditText) findViewById(R.id.edit_shop_name);
        address = (EditText) findViewById(R.id.edit_shop_address);
        product = (EditText) findViewById(R.id.edit_shop_product);
        money = (EditText) findViewById(R.id.edit_shop_money);
        mButtonSaveInformation = (Button) findViewById(R.id.btn_save_information);

        final String txtPhone = mPreference.getString("user_phone","");
        final String txtName = mPreference.getString("shop_name","");
        final String txtAddress = mPreference.getString("shop_address","");
        final String txtProduct = mPreference.getString("shop_product","");

        name.setText(txtName);
        address.setText(txtAddress);
        product.setText(txtProduct);
        int ret = 0;
        money.setText(String.valueOf(mPreference.getInt("shop_money", ret)));

        mButtonSaveInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address.getText().toString().equals("")) {

                } else {
                    String txtAddress = address.getText().toString();
                    LatLng latLng = GeolocationUtils.getInstance().getLatLongFromAddress(EditShopInfomationActivity.this,txtAddress);

                    Shop shop = new Shop();
                    shop.setName(name.getText().toString());
                    shop.setAddress(txtAddress);
                    shop.setProduct(product.getText().toString());
                    shop.setMoney(Integer.valueOf(money.getText().toString()));
                    shop.setLatitude(latLng.latitude);
                    shop.setLongitude(latLng.longitude);

                    boolean isUpdate = databaseHelperShop.updatePhoneInformation(txtPhone,shop);
                    if (isUpdate) {
                        Utils.getInstance().showToast(EditShopInfomationActivity.this,"Save information success !");

                    } else {
                        Utils.getInstance().showToast(EditShopInfomationActivity.this,"Save information fail!");
                    }

                }
            }
        });

    }
}
