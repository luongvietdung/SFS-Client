package com.appsfs.sfs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.appsfs.sfs.Objects.Validation;
import com.appsfs.sfs.R;
import com.appsfs.sfs.Utils.Utils;
import com.appsfs.sfs.api.function.VailidationOrder;
import com.appsfs.sfs.api.helper.CustomRespond;

/**
 * Created by longdv on 5/19/16.
 */
public class CheckOrdersActivity extends AppCompatActivity implements Response.Listener<CustomRespond>, Response.ErrorListener {

    EditText mPhoneShop,mCodeOrder,mCodeCheck;
    Button mButtonCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_orders);

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


        mPhoneShop = (EditText) findViewById(R.id.edit_phone_shop);
        mCodeOrder = (EditText) findViewById(R.id.edit_code_order);
        mCodeCheck = (EditText) findViewById(R.id.edit_code_check);
        mButtonCheck = (Button) findViewById(R.id.btn_check_order);

        mButtonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCheck();
            }
        });
    }

    @Override
    public void onResponse(CustomRespond response) {
        Utils.getInstance().showToast(CheckOrdersActivity.this, "Validate success");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Utils.getInstance().showDiaglog(CheckOrdersActivity.this, "Validate unsuccessful", "Some thing wrong!");
    }

    private boolean validate() {
        return !mCodeCheck.getText().toString().isEmpty() && !mCodeOrder.getText().toString().isEmpty() && !mPhoneShop.getText().toString().isEmpty();
    }

    private void onClickCheck() {
        if (!validate()) {
            Utils.getInstance().showDiaglog(CheckOrdersActivity.this, "Validate unsuccessful", "Please enter all field!");
            return;
        }

        Validation validation = new Validation();
        validation.setCodeCheckOrder(mCodeCheck.getText().toString());
        validation.setCodeOrder(mCodeOrder.getText().toString());
        validation.setPhoneShop(mPhoneShop.getText().toString());

        requestAPI(validation);
    }

    private void requestAPI(Validation validation) {
        new VailidationOrder(CheckOrdersActivity.this, this, this, validation).start();
    }
}
