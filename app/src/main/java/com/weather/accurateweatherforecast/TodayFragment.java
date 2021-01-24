 package com.weather.accurateweatherforecast;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weather.accurateweatherforecast.Adapter.CityAdapter;
import com.weather.accurateweatherforecast.Adapter.WeatherForecastAdabter;
import com.weather.accurateweatherforecast.Common.Common;
import com.weather.accurateweatherforecast.Model.Daily.DailyResult;
import com.weather.accurateweatherforecast.Retrofit.OpenMapApi;
import com.weather.accurateweatherforecast.Retrofit.RetrofitClient;
import com.github.tianma8023.formatter.SunriseSunsetLabelFormatter;
import com.github.tianma8023.model.Time;
import com.github.tianma8023.ssv.SunriseSunsetView;


import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.weather.accurateweatherforecast.Common.Common.converttounixHour;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends Fragment {
    ImageView img_weather,img_wind,img_rain,img_humidity;
    TextView txt_city_name, txt_humidity,txt_wind, txt_description,txt_pressure, txt_date_time, txt_temperature;
    LinearLayout weather_panel;
    ProgressBar loading;
    Button details,dailydetails;
    CompositeDisposable compositeDisposable;
    OpenMapApi mService;
    RecyclerView recycle_forecastcurrent,recycle_forecast;
    SunriseSunsetView mSunriseSunsetView;
    static TodayFragment instance;
    RelativeLayout rela,relative;

    public static TodayFragment getInstance() {
        if (instance == null)
            instance = new TodayFragment();
        return instance;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TodayFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(OpenMapApi.class);

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayFragment newInstance(String param1, String param2) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_today, container, false);
        rela=(RelativeLayout)itemView.findViewById(R.id.relative_);
        details=(Button)itemView.findViewById(R.id.details_);
        dailydetails=(Button)itemView.findViewById(R.id.dailydetails_);
        dailydetails.setPaintFlags(dailydetails.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
        details.setPaintFlags(details.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        img_weather = (ImageView) itemView.findViewById(R.id.img_weather);
        txt_city_name = (TextView) itemView.findViewById(R.id.txt_city_name);
        txt_humidity = (TextView) itemView.findViewById(R.id.txt_humidity);
        txt_wind = (TextView) itemView.findViewById(R.id.txt_wind);
        txt_pressure = (TextView) itemView.findViewById(R.id.txt_pressure);
        txt_description=(TextView) itemView.findViewById(R.id.txt_description);
        txt_date_time = (TextView) itemView.findViewById(R.id.txt_date_time);
        txt_wind=(TextView) itemView.findViewById(R.id.txt_wind);
        img_wind=(ImageView) itemView.findViewById(R.id.img_wind);
        img_rain=(ImageView) itemView.findViewById(R.id.img_pressure);
        img_humidity=(ImageView) itemView.findViewById(R.id.img_humidity);
        txt_temperature = (TextView) itemView.findViewById(R.id.txt_temperature);
        mSunriseSunsetView = (SunriseSunsetView)itemView.findViewById(R.id.ssv);
        weather_panel = (LinearLayout) itemView.findViewById(R.id.weather_panel);
        loading = (ProgressBar) itemView.findViewById(R.id.loading);
        recycle_forecast=(RecyclerView)itemView.findViewById(R.id.recycle_forecast);
        recycle_forecast.setHasFixedSize(true);
        recycle_forecast.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recycle_forecastcurrent=(RecyclerView)itemView.findViewById(R.id.recycle_forecastcurrent);
        recycle_forecastcurrent.setHasFixedSize(true);
        recycle_forecastcurrent.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details=new Intent(getContext(),DailyDetailsActivity.class);
                startActivity(details);


            }
        });
        dailydetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dailydetails=new Intent(getActivity(),SevenDayDetailsActivity.class);
                startActivity(dailydetails);

            }
        });
        getWeatherInformation();

        return itemView;

    }



    public void getWeatherInformation() {
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
                       if(weatherResult.getWeather().get(0).getIcon().equals("01d")){
                            img_weather.setImageResource(R.drawable.dayclearsky);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("01n")){
                            img_weather.setImageResource(R.drawable.nightclearsky);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("02d")){
                            img_weather.setImageResource(R.drawable.dayfewclouds);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("02n")){
                            img_weather.setImageResource(R.drawable.dayfewclouds);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("03d")){
                            img_weather.setImageResource(R.drawable.scatteredclouds);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("03n")){
                            img_weather.setImageResource(R.drawable.scatteredclouds);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("04d")){
                            img_weather.setImageResource(R.drawable.brokenclouds);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("04n")){
                            img_weather.setImageResource(R.drawable.brokenclouds);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("09d")){
                            img_weather.setImageResource(R.drawable.showerrain);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("09n")){
                            img_weather.setImageResource(R.drawable.showerrain);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("10d")){
                            img_weather.setImageResource(R.drawable.dayrain);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("10n")){
                            img_weather.setImageResource(R.drawable.nightrain);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("11d")){
                            img_weather.setImageResource(R.drawable.thunderstrom);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("11n")){
                            img_weather.setImageResource(R.drawable.thunderstrom);
                        }
                       if(weatherResult.getWeather().get(0).getIcon().equals("50d")){
                            img_weather.setImageResource(R.drawable.mist);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("50n")){
                            img_weather.setImageResource(R.drawable.mist);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("13d")){
                            img_weather.setImageResource(R.drawable.snow);
                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("13n")){
                            img_weather.setImageResource(R.drawable.snow);
                        }

                        img_wind.animate();
                        img_rain.animate();
                        img_humidity.animate();
                 if(weatherResult.getWeather().get(0).getIcon().equals("10n") ||weatherResult.getWeather().get(0).getIcon().equals("10d")||
                                weatherResult.getWeather().get(0).getIcon().equals("09n") ||weatherResult.getWeather().get(0).getIcon().equals("09d")||
                                weatherResult.getWeather().get(0).getIcon().equals("11n") ||weatherResult.getWeather().get(0).getIcon().equals("11d")
                        ){

                            rela.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.rainybackground));
                           weather_panel.setBackgroundColor(Color.parseColor("#4D000000"));


                        }


                       if(weatherResult.getWeather().get(0).getIcon().equals("01n")||weatherResult.getWeather().get(0).getIcon().equals("01d")){

                            rela.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.clearskybackground));
                          weather_panel.setBackgroundColor(Color.parseColor("#33000000"));
                        }

                      if(weatherResult.getWeather().get(0).getIcon().equals("04n") ||weatherResult.getWeather().get(0).getIcon().equals("04d")||
                                weatherResult.getWeather().get(0).getIcon().equals("02n") ||
                                weatherResult.getWeather().get(0).getIcon().equals("03n") ||
                                weatherResult.getWeather().get(0).getIcon().equals("02d") ||
                                weatherResult.getWeather().get(0).getIcon().equals("03d")){
                            rela.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.cloudybackground));


                        }
                        if(weatherResult.getWeather().get(0).getIcon().equals("50n")|| weatherResult.getWeather().get(0).getIcon().equals("50d")){

                            rela.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.mistbackground));

                        }
                       if(weatherResult.getWeather().get(0).getIcon().equals("13n")|| weatherResult.getWeather().get(0).getIcon().equals("13d")){
                           rela.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.snowbackground));
                           weather_panel.setBackgroundColor(Color.parseColor("#40000000"));

                        }

                        int sunriseHour =Integer.parseInt(converttounixHour(weatherResult.getSys().getSunrise()).substring(0,2)),
                                sunriseMinute=Integer.parseInt(converttounixHour(weatherResult.getSys().getSunrise()).substring(3,5)),
                                sunsetHour=Integer.parseInt(converttounixHour(weatherResult.getSys().getSunset()).substring(0,2)),
                                sunsetMinute=Integer.parseInt(converttounixHour(weatherResult.getSys().getSunset()).substring(3,5));
                        mSunriseSunsetView.setSunriseTime(new Time(sunriseHour, sunriseMinute));
                        mSunriseSunsetView.setSunsetTime(new Time(sunsetHour, sunsetMinute));
                        mSunriseSunsetView.setLabelFormatter(new SunriseSunsetLabelFormatter() {
                            @Override
                            public String formatSunriseLabel(@NonNull Time sunrise) {
                                return formatLabel(sunrise);
                            }

                            @Override
                            public String formatSunsetLabel(@NonNull Time sunset) {
                                return formatLabel(sunset);
                            }

                            private String formatLabel(Time time) {
                                return String.format(Locale.getDefault(), "%02d:%02d", time.hour, time.minute);
                            }
                        });
