package com.appsfs.sfs.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by longdv on 4/28/16.
 */
public class GeolocationUtils {
    public static GeolocationUtils INSTANCE;
    public static String[] listLatLngTest = {"(20.991448, 105.855386)","(20.991082, 105.862580)","(20.995064, 105.857113)","(21.000633, 105.850580)","(21.005056, 105.847024)"};


    private Circle mCircle;
    private Marker mMarker;
    private GoogleMap mGoogleMap;

    public GeolocationUtils() {
        super();
    }
    public static GeolocationUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GeolocationUtils();
        }
        return INSTANCE;
    }
    /******************************************************
     * 	Create circle market uses shipper
     ******************************************************/
    public CircleOptions getCircleOptions(LatLng latlng) {
        double radiusInMeters = 1000.0;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

        CircleOptions co = new CircleOptions();
        co.center(latlng);
        co.radius(radiusInMeters);
        co.fillColor(shadeColor);
        co.strokeColor(strokeColor);
        co.strokeWidth(8.0f);
        return co;
    }

    /******************************************************
     * 	Get LatLng from Address
     * 	Input: Address
     * 	Output: LatLng
     ******************************************************/

    public LatLng getLatLongFromAddress(Context context,String strAddress) {
        LatLng latLng = null;
        Geocoder geocoder = new Geocoder(context);
        List<Address> address;
        try {
            address = geocoder.getFromLocationName(strAddress,5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            latLng = new LatLng(location.getLatitude(),location.getLongitude());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return latLng;
    }

    /******************************************************
     * 	Radius shows object
     * 	Input: Address
     * 	Output: LatLng
     ******************************************************/
    public double CalculationByDistance(LatLng latLngOrigin,
                                        LatLng latLngDestination) {
        double earthRadius = 3958.75;
        double lat_a = latLngOrigin.latitude;
        double lng_a = latLngOrigin.longitude;
        double lat_b = latLngDestination.latitude;
        double lng_b = latLngDestination.longitude;

        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue();

    }
}
