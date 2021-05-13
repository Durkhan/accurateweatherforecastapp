package com.myweatherforecastapp.accurate.RadarCases;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.myweatherforecastapp.accurate.Common.Common;
import com.myweatherforecastapp.accurate.Drawerlayout.RadarActivity;
import com.myweatherforecastapp.accurate.MainActivity;
import com.myweatherforecastapp.accurate.R;

import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.annotations.NonNull;

public class WindspeedRadar extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final int REQUEST_CODE = 101;
    ImageButton backdrawer;
    Spinner spinner;
    int radar_cases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windspeed);
        Intent intent=getIntent();
        spinner=findViewById(R.id.radarcases);
        backdrawer=findViewById(R.id.backdrawer_);
        backdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(WindspeedRadar.this, MainActivity.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
            }
        });
        GradientDrawable gradientDrawable=new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{
                        ContextCompat.getColor(this,R.color.colorwindspeed1),
                        ContextCompat.getColor(this,R.color.colorwindspeed2),
                        ContextCompat.getColor(this,R.color.colorwindspeed3),
                        ContextCompat.getColor(this,R.color.colorwindspeed4),
                        ContextCompat.getColor(this,R.color.colorwindspeed5),
                        ContextCompat.getColor(this,R.color.colorwindspeed6),
                        ContextCompat.getColor(this,R.color.colorwindspeed7)

                }
        );
        findViewById(R.id.backgroundwindspeed).setBackground(gradientDrawable);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        spineradapter();
        fetchLastLocation();

    }

    private void spineradapter() {
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.radar_cases,R.layout.spinner_list);
        adapter.setDropDownViewResource(R.layout.spinner_list);
        spinner.setAdapter(adapter);
        spinner.setSelection(3);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                radar_cases=spinner.getSelectedItemPosition();
                if(radar_cases ==0){
                    Intent back = new Intent(WindspeedRadar.this, RadarActivity.class);
                    back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(back);

                }
                else if(radar_cases ==1){
                    Intent back = new Intent(WindspeedRadar.this, PressureRadar.class);
                    back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(back);


                }


                else if(radar_cases == 2){
                    Intent back = new Intent(WindspeedRadar.this,CloudsRadar.class);
                    back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(back);



                }
                else if(radar_cases == 4){
                    Intent back = new Intent(WindspeedRadar.this, PrecipitationRadar.class);
                    back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(back);


                }






            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    public void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_CODE);

            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Common.currentlocation=location;
                    MapFragment supportMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.radar_map);
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng = new LatLng(Common.currentlocation.getLatitude(),Common.currentlocation.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Your current location !");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
                            googleMap.addMarker(markerOptions);
                            googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(createTileProvider("wind_new")).visible(true));
                        }
                    });


                }
            }
        });
    }
    private TileProvider createTileProvider(final String tileType) {
        final  String OPWM_TILE_URL="https://tile.openweathermap.org/map/%s/%d/%d/%d.png?appid="+Common.api_key;
        UrlTileProvider urlTileProvider = new UrlTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                String fUrl = String.format(OPWM_TILE_URL, tileType, zoom, x, y);
                URL url = null;
                try {
                    url = new URL(fUrl);
                } catch (MalformedURLException e) {

                    e.printStackTrace();
                }
                return  url;
            }
        };
        return urlTileProvider;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION
                        }, REQUEST_CODE);

                        return;
                    }
                    Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
                    locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Common.currentlocation=location;
                                MapFragment supportMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.radar_map);
                                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(GoogleMap googleMap) {
                                        LatLng latLng = new LatLng(Common.currentlocation.getLatitude(),Common.currentlocation.getLongitude());
                                        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Your current location !");
                                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
                                        googleMap.addMarker(markerOptions);

                                    }
                                });


                            }
                        }
                    });
                }

                break;

        }
    }

}
