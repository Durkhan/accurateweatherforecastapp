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
import android.widget.Toast;

import com.weather.accurateweatherforecast.Adapter.TomorrowHourlyAdabter;
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
 * Use the {@link Tomorrow#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tomorrow extends Fragment {
    static Tomorrow instance;
    CompositeDisposable compositeDisposable;
    OpenMapApi mService;
    RecyclerView recycle_tomorrowhourly;
    String lat,longu;
    PreferenceManager preferenceManager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Tomorrow() {
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
     * @return A new instance of fragment Tomorrow.
     */
    // TODO: Rename and change types and number of parameters
    public static Tomorrow newInstance(String param1, String param2) {
        Tomorrow fragment = new Tomorrow();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Tomorrow getInstance() {
        if (instance == null)
            instance = new Tomorrow();
        return instance;
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
        View itemview=inflater.inflate(R.layout.fragment_tomorrow, container, false);
        recycle_tomorrowhourly=(RecyclerView)itemview.findViewById(R.id.hourly_tomorrow);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();
        String lon=prefs.getString("key","");
       recycle_tomorrowhourly.setHasFixedSize(true);
        recycle_tomorrowhourly.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        getWeatherInformation(lon);
        return  itemview;
    }

    private void getWeatherInformation(String lon) {
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
                       TomorrowHourlyAdabter tomorrowHourlyAdabter =new TomorrowHourlyAdabter(getContext(),dailyResult);
                        recycle_tomorrowhourly.setAdapter(tomorrowHourlyAdabter);


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
