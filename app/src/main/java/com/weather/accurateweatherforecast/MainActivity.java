package com.weather.accurateweatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.weather.accurateweatherforecast.Adapter.ViewPagerAdapter;
import com.weather.accurateweatherforecast.Common.Common;
import com.weather.accurateweatherforecast.Drawerlayout.About;
import com.weather.accurateweatherforecast.Drawerlayout.Contactus;
import com.weather.accurateweatherforecast.Drawerlayout.Help;
import com.weather.accurateweatherforecast.Drawerlayout.Mylocation;
import com.weather.accurateweatherforecast.Drawerlayout.Settings;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int REQUEST_CODE = 101;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout coordinatorLayout;
    private  DrawerLayout drawerLayout;
    FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private  NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        coordinatorLayout = findViewById(R.id.root_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open_drawer, R.string.navigation_close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Intent intent = getIntent();
        navigationView.setNavigationItemSelectedListener(this);


        drawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.openDrawer(GravityCompat.START);
                else drawerLayout.closeDrawer(GravityCompat.END);
            }
        });


        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {


                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            buildLocationRequest();
                            buildLocationCallback();
                            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                    ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                }, REQUEST_CODE);
                                return;
                            }

                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());



                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.cancelPermissionRequest();
                    }

                }).check();
    }






    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        else
            super.onBackPressed();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_location:
                Intent intent=new Intent(getBaseContext(), Mylocation.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
            {
                Intent intent2=new Intent(getBaseContext(), Settings.class);
                startActivity(intent2);
                break;
            }




            case R.id.nav_share: {

                try {
                    Intent intent1 = new Intent(Intent.ACTION_SEND);
                    intent1.setType("text/plain");
                    intent1.putExtra(Intent.EXTRA_SUBJECT, "share app");
                    String share = "https://play.google.com/store/apps/details?=" + BuildConfig.APPLICATION_ID + "\n\n";
                    intent1.putExtra(Intent.EXTRA_TEXT, share);
                    startActivity(Intent.createChooser(intent1, "Share using"));

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.nav_about:
                Intent aboutactivity=new Intent(MainActivity.this, About.class);
                startActivity(aboutactivity);
                break;
            case R.id.nav_help:
                Intent helpactivity=new Intent(MainActivity.this, Help.class);
                startActivity(helpactivity);
                break;
            case R.id.nav_contact:
            {
                Intent intent1=new Intent(MainActivity.this, Contactus.class);
                startActivity(intent1);
            }
        }
        return true;
    }
    private void buildLocationCallback() {
        locationCallback=new LocationCallback(){
            @Override
            public  void onLocationResult(LocationResult locationResult){
                super.onLocationResult(locationResult);
                Common.current_location=locationResult.getLastLocation();
                viewPager=(ViewPager)findViewById(R.id.view_pager);
                setupViewPager(viewPager);
                tabLayout=(TabLayout)findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);


                Log.d("Location",locationResult.getLastLocation().getLatitude()+"/"+locationResult.getLastLocation().getLongitude());
            }

        };

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(TodayFragment.getInstance()," MyLocation");
        adapter.addFragment(CityFragment.getInstance(),"Locations");
        viewPager.setAdapter(adapter);


    }

    private void buildLocationRequest() {
        locationRequest=new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }


}   