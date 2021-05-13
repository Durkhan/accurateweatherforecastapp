package com.myweatherforecastapp.accurate;



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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;



import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.myweatherforecastapp.accurate.Adapter.ViewPagerAdapter;
import com.myweatherforecastapp.accurate.Common.Common;
import com.myweatherforecastapp.accurate.Drawerlayout.About;
import com.myweatherforecastapp.accurate.Drawerlayout.Contactus;
import com.myweatherforecastapp.accurate.Drawerlayout.Help;
import com.myweatherforecastapp.accurate.Drawerlayout.Mylocation;
import com.myweatherforecastapp.accurate.Drawerlayout.RadarActivity;
import com.myweatherforecastapp.accurate.Drawerlayout.Settings;

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
    private static final String TAG="MainActivity";
    public static final String SHARED_PREFS="shared_prefs";
    Toolbar toolbar;
    public static final String TEXT="text";
    View view;
    public static final String button="button";

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
    private void buildLocationCallback() {
        locationCallback=new LocationCallback(){
            @Override
            public  void onLocationResult(LocationResult locationResult){
                super.onLocationResult(locationResult);
                if (locationResult.getLastLocation()!=null) {
                    Common.current_location = locationResult.getLastLocation();
                    viewPager = (ViewPager) findViewById(R.id.view_pager);
                    setupViewPager(viewPager);
                    tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(viewPager);

                    Log.d("Location", locationResult.getLastLocation().getLatitude() + "/" + locationResult.getLastLocation().getLongitude());
                }

            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                if (!locationAvailability.isLocationAvailable()){
                    LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
                    view=inflater.inflate(R.layout.mapdialog,null);
                    Button gotogooglemap=view.findViewById(R.id.dialog);
                    gotogooglemap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps"));
                            startActivity(intent);
                        }
                    }) ;
                    AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this)
                            .setView(view)
                            .create();
                    alertDialog.show();
                }
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
                    String share = "https://play.google.com/store/apps/details?id=com.myweatherforecastapp.accurate";
                    intent1.putExtra(Intent.EXTRA_TEXT, share);
                    startActivity(Intent.createChooser(intent1, "Share using"));

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.nav_radar:
                Intent radaractivity=new Intent(MainActivity.this, RadarActivity.class);
                startActivity(radaractivity);
                break;
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

}