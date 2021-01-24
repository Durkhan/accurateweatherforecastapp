package com.weather.accurateweatherforecast;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.weather.accurateweatherforecast.Common.Common;
import com.weather.accurateweatherforecast.Model.Daily.DailyResult;
import com.weather.accurateweatherforecast.Retrofit.OpenMapApi;
import com.weather.accurateweatherforecast.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NotificationService extends Service {


    // display notification varaibles
    CompositeDisposable compositeDisposable;
    OpenMapApi mService;
    public static final String ID = "Notification";
    public static final int NOTIFICATION_ID = 101;
static RemoteViews remoteViews;



    private static NotificationService instance;
    public static NotificationService getInstance() {


        if (instance == null)
            instance = new NotificationService();
        return instance;
    }

    public NotificationService(){
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(OpenMapApi.class);

        // Required empty public constructor
    }
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

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
                          String lon=weatherResult.getName();
                          String desc=weatherResult.getWeather().get(0).getDescription().substring(0,1).toUpperCase()+weatherResult.getWeather().get(0).getDescription().substring(1).toLowerCase();
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("key",lon);
                            editor.putString("descr",desc);
                            editor.commit();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(getBaseContext(), ""+throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }));



        createNotificationChannel();

         remoteViews = new RemoteViews(getPackageName(), R.layout.notification);


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

                             remoteViews.setTextViewText(R.id.current_temprature, dailyResult.getCurrent().getTemp()+ "°C");
                            remoteViews.setTextViewText(R.id.daily_max_min_temprature, dailyResult.daily.get(0).getTemp().getMax()+ "°/" +dailyResult.daily.get(0).getTemp().getMin()+"°");
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            String lon=prefs.getString("key","");
                            String descr=prefs.getString("descr","");
                            remoteViews.setTextViewText(R.id.city_name,lon);
                            remoteViews.setTextViewText(R.id.current_description,descr );
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("01d")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.dayclearsky);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("01n")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.nightclearsky);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("02n")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.nightfewclouds);
                            }
                            if(dailyResult.daily.get(1).weather.get(0).getIcon().equals("02d")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.dayfewclouds);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("03n")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.scatteredclouds);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("03d")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.scatteredclouds);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("04n")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.brokenclouds);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("04d")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.brokenclouds);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("09n")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.showerrain);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("09d")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.showerrain);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("10d")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.dayrain);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("10n")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.nightrain);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("50d")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.mist);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("50n")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.mist);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("13d")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.mist);
                            }
                            if(dailyResult.daily.get(0).weather.get(0).getIcon().equals("13n")){
                                remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.mist);
                            }

                            NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this, ID);
                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setSmallIcon(R.drawable.weatherpicture);
                            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            builder.setAutoCancel(true);
                            builder.setCustomContentView(remoteViews);
                            builder.setContentIntent(pendingIntent);


                            Notification notification = builder.build();

                            startForeground(1, notification);


                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.d("Error",""+throwable.getMessage());
                        }
                    }));





















        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void createNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notification Channel";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ID, name, importance);
            channel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

}
