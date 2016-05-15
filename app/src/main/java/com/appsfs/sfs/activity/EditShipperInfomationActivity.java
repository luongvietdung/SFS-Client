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
import com.appsfs.sfs.Objects.Shipper;
import com.appsfs.sfs.R;
import com.appsfs.sfs.Utils.SFSPreference;
import com.appsfs.sfs.Utils.Utils;
import com.appsfs.sfs.api.function.EditShipper;
import com.appsfs.sfs.api.function.EditShop;
import com.appsfs.sfs.api.helper.CustomRespond;
import com.appsfs.sfs.api.sync.ShipperSync;
import com.appsfs.sfs.api.sync.ShopSync;
import com.appsfs.sfs.api.sync.UserSync;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by longdv on 4/28/16.
 */
public class EditShipperInfomationActivity extends AppCompatActivity implements Response.Listener<CustomRespond>, Response.ErrorListener{
    EditText name, address, money_ship, money;
    Button mButtonSaveInformation;
    SFSPreference mPreference;
    ShipperSync shipperSync;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shipper);
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
        mPreference = SFSPreference.getInstance(EditShipperInfomationActivity.this);
        name = (EditText) findViewById(R.id.edit_shipper_name);
        address = (EditText) findViewById(R.id.edit_shipper_address);
        money_ship = (EditText) findViewById(R.id.edit_shipper_fee);
        money = (EditText) findViewById(R.id.edit_shipper_money);
        mButtonSaveInformation = (Button) findViewById(R.id.btn_save_information);

        try {
            UserSync userSync = new UserSync(new JSONObject(mPreference.getString("user_json", "")));

            shipperSync = (ShipperSync) userSync.getAccountSync();
            setInitValue(shipperSync);

        } catch (JSONException e) {
            Log.e("aaa", e.getMessage());
            e.getMessage();
        }

        mButtonSaveInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address.getText().toString().equals("")) {

                } else {
                    Shipper shipper = new Shipper();
                    shipper.setId(shipperSync.getId());
                    shipper.setAddress(address.getText().toString());
                    shipper.setName(name.getText().toString());
                    shipper.setMoney(Integer.valueOf(money.getText().toString()));
                    shipper.setMoneyShip(Integer.valueOf(money_ship.getText().toString()));

                    requestEditShipper(shipper);
                }
            }
        });

    }

    private void requestEditShipper(Shipper shipper) {
        EditShipper editShipper = new EditShipper(EditShipperInfomationActivity.this, this,this, shipper);
        editShipper.start();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("FAIL", "FAIL");
        Utils.getInstance().showToast(EditShipperInfomationActivity.this,"Save information fail!");
    }

    @Override
    public void onResponse(CustomRespond response) {
        Log.e("SUCCESS", response.toString());
        mPreference.putString("user_json", response.getData().toString());
        Utils.getInstance().showToast(EditShipperInfomationActivity.this,"Save information success !");

    }

    private void setInitValue(ShipperSync shipperSync) {
        if (shipperSync == null)
            return;

        name.setText(shipperSync.getName());
        address.setText(shipperSync.getAddress());
        money_ship.setText(String.valueOf(shipperSync.getFee()));
        money.setText(String.valueOf(shipperSync.getMoney()));
    }
}
