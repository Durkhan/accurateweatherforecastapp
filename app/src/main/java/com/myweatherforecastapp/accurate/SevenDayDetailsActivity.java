 package com.myweatherforecastapp.accurate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myweatherforecastapp.accurate.Adapter.WeeklyAdabter;
import com.myweatherforecastapp.accurate.Common.Common;
import com.myweatherforecastapp.accurate.Model.Daily.DailyResult;
import com.myweatherforecastapp.accurate.Retrofit.OpenMapApi;
import com.myweatherforecastapp.accurate.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SevenDayDetailsActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable;
    OpenMapApi mService;
    ImageButton backbutton;
    ProgressBar loading3;
    LinearLayout city;
    String units;
    ImageView detailstomorrow;
    TextView dateTomorrow,tomorrowdaytemp,tommorownighttemp,dayscityname,tomorrowdaysunset,dailyhumidity,dailypresssure,tomorrowdaysunrise;
    RecyclerView recycleweekdaily;
    private static SevenDayDetailsActivity instance;
    public static SevenDayDetailsActivity getInstance() {


        if (instance == null)
            instance = new SevenDayDetailsActivity();
        return instance;
    }

    public SevenDayDetailsActivity() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(OpenMapApi.class);

        // Required empty public constructor
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_day_details);
        Intent intent=getIntent();
        detailstomorrow=(ImageView) findViewById(R.id.dailyimage_);
        backbutton=(ImageButton)findViewById(R.id.back__);
        dateTomorrow=(TextView)findViewById(R.id.detsilsdate_);
        tommorownighttemp=(TextView)findViewById(R.id.nighttemperature);
        tomorrowdaytemp=(TextView)findViewById(R.id.daytemperature);
        dayscityname=(TextView) findViewById(R.id.txt_city_name__);
        tomorrowdaysunset=(TextView) findViewById(R.id.sunset_);
        tomorrowdaysunrise=(TextView) findViewById(R.id.sunrise_);
        dailypresssure=(TextView) findViewById(R.id.dailypressure);
        dailyhumidity=(TextView) findViewById(R.id.dailyhumidity);
        recycleweekdaily=(RecyclerView)findViewById(R.id.weekdaily_);
        loading3=findViewById(R.id.loading3);
        city=findViewById(R.id.citydetails);
        recycleweekdaily.setHasFixedSize(true);
        recycleweekdaily.setLayoutManager(new LinearLayoutManager( getInstance(),LinearLayoutManager.VERTICAL, false));
        onRestart();

        getWeatherDailyForecastInformation();
        getdetailsweather();

    }
    @Override
    protected void onRestart(){
        super.onRestart();
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backintent=new Intent(getApplicationContext(),MainActivity.class);
                backintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK  | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(backintent);


            }
        });


    }

    public void getWeatherDailyForecastInformation() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor= prefs.edit();
        if(prefs.getString("key4","").equals("imperial")){
            units=("imperial");
        }
        else{
            units=("metric");
        }
        compositeDisposable.add(mService.getforecastweatherresultdailycity(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                "hourly,daily",
                Common.api_key,
                units)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DailyResult>() {
                    @Override
                    public void accept(DailyResult dailyResult) throws Exception {

                     dateTomorrow.setText(new StringBuilder(Common.converttounixDayDaily(dailyResult.daily.get(1).getDt()).substring(0,1).toUpperCase()+Common.converttounixDayDaily(dailyResult.daily.get(1).getDt()).substring(1).toLowerCase()));
                     tommorownighttemp.setText(new StringBuilder(String.valueOf(dailyResult.daily.get(1).getTemp().getEve())).append("°"));
                     tomorrowdaytemp.setText(new StringBuilder(String.valueOf(dailyResult.daily.get(1).getTemp().getMorn())).append("°"));
                     tomorrowdaysunrise.setText(new StringBuilder(Common.converttounixHour(dailyResult.daily.get(1).getSunrise())));
                     dailypresssure.setText(new StringBuilder(String.valueOf(dailyResult.daily.get(1).getPressure())).append("hPa"));
                       dailyhumidity.setText(new StringBuilder(String.valueOf(dailyResult.daily.get(1).humidity)).append("%"));
                        tomorrowdaysunset.setText(new StringBuilder(Common.converttounixHour(dailyResult.daily.get(1).getSunset())));
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("01d")){
                            detailstomorrow.setImageResource(R.drawable.dayclearsky);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("01n")){
                            detailstomorrow.setImageResource(R.drawable.nightclearsky);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("02n")){
                           detailstomorrow.setImageResource(R.drawable.nightfewclouds);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("02d")){
                          detailstomorrow.setImageResource(R.drawable.dayfewclouds);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("03n")){
                            detailstomorrow.setImageResource(R.drawable.scatteredclouds);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("03d")){
                            detailstomorrow.setImageResource(R.drawable.scatteredclouds);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("04n")){
                            detailstomorrow.setImageResource(R.drawable.brokenclouds);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("04d")){
                            detailstomorrow.setImageResource(R.drawable.brokenclouds);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("09n")){
                            detailstomorrow.setImageResource(R.drawable.showerrain);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("09d")){
                            detailstomorrow.setImageResource(R.drawable.showerrain);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("10d")){
                            detailstomorrow.setImageResource(R.drawable.dayrain);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("10n")){
                            detailstomorrow.setImageResource(R.drawable.nightrain);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("50d")){
                            detailstomorrow.setImageResource(R.drawable.mist);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("50n")){
                            detailstomorrow.setImageResource(R.drawable.mist);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("13d")){
                            detailstomorrow.setImageResource(R.drawable.snow);
                        }
                        if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("13n")){
                            detailstomorrow.setImageResource(R.drawable.snow);
                        }
                        WeeklyAdabter adabterdailyweek =new WeeklyAdabter(getBaseContext(),dailyResult);
                        recycleweekdaily.setAdapter(adabterdailyweek);
                        city.setVisibility(View.VISIBLE);
                        loading3.setVisibility(View.GONE);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Error",""+throwable.getMessage());
                    }
                }));

    }

    private void getdetailsweather() {
        compositeDisposable.add(mService.getforecastweatherresultbylatlon(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.api_key,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {

                    @Override
                    public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
                        dayscityname.setText(weatherForecastResult.getCity().name);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Error",""+throwable.getMessage());
                    }
                }));

    }
}