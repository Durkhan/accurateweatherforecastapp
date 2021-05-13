 package com.myweatherforecastapp.accurate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.internal.$Gson$Types;
import com.myweatherforecastapp.accurate.Common.Common;
import com.myweatherforecastapp.accurate.Retrofit.OpenMapApi;
import com.myweatherforecastapp.accurate.Retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.label305.asynctask.SimpleAsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

 /**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityFragment extends Fragment {
     private List<String> listCities;
     EditText city;
     ImageButton search;
     CompositeDisposable compositeDisposable;
     OpenMapApi mService;
     String lon;
     static CityFragment instance;
     private InterstitialAd mInterstitialAd;

     public static CityFragment getInstance() {
         if (instance == null)
             instance = new CityFragment();
         return instance;
     }

     // TODO: Rename parameter arguments, choose names that match
     // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
     private static final String ARG_PARAM1 = "param1";
     private static final String ARG_PARAM2 = "param2";

     // TODO: Rename and change types of parameters
     private String mParam1;
     private String mParam2;

     public CityFragment() {
         compositeDisposable = new CompositeDisposable();
         Retrofit retrofit = RetrofitClient.getInstance();
         mService = retrofit.create(OpenMapApi.class);

     }

     /**
      * Use this factory method to create a new instance of
      * this fragment using the provided parameters.
      *
      * @param param1 Parameter 1.
      * @param param2 Parameter 2.
      * @return A new instance of fragment CityFragment.
      */
     // TODO: Rename and change types and number of parameters
     public static CityFragment newInstance(String param1, String param2) {
         CityFragment fragment = new CityFragment();
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
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         View itemView = inflater.inflate(R.layout.fragment_city, container, false);
         city = itemView.findViewById(R.id.cities);
        search = itemView.findViewById(R.id.searc);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeatherInformation(city.getText().toString());
            }
        });

         new LoadCities().execute();

         return itemView;
     }

     private class LoadCities extends SimpleAsyncTask<List<String>> {
         @Override
         protected List<String> doInBackgroundSimple() {
             listCities = new ArrayList<>();
             try {
                 StringBuilder builder = new StringBuilder();
                 InputStream is = getResources().openRawResource(R.raw.citylist);
                 GZIPInputStream gzipInputStream = new GZIPInputStream(is);
                 InputStreamReader reader = new InputStreamReader(gzipInputStream);
                 BufferedReader in = new BufferedReader(reader);
                 String readed;
                 while ((readed = in.readLine()) != null)
                     builder.append(readed);
                 listCities = new Gson().fromJson(builder.toString(), new TypeToken<List<String>>() {
                 }.getType());


             } catch (IOException e) {
                 e.printStackTrace();
             }
             return listCities;
         }

         @Override
         protected void onSuccess(final List<String> listCity) {
             super.onSuccess(listCity);
             city.addTextChangedListener(new TextWatcher() {
                 @Override
                 public void beforeTextChanged(final CharSequence charSequence, int i, int i1, int i2) {



                 }

                 @Override
                 public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                 }


                 @Override
                 public void afterTextChanged(final Editable editable) {
                     MobileAds.initialize(getContext() ,new OnInitializationCompleteListener() {

                         @Override
                         public void onInitializationComplete(InitializationStatus initializationStatus) {

                         }
                     });
                     SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                     SharedPreferences.Editor editor = prefs.edit();
                     editor.putString("cityedit", String.valueOf(editable));
                     editor.apply();
                     mInterstitialAd = new InterstitialAd(getContext());
                     mInterstitialAd.setAdUnitId("ca-app-pub-9662381145965378/8948810136");
                     mInterstitialAd.loadAd(new AdRequest.Builder().build());
                 search.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         if(mInterstitialAd.isLoaded()){
                             mInterstitialAd.show();
                         }
                         else{
                         getWeatherInformation(city.getText().toString());

                         }
                     }
                 });
                     mInterstitialAd.setAdListener(new AdListener(){
                         @Override
                         public void onAdClosed() {
                             mInterstitialAd.loadAd(new AdRequest.Builder().build());
                             getWeatherInformation(city.getText().toString());
                         }
                     });

                 }

             });
         }
     }

     private void getWeatherInformation(final String lon) {
         compositeDisposable.add(mService.getweatherresultbycity(lon,
                 Common.api_key,
                 "metric")
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Consumer<WeatherResult>() {
                     @Override
                     public void accept(WeatherResult weatherResult) throws Exception {
                         Intent cityweather = new Intent(getContext(), CityWeather.class);
                         cityweather.putExtra("city", lon);
                         startActivity(cityweather);
                     }
                 }, new Consumer<Throwable>() {
                     @Override
                     public void accept(Throwable throwable) throws Exception {
                         Toast toast = Toast.makeText(getActivity(),
                                 "This city doesn't exist,please enter correct name", Toast.LENGTH_LONG);
                         toast.setGravity(Gravity.CENTER, 0, 0);
                         toast.show();
                     }
                 }));


     }
 }


