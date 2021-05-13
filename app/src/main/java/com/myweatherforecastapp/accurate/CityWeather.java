package com.myweatherforecastapp.accurate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myweatherforecastapp.accurate.Adapter.ViewPagerAdapter;
import com.myweatherforecastapp.accurate.Common.Common;
import com.myweatherforecastapp.accurate.Retrofit.OpenMapApi;
import com.myweatherforecastapp.accurate.Retrofit.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CityWeather extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageButton buttonback;
    Button weekly;
    CompositeDisposable compositeDisposable;
    OpenMapApi mService;
    String lon;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ProgressBar loading2;
    TextView cityname,tempuraturecity,feelslikecity,weatherdate,country,sunrise,sunset,humidity,visibiliy,pressure,wind,fahreit;
     LinearLayout linacity;
     RelativeLayout rela;
     String units;
    private static CityWeather instance;
    public static CityWeather getInstance() {



        if (instance == null)
            instance = new CityWeather();
        return instance;
    }

    public CityWeather() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(OpenMapApi.class);

        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Intent intent=getIntent();
        lon=intent.getStringExtra("city");
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = prefs.edit();
        editor.putString("key",lon);
        editor.commit();
        if(prefs.getString("key4","").equals("imperial")){
            units=("imperial");
        }
        else{
            units=("metric");
        }
        cityname=(TextView)findViewById(R.id.txt_city_name_);
        feelslikecity=(TextView)findViewById(R.id.feelslike);
        weatherdate=(TextView)findViewById(R.id.weatherdate);
        tempuraturecity=(TextView)findViewById(R.id.citytempurature);
        country=(TextView)findViewById(R.id.txt_country_);
        sunrise=(TextView)findViewById(R.id.sunrise);
        sunset=(TextView)findViewById(R.id.sunset);
        fahreit=(TextView)findViewById(R.id.txtcelfah);
        wind=(TextView)findViewById(R.id.wind);
        visibiliy=(TextView)findViewById(R.id.visibility);
        humidity=(TextView)findViewById(R.id.humidity);
        buttonback=(ImageButton)findViewById(R.id.locationcityback_);
        pressure=(TextView)findViewById(R.id.pressure);
        linacity=findViewById(R.id.linacity);
        rela=findViewById(R.id.rel__);
        weekly=(Button) findViewById(R.id.weekly);
        loading2= (ProgressBar)findViewById(R.id.loading2);
        getWeatherInformation(lon);
        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cityactivity=new Intent(CityWeather.this,CitySevendayactivities.class);
                cityactivity.putExtra("city",lon);
                startActivity(cityactivity);
            }
        });
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backintent=new Intent(CityWeather.this,MainActivity.class);
                backintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK  | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(backintent);
            }
        });


    }


 private void getWeatherInformation( String lon) {
        compositeDisposable.add(mService.getweatherresultbycity(lon,
                Common.api_key,
                units)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>(){
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {
                       if(prefs.getString("key4","").equals("imperial")){
                           fahreit.setText("°F");
                       }
                       cityname.setText(new StringBuilder(String.valueOf(weatherResult.getName())).append(", "));
                       tempuraturecity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())));
                        pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append(" hPa"));
                        humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append("%"));
                        visibiliy.setText(new StringBuilder(String.valueOf(weatherResult.getVisibility())).append(" m"));
                        wind.setText(new StringBuilder(String.valueOf(weatherResult.getWind().getSpeed())).append(" m/s"));
                        sunrise.setText(new StringBuilder(Common.converttounixHour(weatherResult.getSys().getSunrise())));
                        sunset.setText(new StringBuilder(Common.converttounixHour(weatherResult.getSys().getSunset())));
                        feelslikecity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getFeelsLike())).append("°"));
                        weatherdate.setText(new StringBuilder(Common.converttoweatherunix(weatherResult.getDt())));
                        country.setText(weatherResult.getSys().getCountry());
                        linacity.setVisibility(View.VISIBLE);
                        loading2.setVisibility(View.GONE);
                       if(weatherResult.getWeather().get(0).getIcon().equals("10n") ||weatherResult.getWeather().get(0).getIcon().equals("10d")||
                                weatherResult.getWeather().get(0).getIcon().equals("09n") ||weatherResult.getWeather().get(0).getIcon().equals("09d")||
                                weatherResult.getWeather().get(0).getIcon().equals("11n") ||weatherResult.getWeather().get(0).getIcon().equals("11d")
                        ){

                            rela.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.rainybackground));
                            linacity.setBackgroundColor(Color.parseColor("#59000000"));


                        }
                            if(weatherResult.getWeather().get(0).getIcon().equals("01d")){

                           rela.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.clearskybackground));
                          linacity.setBackgroundColor(Color.parseColor("#33000000"));
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("01n")){

                           rela.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.nightclearskybackground));
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("04n") ||weatherResult.getWeather().get(0).getIcon().equals("04d")||
                                weatherResult.getWeather().get(0).getIcon().equals("02n") ||
                                weatherResult.getWeather().get(0).getIcon().equals("03n") ||
                                weatherResult.getWeather().get(0).getIcon().equals("02d") ||
                                weatherResult.getWeather().get(0).getIcon().equals("03d")){
                            rela.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.cloudybackground));
                            linacity.setBackgroundColor(Color.parseColor("#1F000000"));


                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("50n")|| weatherResult.getWeather().get(0).getIcon().equals("50d")){

                            rela.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.mistbackground));
                            linacity.setBackgroundColor(Color.parseColor("#1A000000"));
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("13n")|| weatherResult.getWeather().get(0).getIcon().equals("13d")){

                            rela.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.snowbackground));
                            linacity.setBackgroundColor(Color.parseColor("#40000000"));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getParent(), ""+throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }));
    }
@Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(Today.getInstance(),"Today");
        adapter.addFragment(Tomorrow.getInstance(),"Tomorrow");
        viewPager.setAdapter(adapter);



    }

}