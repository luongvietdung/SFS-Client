package com.appsfs.sfs.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.appsfs.sfs.R;
import com.appsfs.sfs.Utils.SFSPreference;
import com.appsfs.sfs.Objects.User;
import com.appsfs.sfs.Utils.Utils;
import com.appsfs.sfs.api.helper.CustomRespond;
import com.appsfs.sfs.database.DatabaseHelperUser;

import org.json.JSONObject;

/**
 * Created by longdv on 4/3/16.
 */
public class RegisterActivity extends AppCompatActivity implements Response.Listener<CustomRespond>, Response.ErrorListener{
    EditText mEmail,mPhoneNumbers,mPassword,mConfirmPassword;
    RadioButton radioShipper,radioShop;
    RadioGroup radioGroup;
    Button mButtonNext;
    int type_user = 0;     // O: shipper
                            // 1: Shop
                            // -1 : not create

    SFSPreference mSfsPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSfsPreference = SFSPreference.getInstance(RegisterActivity.this);
        mEmail = (EditText) findViewById(R.id.email);
        mPhoneNumbers = (EditText) findViewById(R.id.phone_number);
        mPassword = (EditText) findViewById(R.id.password);
        mConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        radioShipper = (RadioButton) findViewById(R.id.radio_shipper);
        radioShop = (RadioButton) findViewById(R.id.radio_shop);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mButtonNext = (Button) findViewById(R.id.btn_next);

        mPhoneNumbers.setText(Utils.getInstance().getMyPhoneNumber(getApplicationContext()));

        final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_shipper:
                        // do operations specific to this selection
                        type_user = 0;
                        break;

                    case R.id.radio_shop:
                        // do operations specific to this selection
                        type_user = 1;
                        break;
                }
            }
        });

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextClick();
            }
        });


    }

    @Override
    public void onResponse(CustomRespond response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    private void onNextClick() {
        if((!Utils.getInstance().isValidPhoneNumber(mPhoneNumbers.getText().toString()))) {
            Utils.getInstance().showDiaglog(RegisterActivity.this,"Error","Phone number error!");
        }
        else if (mPassword.getText().toString().length() < 8 ||
                (!mConfirmPassword.getText().toString().equals(mPassword.getText().toString()))) {

            Utils.getInstance().showToast(getApplicationContext(),"Password length 8 characters or more!");
        }
        else {
            mSfsPreference.putString("pre_phone_number",mPhoneNumbers.getText().toString());
            mSfsPreference.putString("pre_email",mEmail.getText().toString());
            mSfsPreference.putString("pre_password",mPassword.getText().toString());
            mSfsPreference.putInt("type_user",type_user);

            if (type_user == 0) {
                Intent intent = new Intent(RegisterActivity.this,ShipperInfomationActivity.class);
                startActivity(intent);
            } else if (type_user == 1) {
                Intent intent = new Intent(RegisterActivity.this,ShopInfomationActivity.class);
                startActivity(intent);
            }
        }

    }
}
