package com.example.toyproject;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.toyproject.utils.CommonContextHolder;

import net.daum.android.map.MapViewEventListener;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;


public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission granted");
        } else {
            Log.d(TAG, "Permission Not granted");
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        CommonContextHolder.setContext(this);
        mMapView = new MapView(this);
        ViewGroup mapViewContainer = findViewById(R.id.mapView);
        mapViewContainer.addView(mMapView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission granted");
                    LocationInfoProvider locationInfoProvider = new LocationInfoProvider(getApplicationContext());
                    mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(locationInfoProvider.getLatitude(), locationInfoProvider.getLongitude()), 1, true);
                    MapPOIItem marker = new MapPOIItem();
                    marker.setItemName("Default Marker");
                    marker.setTag(0);
                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(locationInfoProvider.getLatitude(), locationInfoProvider.getLongitude()));
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

                    mMapView.addPOIItem(marker);
                } else {
                    Log.d(TAG, "Permission Denied");
                }
            }
        });
        mMapView.setMapViewEventListener(new MapViewEventListener() {
            @Override
            public void onLoadMapView() {
                Log.d(TAG, "onLoadMapView");

            }
        });

        mMapView.setCurrentLocationEventListener(new MapView.CurrentLocationEventListener() {
            @Override
            public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
                Log.d(TAG, "onCurrentLocationupdated");
            }

            @Override
            public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
                Log.d(TAG, "DeviceHeading UPdate");
            }

            @Override
            public void onCurrentLocationUpdateFailed(MapView mapView) {
                Log.d(TAG, "onCurrentLocationupdateFailed");
            }

            @Override
            public void onCurrentLocationUpdateCancelled(MapView mapView) {
                Log.d(TAG, "oncurrentLocationupdateCancelled");
            }
        });


        //Getting Hash Key (Now only debug version)
        /*
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d("@@TEST", keyHash);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }
        */
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            CommonContextHolder.setContext(this);
        }
    }
}
