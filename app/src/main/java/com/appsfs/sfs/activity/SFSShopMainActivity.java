package com.appsfs.sfs.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.appsfs.sfs.Objects.MyMarker;
import com.appsfs.sfs.R;
import com.appsfs.sfs.Utils.SFSPreference;
import com.appsfs.sfs.Objects.Shipper;
import com.appsfs.sfs.Objects.Shop;
import com.appsfs.sfs.Utils.Utils;
import com.appsfs.sfs.api.function.GetCodeOrder;
import com.appsfs.sfs.api.function.GetShipperOnline;
import com.appsfs.sfs.api.function.LogoutUser;
import com.appsfs.sfs.api.helper.AccessHeader;
import com.appsfs.sfs.api.helper.CustomRespond;
import com.appsfs.sfs.api.helper.RequestHelper;
import com.appsfs.sfs.api.sync.ShipperListSync;
import com.appsfs.sfs.api.sync.ShipperSync;
import com.appsfs.sfs.api.sync.UserSync;
import com.appsfs.sfs.database.DatabaseHelperShipper;
import com.appsfs.sfs.database.DatabaseHelperShop;
import com.appsfs.sfs.database.DatabaseHelperUser;
import com.appsfs.sfs.service.GPSService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


/**
 * Created by longdv on 4/10/16.
 */
public class SFSShopMainActivity extends AppCompatActivity implements OnMapReadyCallback,Response.Listener<CustomRespond>,Response.ErrorListener {
    private DrawerLayout mDrawerLayout;
    private GoogleMap mMap;
    private TextView mHeaderName;
    SFSPreference mSfsPreference;
    private View mHeaderView;
    LatLng latLng = null;
    UserSync userSync;

    ShipperListSync shipperListSync;
    Marker marker;
    ArrayList<ShipperSync> mShipperSyncs;

