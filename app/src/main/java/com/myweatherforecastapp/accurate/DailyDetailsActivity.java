package com.myweatherforecastapp.accurate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myweatherforecastapp.accurate.Adapter.ThreeHourlyDetails;
import com.myweatherforecastapp.accurate.Common.Common;
import com.myweatherforecastapp.accurate.Retrofit.OpenMapApi;
import com.myweatherforecastapp.accurate.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DailyDetailsActivity extends AppCompatActivity {
    private static DailyDetailsActivity instance;
    RecyclerView recycle_forecastdetails;
    ImageButton backbutton;
    WeatherForecastResult weatherForecastResult;
    RelativeLayout detailsrela;
    CompositeDisposable compositeDisposable;
    OpenMapApi mService;
    LinearLayout weather_panel;
    String units;
    TextView details_cityname;
    public static DailyDetailsActivity getInstance() {

        if (instance == null)
            instance = new DailyDetailsActivity();
        return instance;
    }

    public DailyDetailsActivity() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(OpenMapApi.class);

        // Required empty public constructor
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_details_);
        recycle_forecastdetails = (RecyclerView) findViewById(R.id.hourly);
        detailsrela=(RelativeLayout)findViewById(R.id.detailsrela_);
        weather_panel=(LinearLayout)findViewById(R.id.linadetails);
        backbutton=(ImageButton)findViewById(R.id.back_);
        recycle_forecastdetails.setHasFixedSize(true);
        details_cityname=(TextView)findViewById(R.id.txt_city_name_);
        recycle_forecastdetails.setLayoutManager(new LinearLayoutManager( getInstance(),LinearLayoutManager.VERTICAL, false));
        getdetailsweather();
        getWeatherInformation();

        Intent intent = getIntent();
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backintent=new Intent(DailyDetailsActivity.this,MainActivity.class);
                backintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK  | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(backintent);


            }
        });

    }


    private void getdetailsweather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor= prefs.edit();
        if(prefs.getString("key4","").equals("imperial")){
            units=("imperial");
        }
        else{
            units=("metric");
        }
        compositeDisposable.add(mService.getforecastweatherresultbylatlon(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.api_key,
                units)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {

                    @Override
                    public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
                        details_cityname.setText(weatherForecastResult.getCity().name);
                        ThreeHourlyDetails adabter=new ThreeHourlyDetails(getBaseContext(),weatherForecastResult);
                        recycle_forecastdetails.setAdapter(adabter);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Error",""+throwable.getMessage());
                    }
                }));

    }
    private void getWeatherInformation() {
        compositeDisposable.add(mService.getweatherresultbylatlon(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.api_key,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {

                        if(weatherResult.getWeather().get(0).getIcon().equals("10n") ||weatherResult.getWeather().get(0).getIcon().equals("10d")||
                                weatherResult.getWeather().get(0).getIcon().equals("09n") ||weatherResult.getWeather().get(0).getIcon().equals("09d")||
                                weatherResult.getWeather().get(0).getIcon().equals("11n") ||weatherResult.getWeather().get(0).getIcon().equals("11d")
                        ){

                            detailsrela.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.rainybackground));
                            weather_panel.setBackgroundColor(Color.parseColor("#4D000000"));


                        }


                        if(weatherResult.getWeather().get(0).getIcon().equals("01n")||weatherResult.getWeather().get(0).getIcon().equals("01d")){

                            detailsrela.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.clearskybackground));
                            weather_panel.setBackgroundColor(Color.parseColor("#33000000"));
                        }

                        if(weatherResult.getWeather().get(0).getIcon().equals("04n") ||weatherResult.getWeather().get(0).getIcon().equals("04d")||
                                weatherResult.getWeather().get(0).getIcon().equals("02n") ||
                                weatherResult.getWeather().get(0).getIcon().equals("03n") ||
                                weatherResult.getWeather().get(0).getIcon().equals("02d") ||
                                weatherResult.getWeather().get(0).getIcon().equals("03d")){
                            detailsrela.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.cloudybackground));


                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("50n")|| weatherResult.getWeather().get(0).getIcon().equals("50d")){

                            detailsrela.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.mistbackground));

                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("13n")|| weatherResult.getWeather().get(0).getIcon().equals("13d")){
                            detailsrela.setBackground(ContextCompat.getDrawable(getBaseContext(),R.drawable.snowbackground));
                            weather_panel.setBackgroundColor(Color.parseColor("#40000000"));

                        }




                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getParent(), ""+throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }));
    }


}