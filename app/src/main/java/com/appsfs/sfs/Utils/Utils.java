package com.appsfs.sfs.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.appsfs.sfs.activity.LoginActivity;
import com.google.android.gms.maps.model.LatLng;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by longdv on 4/3/16.
 */
public class Utils {
    public static Utils INSTANCE;
    public static String[] listLatLngTest = {"(20.991448, 105.855386)","(20.991082, 105.862580)","(20.995064, 105.857113)","(21.000633, 105.850580)","(21.005056, 105.847024)"};

    public Utils() {
        super();
    }
    public static Utils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Utils();
        }
        return INSTANCE;
    }

    /********************************************************
     * Check network
     ********************************************************/
    public boolean checkNetworkState(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    /********************************************************
     * Validation of Phone Number
     ********************************************************/
    public boolean isValidPhoneNumber(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            if (target.length() < 6 || target.length() > 13) {
                return false;
            } else {
                return android.util.Patterns.PHONE.matcher(target).matches();
            }
        }
    }

    /********************************************************
     * Show Dialog Enable Location
     ********************************************************/
    public void showEnableGPSDiaglog(final Context context, String title,String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(message);
        alertBuilder.setCancelable(true);

        alertBuilder.setNegativeButton("Enable", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent callGPSSettingIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(callGPSSettingIntent);
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }


    /********************************************************
     * Show Dialog Enable Location
     ********************************************************/
    public void showDiaglog(final Context context, String title,String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(message);
        alertBuilder.setCancelable(true);

        alertBuilder.setNegativeButton("OK",null);
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    /********************************************************
     * Show Toast
     ********************************************************/
    public void showToast(Context context,String message) {
       Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }


    /********************************************************
     * Detect phone number device's
     ********************************************************/
    public String getMyPhoneNumber(Context context){
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getLine1Number();
    }

    /********************************************************
     * Call phone number
     ********************************************************/
    public void callPhoneNumber(Context context,String phoneNumber){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(callIntent);
    }

    /********************************************************
     * Start Activity
     ********************************************************/
    public void changeActivity(Context context,Class<?> activity){
        Intent intent = new Intent(context,activity);
        context.startActivity(intent);
    }




}
