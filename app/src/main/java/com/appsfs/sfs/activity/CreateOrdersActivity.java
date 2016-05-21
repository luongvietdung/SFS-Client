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
import com.appsfs.sfs.Objects.Orders;
import com.appsfs.sfs.R;
import com.appsfs.sfs.Utils.SFSPreference;
import com.appsfs.sfs.Utils.Utils;
import com.appsfs.sfs.api.function.CreateOrder;
import com.appsfs.sfs.api.function.GetShipperOnline;
import com.appsfs.sfs.api.helper.CustomRespond;
import com.appsfs.sfs.api.sync.UserSync;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

/**
 * Created by longdv on 5/19/16.
 */
public class CreateOrdersActivity extends AppCompatActivity implements Response.Listener<CustomRespond>, Response.ErrorListener {
    EditText mCodeOrder,mPhoneCustomer,mPhoneShipper,mCodeCheckOrder;
    Button mButtonCreateOrder;
    SFSPreference mSfsPreference;

    UserSync userSync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

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
        mSfsPreference = SFSPreference.getInstance(this);

        String json = mSfsPreference.getString("user_json","");
        try {
            userSync = new UserSync(new JSONObject(json));
        } catch (Exception e) {
            Log.d("sabdjkasdk",e.getLocalizedMessage());
        }

        mCodeOrder = (EditText) findViewById(R.id.edit_code_order);
        mCodeOrder.setText(mSfsPreference.getString("order_code", ""));
        mCodeOrder.setEnabled(false);


        mPhoneCustomer = (EditText) findViewById(R.id.edit_phone_customer);
        mPhoneShipper = (EditText) findViewById(R.id.edit_phone_shipper);
        mCodeCheckOrder = (EditText) findViewById(R.id.edit_code_check_order);
        mButtonCreateOrder = (Button) findViewById(R.id.btn_create_orders);

        mButtonCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateOrder();
            }
        });

    }

    @Override
    public void onResponse(CustomRespond response) {
        Log.e("aaa", response.getData().toString());
        Utils.getInstance().showToast(CreateOrdersActivity.this,"Create order success!");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Utils.getInstance().showToast(CreateOrdersActivity.this,"Create order!");
    }

    private boolean validate() {

        return !mCodeOrder.getText().toString().isEmpty() && !mCodeCheckOrder.getText().toString().isEmpty() && !mPhoneCustomer.getText().toString().isEmpty() && !mPhoneShipper.getText().toString().isEmpty();
    }

    private void onCreateOrder() {
        if (!validate()) {
            Utils.getInstance().showDiaglog(CreateOrdersActivity.this, "Create orders unsuccessful", "Please enter all field!");
            return;
        }

        Orders orders = new Orders();
        orders.setOwner(userSync);
        orders.setCodeCheckOrder(mCodeCheckOrder.getText().toString());
        orders.setCodeOrder(mCodeOrder.getText().toString());
        orders.setPhoneShipper(mPhoneShipper.getText().toString());
        orders.setPhoneCustomer(mPhoneCustomer.getText().toString());

        startRequest(orders);

    }

    private void startRequest(Orders orders) {
        new CreateOrder(CreateOrdersActivity.this, this, this, orders).start();
    }


}