    private ArrayList<MyMarker> mMyMarkersArray ;
    private HashMap<Marker, MyMarker> mMarkersHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);


        mMarkersHashMap = new HashMap<Marker, MyMarker>();
        mMyMarkersArray = new ArrayList<MyMarker>();

        mSfsPreference = SFSPreference.getInstance(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        mHeaderView = navigationView.getHeaderView(0);
        mHeaderName = (TextView) mHeaderView.findViewById(R.id.tv_header);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_edit_profile:
                        Intent i = new Intent(SFSShopMainActivity.this, EditProfileActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.navigation_item_edit_information:
                        Intent i1 = new Intent(SFSShopMainActivity.this, EditShopInfomationActivity.class);
                        startActivity(i1);
                        return true;
                    case R.id.navigation_item_order:
                        getNewCodeOrder();
                        return true;

                    case R.id.navigation_item_detail_order:

                        startDetailOrder();
//                        getNewCodeOrder();
                        return true;
                    case R.id.navigation_item_signout:
                        clickLogout();
                        return true;

                    default:
                        return true;
                }
            }
        });

        String json = mSfsPreference.getString("user_json","");
        try {
            userSync = new UserSync(new JSONObject(json));
            mHeaderName.setText(userSync.getPhone());
            latLng = new LatLng(userSync.getLatitude(),userSync.getLongitude());
            Log.d("","Locatoion " + latLng);
            new GetShipperOnline(SFSShopMainActivity.this,this,this).start();
        } catch (Exception e) {
            Log.d("sabdjkasdk",e.getLocalizedMessage());
        }

    }

    private void createOrder() {
        Intent i2 = new Intent(SFSShopMainActivity.this, CreateOrdersActivity.class);
        startActivity(i2);
    }

    private void startDetailOrder() {
        Intent i3 = new Intent(SFSShopMainActivity.this, DetailOrdersActivity.class);
        startActivity(i3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_shop));


        mShipperSyncs = shipperListSync.getListShipperSync();

        mMyMarkersArray = addMarkerArray(mShipperSyncs);
        plotMarkers(mMyMarkersArray);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(marker.getPosition())
                        .zoom(13)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),1000,null);
                marker.showInfoWindow();
                return true;
            }
        });



        CameraUpdate center =
                CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(13.0f);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 13);
        mMap.animateCamera(cameraUpdate);
    }


    private ArrayList<MyMarker> addMarkerArray(ArrayList<ShipperSync> shipperList) {
        ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
        for (int i = 0; i < shipperList.size(); i++) {
            MyMarker myMarker = new MyMarker();
            LatLng latLngShops = new LatLng(shipperList.get(i).getLatitude(), shipperList.get(i).getLongitude());
            String nameShops = shipperList.get(i).getName();
            String phoneShops = shipperList.get(i).getPhoneNumber();
            myMarker.setLabelName(nameShops);
            myMarker.setLabelPhone(phoneShops);
            myMarker.setLatitude(latLngShops.latitude);
            myMarker.setLongitude(latLngShops.longitude);
            mMyMarkersArray.add(myMarker);
        }

        return mMyMarkersArray;
    }

    private void plotMarkers(ArrayList<MyMarker> markers) {

        if (markers.size() > 0) {

            for (final MyMarker myMarker : markers) {
                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getLatitude(), myMarker.getLongitude()));
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_shipper));

                marker = mMap.addMarker(markerOption);
                mMarkersHashMap.put(marker, myMarker);
                CustomWindowInfo customWindowInfo = new CustomWindowInfo(SFSShopMainActivity.this);
                mMap.setInfoWindowAdapter(customWindowInfo);
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Utils.getInstance().callPhoneNumber(SFSShopMainActivity.this,myMarker.getLabelPhone());
                    }
                });

            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(CustomRespond response) {
        if (response.getFrom().equalsIgnoreCase(LogoutUser.SIGN_OUT_USER)) {
            AccessHeader.resetAccessHeader();
            mSfsPreference.putString("user_json", "");
            mSfsPreference.putInt("current_id_user", 0);
            Utils.getInstance().changeActivity(SFSShopMainActivity.this, MainActivity.class);
        } else if (response.getFrom().equalsIgnoreCase(GetCodeOrder.NEW_CODE_ORDER)) {
            try {
                mSfsPreference.putString("order_code", response.getData().getString("code"));
                createOrder();
            } catch (JSONException e) {
                e.getMessage();
            }
        } else {
            try {
                shipperListSync = new ShipperListSync(response.getData());
                Log.e("TAG","respone: " + response);

            } catch (Exception e) {
                Log.d("shipper null",e.getLocalizedMessage());

            }
        /*Google map*/
            if (Utils.getInstance().checkNetworkState(SFSShopMainActivity.this) == true) {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            }  else {
                Utils.getInstance().showDiaglog(this,"NO INTERNET!","Please check your device's connection settings");
            }

        }


//        Utils.getInstance().changeActivity(SFSShopMainActivity.this, LoginActivity.class);

    }


    private class CustomWindowInfo implements GoogleMap.InfoWindowAdapter {
        private LayoutInflater mInflater;
        MyMarker myMarker;

        public CustomWindowInfo(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        class ViewHolder {
            TextView mName;
            TextView mPhone;
            ImageView imageViewCall;
        }


        @Override
        public View getInfoWindow(final Marker marker) {

            View v = mInflater.inflate(R.layout.info_window_layout, null);
            ViewHolder viewHolder = new ViewHolder();

            myMarker = mMarkersHashMap.get(marker);

            viewHolder.mName = (TextView) v.findViewById(R.id.tv_window_name);
            viewHolder.mPhone = (TextView) v.findViewById(R.id.tv_window_phone);
            viewHolder.imageViewCall = (ImageView) v.findViewById(R.id.iv_window_call);
            viewHolder.mName.setText(myMarker.getLabelName());
            viewHolder.mPhone.setText(myMarker.getLabelPhone());
            return v;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

    }

    private  void clickLogout() {
        LogoutUser logoutUser = new LogoutUser(SFSShopMainActivity.this,this,this);
        logoutUser.start();
        stopService(new Intent(SFSShopMainActivity.this, GPSService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMap != null)
            mMap.clear();
    }

    private void getNewCodeOrder() {
        new GetCodeOrder(SFSShopMainActivity.this, this, this, userSync).start();
    }
}
