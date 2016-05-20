package com.appsfs.sfs.service;

import android.Manifest;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by longdv on 4/23/16.
 */
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.appsfs.sfs.Utils.SFSPreference;
import com.appsfs.sfs.Utils.Utils;
import com.appsfs.sfs.api.function.UpdateUser;
import com.appsfs.sfs.api.sync.UserSync;
import com.appsfs.sfs.database.DatabaseHelperShipper;

import java.util.List;

public class GPSService extends Service implements Response.Listener, Response.ErrorListener{
    private static final String TAG = "GPSTracker";
    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location mLastLocation; // location

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(Object response) {

    }

    double latitude; // latitude
    double longitude; // longitude


    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;

    Location location;
    SFSPreference mPreference;


    private class LocationListener implements android.location.LocationListener {
        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");

        initializeLocationManager();
        mPreference = SFSPreference.getInstance(GPSService.this);
        // getting GPS status
        updateLocation();


    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }

        }

        stopSelf();
    }


    private Location getLastKnownLocation() {
        initializeLocationManager();
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            try {
                Location l = mLocationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }

            } catch (Exception e) {

            }

        }
        return bestLocation;
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private void updateLocation() {

        isGPSEnabled = mLocationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = mLocationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
            Log.d("", "No network");
        } else {
            int id = mPreference.getInt("current_id_user", 0);
            Log.e(TAG, "current_id_user : " + id);
            if (isNetworkEnabled) {
                Log.e(TAG, "isNetworkEnabled");
                try {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                            mLocationListeners[1]);
                    if (mLocationManager != null) {
                        location = getLastKnownLocation();
//                        location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            double lat = location.getLatitude();
                            double lng = location.getLongitude();

                            Log.e(TAG, "Lat : " + lat + " Lng : " + lng);

                            UpdateUser updateUser = new UpdateUser(GPSService.this,id,lat,lng,this,this);
                            updateUser.start();
                           /* boolean isLocation = databaseHelperShipper.updateLocation(phone, lat, lng);
                            if (isLocation) {
//                                Utils.getInstance().showToast(GPSService.this, "update location success");
                            } else {
                                Utils.getInstance().showToast(GPSService.this, "update location fail");
                            }*/
                        } else {
                            Log.e(TAG, "Location Network null");
                        }
                    }

                } catch (java.lang.SecurityException ex) {
                    Log.i(TAG, "fail to request location update, ignore", ex);
                } catch (IllegalArgumentException ex) {
                    Log.d(TAG, "network provider does not exist, " + ex.getMessage());
                }
            }
            if (isGPSEnabled) {
                try {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                            mLocationListeners[0]);

                    if (mLocationManager != null) {
                        Log.e(TAG, "mLocationManager not null");
                        location = getLastKnownLocation();
//                        location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            Log.e(TAG, "location not null");
                            double lat = location.getLatitude();
                            double lng = location.getLongitude();
                            String phone = mPreference.getString("user_phone", "");
                            Log.d("Phone : ", "user_phone : " + phone);
                            Log.d("Lat : " + lat, "Lng : " + lng);
                            UpdateUser updateUser = new UpdateUser(GPSService.this,id,lat,lng,this,this);
                            updateUser.start();
                        } else {
                            Log.e(TAG, "Location GPS null");
                        }
                    }
                } catch (java.lang.SecurityException ex) {
                    Log.i(TAG, "fail to request location update, ignore", ex);
                } catch (IllegalArgumentException ex) {
                    Log.d(TAG, "gps provider does not exist " + ex.getMessage());
                }

            }
        }

    }

}