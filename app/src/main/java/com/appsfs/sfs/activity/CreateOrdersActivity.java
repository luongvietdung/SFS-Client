package com.appsfs.sfs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.appsfs.sfs.R;
import com.appsfs.sfs.Utils.SFSPreference;
import com.appsfs.sfs.api.helper.CustomRespond;

/**
 * Created by longdv on 5/19/16.
 */
public class CreateOrdersActivity extends AppCompatActivity implements Response.Listener<CustomRespond>, Response.ErrorListener {
    EditText mCodeOrder,mPhoneCustomer,mPhoneShipper,mCodeCheckOrder;
    Button mButtonCreateOrder;
    SFSPreference mSfsPreference;


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

        mCodeOrder = (EditText) findViewById(R.id.edit_code_order);
        mCodeOrder.setText(mSfsPreference.getString("order_code", ""));
        mCodeOrder.setEnabled(false);
        mPhoneCustomer = (EditText) findViewById(R.id.edit_phone_customer);
        mPhoneShipper = (EditText) findViewById(R.id.edit_phone_shipper);
        mCodeCheckOrder = (EditText) findViewById(R.id.edit_code_check_order);
        mButtonCreateOrder = (Button) findViewById(R.id.btn_create_orders);

    }

    @Override
    public void onResponse(CustomRespond response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


}
