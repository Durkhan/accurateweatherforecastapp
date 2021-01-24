package com.weather.accurateweatherforecast;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.weather.accurateweatherforecast.Adapter.TodayHourlyAdapter;
import com.weather.accurateweatherforecast.Common.Common;
import com.weather.accurateweatherforecast.Model.Daily.DailyResult;
import com.weather.accurateweatherforecast.Retrofit.OpenMapApi;
import com.weather.accurateweatherforecast.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Today#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Today extends Fragment {
    static Today instance;
    CompositeDisposable compositeDisposable;
    OpenMapApi mService;
    RecyclerView recycle_todayhourly;
    String lat,longu;
    PreferenceManager preferenceManager;
    TextView hourlytemperaturecity;
    ImageView img_weathercity;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Today.
     */
    // TODO: Rename and change types and number of parameters
    public static Today newInstance(String param1, String param2) {
        Today fragment = new Today();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Today getInstance() {

        if (instance == null)
            instance = new Today();
        return instance;
    }
    public Today() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(OpenMapApi.class);
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View itemview=inflater.inflate(R.layout.fragment_today2, container, false);
        recycle_todayhourly=(RecyclerView)itemview.findViewById(R.id.hourly_today);
        hourlytemperaturecity=itemview.findViewById(R.id.hourlytemperaturecity);
        img_weathercity=itemview.findViewById(R.id.img_weathercity);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();
        String lon=prefs.getString("key","");
       recycle_todayhourly.setHasFixedSize(true);
     recycle_todayhourly.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


        getWeatherInformation(lon);

        return itemview;
    }

   private void getWeatherInformation( String lon) {
        compositeDisposable.add(mService.getweatherresultbycity(lon,
                Common.api_key,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {
                     lat=String.valueOf(weatherResult.getCoord().getLat());
                     longu=String.valueOf(weatherResult.getCoord().getLon());
                      getweatherHourlyforecast(lat,longu);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }));
    }
    private void getweatherHourlyforecast(String lat,String longu) {
        compositeDisposable.add(mService.getforecastweatherresultdailycity(lat,
                longu,
                "hourly,daily",
                Common.api_key,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DailyResult>() {
                    @Override
                    public void accept(DailyResult dailyResult) throws Exception {
                        TodayHourlyAdapter todayHourlyAdapter =new TodayHourlyAdapter(getContext(),dailyResult);
                        recycle_todayhourly.setAdapter(todayHourlyAdapter);
                        hourlytemperaturecity.setText(new StringBuilder(String.valueOf(dailyResult.hourly.get(0).getTemp())).append("°"));
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("01d")){
                            img_weathercity.setImageResource(R.drawable.dayclearsky);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("01n")){
                            img_weathercity.setImageResource(R.drawable.nightclearsky);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("02n")){
                            img_weathercity.setImageResource(R.drawable.nightfewclouds);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("02d")){
                            img_weathercity.setImageResource(R.drawable.dayfewclouds);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("03n")){
                            img_weathercity.setImageResource(R.drawable.scatteredclouds);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("03d")){
                           img_weathercity.setImageResource(R.drawable.scatteredclouds);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("04n")){
                            img_weathercity.setImageResource(R.drawable.brokenclouds);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("04d")){
                          img_weathercity.setImageResource(R.drawable.brokenclouds);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("09n")){
                          img_weathercity.setImageResource(R.drawable.showerrain);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("09d")){
                            img_weathercity.setImageResource(R.drawable.showerrain);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("10d")){
                            img_weathercity.setImageResource(R.drawable.dayrain);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("10n")){
                            img_weathercity.setImageResource(R.drawable.nightrain);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("50d")){
                            img_weathercity.setImageResource(R.drawable.mist);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("50n")){
                            img_weathercity.setImageResource(R.drawable.mist);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("13d")){
                           img_weathercity.setImageResource(R.drawable.snow);
                        }
                        if(dailyResult.hourly.get(0).weather.get(0).getIcon().equals("13n")){
                            img_weathercity.setImageResource(R.drawable.snow);
                        }




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