// start animation
                        mSunriseSunsetView.startAnimate();

                        txt_city_name.setText(weatherResult.getName());
                        txt_humidity.setText(new StringBuilder(
                                String.valueOf(weatherResult.getMain().getHumidity())));
                        txt_pressure.setText(new StringBuilder(
                                String.valueOf(weatherResult.getMain().getPressure())));
                        txt_temperature.setText(new StringBuilder(
                                String.valueOf(weatherResult.getMain().getTemp())).append("°C").toString());
                        txt_wind.setText(new StringBuilder(
                                String.valueOf(weatherResult.getWind().getSpeed())));
                        txt_date_time.setText(Common.converttounix(weatherResult.getDt()));

                        txt_description.setText(weatherResult.getWeather().get(0).getDescription());
                        weather_panel.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        getWeatherDailyForecastInformation();
                        getWeatherForecastHourlyInformation();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }));
    }
    private void getWeatherDailyForecastInformation() {
        compositeDisposable.add(mService.getforecastweatherresultdailycity(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                "hourly,daily",
                Common.api_key,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DailyResult>() {
                    @Override
                    public void accept(DailyResult dailyResult) throws Exception {
                        CityAdapter adabtercitycurrent =new CityAdapter(getContext(),dailyResult);

                        recycle_forecastcurrent.setAdapter(adabtercitycurrent);


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Error",""+throwable.getMessage());
                    }
                }));

    }
    private void getWeatherForecastHourlyInformation() {
        compositeDisposable.add(mService.getforecastweatherresultbylatlon(
                String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.api_key,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                    @Override
                    public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
                        WeatherForecastAdabter adabter =new WeatherForecastAdabter(getContext(),weatherForecastResult);
                        recycle_forecast.setAdapter(adabter);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Error",""+throwable.getMessage());
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
}
