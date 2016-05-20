package com.appsfs.sfs.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.appsfs.sfs.R;
import com.appsfs.sfs.Utils.Utils;

public class MainActivity extends AppCompatActivity {

    Button mButtonStartLogin;
    Button mButtonStartRegister;
    boolean isGPS,isNetwork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
/*       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        mButtonStartLogin = (Button) findViewById(R.id.btn_start_login);
        mButtonStartRegister = (Button) findViewById(R.id.btn_start_register);

        isGPS = isGPSEnabled(getApplicationContext()); /* Check turn on GPS */
        isNetwork = Utils.getInstance().checkNetworkState(getApplicationContext()); /* Check turn on GPS */
        if (!isGPS) {
            Utils.getInstance().showEnableGPSDiaglog(MainActivity.this, "Location Service Disabled", "Please enable location services");
            if (isNetwork == true) {
                mButtonStartLogin.setOnClickListener(handlerClickButton);
                mButtonStartRegister.setOnClickListener(handlerClickButton);
            } else {
                Utils.getInstance().showDiaglog(MainActivity.this, "Network Disabled", "Please turn on Wifi or 3G");

            }

        } else {
            if (isNetwork == true) {
                mButtonStartLogin.setOnClickListener(handlerClickButton);
                mButtonStartRegister.setOnClickListener(handlerClickButton);
            } else {
                Utils.getInstance().showDiaglog(MainActivity.this, "Network Disabled", "Please turn on Wifi or 3G");

            }
            mButtonStartLogin.setOnClickListener(handlerClickButton);
            mButtonStartRegister.setOnClickListener(handlerClickButton);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener handlerClickButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btn_start_login) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }else if (id == R.id.btn_start_register) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        }
    };

    private boolean isGPSEnabled (Context mContext){
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
