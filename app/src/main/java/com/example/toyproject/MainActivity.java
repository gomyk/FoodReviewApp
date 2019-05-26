package com.example.toyproject;


import android.Manifest;
import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
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

import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toyproject.AccountManager.LoginActivity;
import com.example.toyproject.AccountManager.UserAccountDataHolder;
import com.example.toyproject.Database.ReveiwDatabase;
import com.example.toyproject.RecyclerView.CustomRecyclerView;
import com.example.toyproject.utils.CommonContextHolder;
import com.example.toyproject.utils.CommonContracts;
import com.example.toyproject.utils.ReviewDataManager;


import java.io.InputStream;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final String TAG = "MainActivity";
    private MapViewManager mMapViewManager;
    private CustomRecyclerView mCustomRecyclerView;
    private ViewGroup mRecyclerViewGroup;
    private ConstraintLayout mContentMain;
    private Boolean mIsLoggedIn = false;
    private TextView mLoginText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReviewDataManager.sDatabase = Room.databaseBuilder(this, ReveiwDatabase.class, "testDB")
                .allowMainThreadQueries()
                .build();
        CommonContextHolder.setContext(this);
        CommonContextHolder.setRecyclerViewVisible(false);
        sharedPreferences = this.getSharedPreferences("sfile", MODE_PRIVATE);
        CommonContextHolder.setLoginMethod(sharedPreferences.getString("LoginMethod", "NotLogin"));

        if (TextUtils.equals(CommonContextHolder.getLoginMethod(), "kakaotalk")) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, CommonContracts.LOGIN_ACTIVITY_REQUEST);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission Granted");
        } else {
            Log.d(TAG, "Permission Not Granted");
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        mContentMain = findViewById(R.id.content_main);
        mRecyclerViewGroup = findViewById(R.id.recyclerViewLayout);
        mCustomRecyclerView = new CustomRecyclerView((RecyclerView) findViewById(R.id.recyclerView));
        mCustomRecyclerView.Initialize();
        mMapViewManager = new MapViewManager((ViewGroup) findViewById(R.id.mapView));
        mMapViewManager.setCustomMapViewListener(new MapViewManager.MapViewCustomListener() {
            @Override
            public void onPoiTouched() {
                setRecyclerViewVisibility(true);
            }

            @Override
            public void onCalloutBallounTouched(double lon, double lat) {
                Intent intent = new Intent(getApplicationContext(), WriteReviewActivity.class);
                intent.putExtra("longitude", lon);
                intent.putExtra("lat", lat);
                startActivityForResult(intent, CommonContracts.REVIEW_WRITE_ACTIVITY);
            }

            @Override
            public void onFingerOnMap() {
                setRecyclerViewVisibility(false);
            }
        });
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
                setLoginCache("NotLogin");
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, CommonContracts.LOGIN_ACTIVITY_REQUEST);
            }
        });
        //Getting Hash Key (Now only debug version)

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String keyHash = new String(Base64.encode(md.digest(), 0));
//                Log.i("@@TEST", keyHash);
//            }
//        } catch (Exception e) {
//            Log.e("name not found", e.toString());
//        }

    }
    void setRecyclerViewVisibility(boolean visible){
        if(CommonContextHolder.getRecyclerViewVisible() == visible){
            return;
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mContentMain);
        if (!visible) {
            constraintSet.connect(R.id.recyclerViewLayout, ConstraintSet.TOP, R.id.content_main, ConstraintSet.BOTTOM, 0);
        } else {
            constraintSet.connect(R.id.recyclerViewLayout, ConstraintSet.TOP, R.id.recyler_guideline, ConstraintSet.BOTTOM, 0);
        }
        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator(new AccelerateInterpolator());
        transition.setDuration(500);
        TransitionManager.beginDelayedTransition(mContentMain, transition);
        constraintSet.applyTo(mContentMain);
        CommonContextHolder.setRecyclerViewVisible(!visible);
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
            case R.id.action_settings: {
                return true;
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
            case CommonContracts.LOGIN_ACTIVITY_REQUEST: {
                if (resultCode == CommonContracts.LOGIN_SUCCESS) {
                    mIsLoggedIn = true;
                    setLoginCache("kakaotalk");
                    setUserInfo();
                }
            }
        }
    }

    private void setLoginCache(String method) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("LoginMethod", method);
        editor.commit();
        CommonContextHolder.setLoginMethod(method);
    }

    private void setUserInfo() {
        Menu menu = findViewById(R.id.nav_menu);
        onPrepareOptionsMenu(menu);
        new DownloadImageTask((ImageView) findViewById(R.id.user_icon))
                .execute(UserAccountDataHolder.sThumbnailPath);
        ImageView user_icon = findViewById(R.id.user_icon);
        TextView user_email = findViewById(R.id.user_email);
        user_icon.setImageResource(R.drawable.kimdonghyun_face);
        user_email.setText(UserAccountDataHolder.sNickName);
        mLoginText.setText("Log Out");
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}