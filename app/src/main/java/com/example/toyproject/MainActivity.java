package com.example.toyproject;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toyproject.RecyclerView.CustomRecyclerView;
import com.example.toyproject.utils.CommonContextHolder;
import com.example.toyproject.utils.CommonContracts;


import java.security.MessageDigest;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final String TAG = "MainActivity";
    private MapViewManager mMapViewManager;
    private CustomRecyclerView mCustomRecyclerView;
    private Boolean mIsLoggedIn = false;
    private TextView mLoginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CommonContextHolder.setContext(this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission Granted");
        } else {
            Log.d(TAG, "Permission Not Granted");
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        mMapViewManager = new MapViewManager((ViewGroup) findViewById(R.id.mapView));
        mCustomRecyclerView = new CustomRecyclerView((RecyclerView) findViewById(R.id.recyclerView));
        mCustomRecyclerView.Initialize();

        if (isPermissionGranted()) {
            mMapViewManager.addCurrentLocationMarker();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionGranted()) {
                    mMapViewManager.updateCurrentLocation();
                    mMapViewManager.addCurrentLocationMarker();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mLoginText = navigationView.getHeaderView(0).findViewById(R.id.login_text);
        mLoginText.setOnClickListener(view -> {
            if (mIsLoggedIn) {
                mIsLoggedIn = false;
                mLoginText.setText("Log In");
                ImageView user_icon = findViewById(R.id.user_icon);
                TextView user_email = findViewById(R.id.user_email);
                user_icon.setImageResource(R.drawable.googleg_standard_color_18);
                user_email.setText(R.string.nav_header_subtitle);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, CommonContracts.LOGIN_ACTIVITY_REQUSET);
            }
        });
        //Getting Hash Key (Now only debug version)

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyHash = new String(Base64.encode(md.digest(), 0));
                Log.i("@@TEST", keyHash);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        switch (id) {
            case R.id.nav_login: {
                item.setTitle("logout");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            CommonContextHolder.setContext(this);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CommonContracts.LOGIN_ACTIVITY_REQUSET: {
                if (resultCode == Activity.RESULT_OK) {
                    mIsLoggedIn = true;
                    Log.d("@@TEST", "found");
                    Menu menu = findViewById(R.id.nav_menu);
                    onPrepareOptionsMenu(menu);
                    ImageView user_icon = findViewById(R.id.user_icon);
                    TextView user_email = findViewById(R.id.user_email);
                    user_icon.setImageResource(R.drawable.kimdonghyun_face);
                    user_email.setText(data.getExtras().getCharSequence("id"));
                    mLoginText.setText("Log Out");
                } else {
                    //Todo : when it canceled
                }
            }
        }
    }
}
