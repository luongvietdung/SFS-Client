package com.appsfs.sfs.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.appsfs.sfs.Objects.Shipper;
import com.appsfs.sfs.Objects.Shop;
import com.appsfs.sfs.Objects.User;
import com.appsfs.sfs.Utils.Utils;
import com.appsfs.sfs.api.function.LoginUser;
import com.appsfs.sfs.api.function.UpdateUser;
import com.appsfs.sfs.api.sync.ShipperSync;
import com.appsfs.sfs.api.sync.ShopSync;
import com.appsfs.sfs.api.sync.UserSync;
import com.appsfs.sfs.database.DatabaseHelperShipper;
import com.appsfs.sfs.database.DatabaseHelperShop;
import com.appsfs.sfs.database.DatabaseHelperUser;
import com.appsfs.sfs.service.GPSService;

import org.json.JSONObject;

/**
 * Created by longdv on 4/3/16.
 */
public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    EditText phoneNumber;
    EditText password;
    Button mButtonLogin;
    DatabaseHelperUser databaseHelperUser;
    DatabaseHelperShop databaseHelperShop;
    DatabaseHelperShipper databaseHelperShipper;
    SFSPreference mSfsPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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


        phoneNumber = (EditText)findViewById(R.id.phone_number);
        password = (EditText) findViewById(R.id.password);
        mButtonLogin = (Button) findViewById(R.id.btn_login);
        mSfsPreference = SFSPreference.getInstance(this);

//        databaseHelperUser = DatabaseHelperUser.getInstance(getApplicationContext());
//        databaseHelperShipper = DatabaseHelperShipper.getInstance(getApplicationContext());
//        databaseHelperShop = DatabaseHelperShop.getInstance(getApplicationContext());


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLogin();

            }
        });

    }


    @Override
    public void onResponse(JSONObject response) {
        Log.e("Data","onResponse");
        try {
            Log.e("Data", response.toString());


            UserSync userSync = new UserSync(response);
            mSfsPreference.putString("user_json", response.toString());
//            mSfsPreference.putInt("current_user",userSync.getAccountable_id());
//            mSfsPreference.putString("current_user_type",userSync.getAccountable_type());
//            mSfsPreference.putString("current_user_name",userSync.getPhone());
//            mSfsPreference.putString("current_user", response.toString());
            mSfsPreference.putInt("current_id_user", userSync.getId());
            if (userSync.getRole() == 0) {
                 /* Start service */
                final int startupID =1111111;
                final AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

                try
                {
                    Intent intent1 = new Intent(this,GPSService.class);
                    PendingIntent serviceManager = PendingIntent.getService(this, startupID, intent1, 0);
                    alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 1000  * 10, serviceManager);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            switch (userSync.getRole()) {
                case 0:
                    startShipper();
                    break;
                case 1:
                    startShop();
                    break;
                default:
                    Utils.getInstance().showDiaglog(LoginActivity.this, "Login unsuccessful", "Something wrongI!");
                    return;
            }


        } catch (Exception e) {
            e.getMessage();
            Utils.getInstance().showDiaglog(LoginActivity.this, "Login unsuccessful", "Something wrongI!");
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("TAG", "onErrorResponse");
//        if(error.networkResponse.statusCode == 401) {
//            Utils.getInstance().showDiaglog(LoginActivity.this, "Login unsuccessful","Please check username and password!");
//        } else {
//            Utils.getInstance().showDiaglog(LoginActivity.this, "Login unsuccessful", "Cannot connect API!");
//        }
    }

    private void startShipper() {
        Intent intent = new Intent(getApplicationContext(),SFSShipperMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startShop() {
        Intent intent = new Intent(getApplicationContext(),SFSShopMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void clickLogin() {
        String txtPhone = phoneNumber.getText().toString();
        String txtPass = password.getText().toString();
        if(txtPhone.equals("") || txtPhone == null){
            Utils.getInstance().showToast(getApplicationContext(), "Username Empty");
        }else if(txtPass.equals("") || txtPass == null){
            Utils.getInstance().showToast(getApplicationContext(), "Password Empty");
        } else {
            LoginUser loginUser = new LoginUser(txtPhone, txtPass, getApplicationContext(), this, this);
            loginUser.start();

//            int id = 26;
//            double lat =12.2312321;
//            double lng =80.21321;
//            UpdateUser updateUser = new UpdateUser(LoginActivity.this,id,lat,lng,this,this);
//            updateUser.start();
        }
    }
}
